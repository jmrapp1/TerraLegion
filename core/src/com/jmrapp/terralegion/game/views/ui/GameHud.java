package com.jmrapp.terralegion.game.views.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;
import com.jmrapp.terralegion.engine.input.InputController;
import com.jmrapp.terralegion.engine.input.JoystickControl;
import com.jmrapp.terralegion.engine.utils.Settings;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.ui.PictureButton;
import com.jmrapp.terralegion.engine.views.screens.ScreenManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.item.impl.BlockItem;
import com.jmrapp.terralegion.game.item.impl.CombatItem;
import com.jmrapp.terralegion.game.item.impl.ToolItem;
import com.jmrapp.terralegion.game.utils.Vector2Factory;
import com.jmrapp.terralegion.game.views.screen.GameScreen;
import com.jmrapp.terralegion.game.views.screen.InventoryScreen;
import com.jmrapp.terralegion.game.world.DayManager;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.block.BlockManager;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;

/**
 * Created by Jon on 9/29/15.
 */
public class GameHud {

    private static final Drawable blankTile = ResourceManager.getInstance().getDrawable("blankTile");
    private static final Texture heartTexture = ResourceManager.getInstance().getTexture("heart");

    private JoystickControl moveControl, actionControl;
    private Stage stage;
    private SpriteBatch stageSb, sb;
    private OrthoCamera camera;
    private World world;
    private Texture bg;

    private InventoryBox[] inventoryBoxes = new InventoryBox[5];
    private PictureButton inventoryBtn = new PictureButton(ResourceManager.getInstance().getDrawable("inventoryBtn"), 20 + (InventoryBox.bg.getWidth() + 7) * 5, Settings.getHeight() - InventoryBox.bg.getHeight() - 10);

    private float lastBlockPlace;
    private Vector2 highlightedBlockPosition = Vector2Factory.instance.getVector2();
    private Rectangle collisionTestRect = new Rectangle();

    private GameScreen gameScreen;

    public GameHud(GameScreen gameScreen, World world) {
        this.gameScreen = gameScreen;
        this.world = world;
        stageSb = new SpriteBatch();
        sb = new SpriteBatch();
        stage = new Stage(new StretchViewport(Settings.getWidth(), Settings.getHeight()), stageSb);
        moveControl = new JoystickControl(ResourceManager.getInstance().getTexture("joystickBg"), ResourceManager.getInstance().getTexture("joystickKnob"), 10, 20, 20, 200, 200);
        actionControl = new JoystickControl(ResourceManager.getInstance().getTexture("joystickBg"), ResourceManager.getInstance().getTexture("joystickKnob"), 10, 550, 20, 200, 200);
        stage.addActor(moveControl.getTouchpad());
        stage.addActor(actionControl.getTouchpad());
        stage.act(Gdx.graphics.getDeltaTime());

        camera = new OrthoCamera();
        InputController.getInstance().addInputProcessor(stage);

        bg = ResourceManager.getInstance().getTexture("bg");

        for (int i = 0; i < inventoryBoxes.length; i++) {
            inventoryBoxes[i] = new InventoryBox(world.getPlayer().getInventory().getItemStack(i, 0), 20 + (i * (InventoryBox.bg.getWidth() + 7)), Settings.getHeight() - InventoryBox.bg.getHeight() - 10);
        }
        inventoryBoxes[0].setSelected(true);
    }

    public void update(OrthoCamera gameCamera) {
        camera.update();
        stage.act(Gdx.graphics.getDeltaTime());

        if (world.getPlayer().getInventory().wasChanged()) {
            updateInventoryBoxes();
            world.getPlayer().getInventory().resetChangedFlag();
        }

        if (moveControl.getTouchpad().isTouched()) {
            if (moveControl.getTouchpad().getKnobPercentY() > .5f && world.getPlayer().canJump())
                world.getPlayer().jump();
            world.getPlayer().addVelocity(moveControl.getTouchpad().getKnobPercentX() * world.getPlayer().getSpeed(), 0);
        } else if (actionControl.getTouchpad().isTouched()) {
            ItemStack selectedItemStack = getSelectedItemStack();
            if (selectedItemStack != null) {
                if (selectedItemStack.getItem() instanceof ToolItem) {
                    if (selectedItemStack.getItem() instanceof CombatItem) {
                        LivingEntity entity = findLivingEntity((ToolItem) selectedItemStack.getItem()); //look for entities we can attack
                        if (entity != null) { //if we found an entity to attack, attack it
                            entity.damage(((CombatItem) selectedItemStack.getItem()).getDamage());
                            world.getPlayer().usedTool();
                        }
                    } else {
                        breakBlock((ToolItem) selectedItemStack.getItem());
                    }
                } else if (selectedItemStack.getItem() instanceof BlockItem) {
                    placeBlock((BlockItem) selectedItemStack.getItem());
                }
            }
        } else if (Gdx.input.isTouched(0)) {
            float touchX = camera.unprojectXCoordinate(Gdx.input.getX(), Gdx.input.getY());
            float touchY = camera.unprojectYCoordinate(Gdx.input.getX(), Gdx.input.getY());

            for (InventoryBox box : inventoryBoxes) {
                if (box.isPressed(touchX, touchY)) {
                    if (!box.isSelected()) {
                        unsetSelectedInventoryBox();
                        box.setSelected(true);
                    }
                }
            }

            if (inventoryBtn.isPressed(touchX, touchY)) {
                ScreenManager.setScreen(new InventoryScreen(gameScreen, world, world.getPlayer().getInventory()));
            }

            touchX = gameCamera.unprojectXCoordinate(Gdx.input.getX(), Gdx.input.getY());
            touchY = gameCamera.unprojectYCoordinate(Gdx.input.getX(), Gdx.input.getY());

            ItemStack selectedItemStack = getSelectedItemStack();
            Vector2 origin = Vector2Factory.instance.getVector2(world.getPlayer().getX(), world.getPlayer().getY());
            Vector2 position = Vector2Factory.instance.getVector2(touchX, touchY);

            if (selectedItemStack != null) {
                if (selectedItemStack.getItem() instanceof ToolItem) {
                    ToolItem tool = (ToolItem) selectedItemStack.getItem();
                    if (position.dst(origin) <= tool.getReach()) {
                        BlockType type = world.getChunkManager().getBlockFromPos(touchX, touchY);
                        if (tool.canDamageBlock(type)) {
                            highlightedBlockPosition.set(((int)touchX / ChunkManager.TILE_SIZE) * ChunkManager.TILE_SIZE, ((int)touchY / ChunkManager.TILE_SIZE) * ChunkManager.TILE_SIZE);
                            if (world.getPlayer().canUseTool(tool))
                                if (world.getChunkManager().damageBlock(touchX, touchY, tool.getPower()))
                                    world.getPlayer().usedTool();
                        }
                    }
                } else if (selectedItemStack.getItem() instanceof BlockItem) {
                    if (position.dst(origin) <= 100) {
                        BlockType type = world.getChunkManager().getBlockFromPos(touchX, touchY);
                        if (type == BlockType.AIR) {
                            if(world.getChunkManager().getChunkFromPos(touchX, touchY).findLivingEntitiesInRange(touchX, touchY, ChunkManager.TILE_SIZE).size == 0) {
                                highlightedBlockPosition.set(((int) touchX / ChunkManager.TILE_SIZE) * ChunkManager.TILE_SIZE, ((int) touchY / ChunkManager.TILE_SIZE) * ChunkManager.TILE_SIZE);
                                if (Timer.getGameTimeElapsed() - lastBlockPlace > .75f) {
                                    if (world.getChunkManager().getBlockFromPos(touchX, touchY) == BlockType.AIR) {
                                        collisionTestRect.set(highlightedBlockPosition.x, highlightedBlockPosition.y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE);
                                        if (!world.getPlayer().getBounds().overlaps(collisionTestRect) || !BlockManager.getBlock(((BlockItem) selectedItemStack.getItem()).getBlockType()).collides()) {
                                            world.getChunkManager().setBlock(((BlockItem) selectedItemStack.getItem()).getBlockType(), touchX, touchY, true);
                                            world.getPlayer().getInventory().removeItemStack(selectedItemStack.getItem(), 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Vector2Factory.instance.destroy(origin);
            Vector2Factory.instance.destroy(position);
        } else {
            highlightedBlockPosition.set(-100, -100);
        }
    }

    public void render(OrthoCamera gameCamera) {
        if (highlightedBlockPosition.x != -100 && highlightedBlockPosition.y != -100) { //means we are highlighting a block and trying to break it
            sb.setProjectionMatrix(gameCamera.combined);
            sb.begin();
            blankTile.render(sb, highlightedBlockPosition.x, highlightedBlockPosition.y);
            sb.end();

            if(Gdx.input.isTouched()){
                for(InventoryBox inventoryBox : inventoryBoxes){
                    if(inventoryBox.isSelected() && !(inventoryBox.getItemStack() == null)) {
                        Vector2 touchPosition = gameCamera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());

                        Drawable icon = inventoryBox.getItemStack().getItem().getIcon();

                        sb.begin();
                        icon.render(sb, touchPosition.x + icon.getWidth() * 1.5f, touchPosition.y); //drawn slightly to the side to avoid your finger overlapping the icon
                        sb.end();
                    }
                }
            }
        }

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        ResourceManager.getInstance().getFont("font").draw(sb, "FPS: " + Gdx.graphics.getFramesPerSecond(), Settings.getWidth() - 150, Settings.getHeight() - 60);
        Chunk chunk = world.getChunkManager().getLoadedChunks()[1][1];
        if (chunk != null) {
            ResourceManager.getInstance().getFont("font").draw(sb, "Chunk: " + chunk.getStartX() + ", " + chunk.getStartY(), Settings.getWidth() - 150, Settings.getHeight() - 80);
        }

        int hearts = (int) world.getPlayer().getHealth() / 20;
        for (int i = 0; i < hearts; i++) {
            sb.draw(heartTexture, Settings.getWidth() - 75 - (i * (heartTexture.getWidth() + 5)), Settings.getHeight() - heartTexture.getHeight() - 15);
        }

        for (InventoryBox box : inventoryBoxes)
            box.render(sb);

        inventoryBtn.render(sb);

        sb.end();

        stage.draw();
    }

    public void renderBackground() {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        float value = DayManager.getInstance().getWorldLightValue();
        sb.setColor(value, value, value, 1);
        sb.draw(bg, 0, 0);
        sb.setColor(Color.WHITE);
        sb.end();
    }

    private LivingEntity findLivingEntity(ToolItem item) {
        if (world.getPlayer().canUseTool(item)) {

            Vector2 direction = Vector2Factory.instance.getVector2(actionControl.getTouchpad().getKnobPercentX(), actionControl.getTouchpad().getKnobPercentY()).nor(); //Get vector direction of the know
            Chunk chunk = world.getChunkManager().getChunkFromPos(world.getPlayer().getX(), world.getPlayer().getY());

            //The entities found array is sorted by distance to player from least to greatest
            for (LivingEntity entity : chunk.findLivingEntitiesInRange(world.getPlayer().getX(), world.getPlayer().getY(), item.getReach())) {
                if (direction.x < 0) {
                    if (entity.getX() < world.getPlayer().getX()) {
                        return entity;
                    }
                } else if (direction.x >= 0) {
                    if (entity.getX() >= world.getPlayer().getX()) {
                        return entity;
                    }
                }
            }
        }
        return null;
    }

    private void breakBlock(ToolItem tool) {
        Vector2 direction = Vector2Factory.instance.getVector2(actionControl.getTouchpad().getKnobPercentX(), actionControl.getTouchpad().getKnobPercentY()).nor(); //Get vector direction of the know
        Vector2 foundBlockPos = world.getChunkManager().findBlock(tool, world.getPlayer().getX() + ChunkManager.TILE_SIZE / 2, world.getPlayer().getY() + ChunkManager.TILE_SIZE / 2, direction, tool.getReach()); //Find a block
        if (direction.x != 0 && direction.y != 0) {
            if (foundBlockPos != null) {
                float px = ChunkManager.tileToPixelPosition((int) foundBlockPos.x);
                float py = ChunkManager.tileToPixelPosition((int) foundBlockPos.y);
                highlightedBlockPosition.set(px, py);
            }
            if (world.getPlayer().canUseTool(tool)) {
                if (foundBlockPos != null) { //if block found within reach
                    if (world.getChunkManager().damageBlock((int) foundBlockPos.x, (int) foundBlockPos.y, tool.getPower())) { //Damage the block
                        world.getPlayer().usedTool();
                    }
                }
            }
        }
        Vector2Factory.instance.destroy(foundBlockPos);
        Vector2Factory.instance.destroy(direction);
    }

    private void placeBlock(BlockItem block) {
        Vector2 direction = Vector2Factory.instance.getVector2(actionControl.getTouchpad().getKnobPercentX(), actionControl.getTouchpad().getKnobPercentY()).nor(); //Get vector direction of the know
        Vector2 foundBlockPos = world.getChunkManager().findFarthestAirBlock(world.getPlayer().getX() + ChunkManager.TILE_SIZE / 2, world.getPlayer().getY() + ChunkManager.TILE_SIZE / 2, direction, 100); //Find an air block

        if (direction.x != 0 && direction.y != 0) {
            if (foundBlockPos != null) { //if we found an air block
                float px = ChunkManager.tileToPixelPosition((int) foundBlockPos.x);
                float py = ChunkManager.tileToPixelPosition((int) foundBlockPos.y);
                highlightedBlockPosition.set(px, py);
            }
            if (foundBlockPos != null) { //if block found within reach
                if (Timer.getGameTimeElapsed() - lastBlockPlace > .75f) {
                    collisionTestRect.set(highlightedBlockPosition.x, highlightedBlockPosition.y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE);
                    if (!world.getPlayer().getBounds().overlaps(collisionTestRect)) {
                        world.getChunkManager().setBlock(block.getBlockType(), (int) foundBlockPos.x, (int) foundBlockPos.y, true);
                        world.getPlayer().getInventory().removeItemStack(block, 1);
                        lastBlockPlace = Timer.getGameTimeElapsed();
                    }
                }
            }
        }
        Vector2Factory.instance.destroy(foundBlockPos);
        Vector2Factory.instance.destroy(direction);
    }

    public void updateInventoryBoxes() {
        for (int i = 0; i < inventoryBoxes.length; i++) {
            inventoryBoxes[i].setItemStack(world.getPlayer().getInventory().getItemStack(i, 0));
        }
    }

    private void unsetSelectedInventoryBox() {
        for (InventoryBox b : inventoryBoxes) {
            if (b.isSelected()) {
                b.setSelected(false);
                break;
            }
        }
    }

    public ItemStack getSelectedItemStack() {
        for (InventoryBox box : inventoryBoxes) {
            if (box.isSelected()) {
                return box.getItemStack();
            }
        }
        return null;
    }

    public void resume() {
        InputController.getInstance().addInputProcessor(stage);
    }

    public void dispose() {
        InputController.getInstance().removeInputProcessor(stage);
    }

    public void resize(int width, int height) {
        camera.resize();
    }

}
