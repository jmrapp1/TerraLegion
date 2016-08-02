package com.jmrapp.terralegion.game.world.block.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.views.screens.ScreenManager;
import com.jmrapp.terralegion.game.utils.LightUtils;
import com.jmrapp.terralegion.game.views.screen.InventoryScreen;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.block.BlockProperties;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.block.InventoryBlock;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

/**
 * Acts as a block where players may store items
 *
 * Created by root on 8/2/16.
 *
 * TODO For breaking, the chest could display a menu: "(Break) or (Open)", or it could hold a (Break) button inside the inventory screen.
 */
public class ChestBlock extends InventoryBlock {

    /** Used for the chest's inventory display **/
    private InventoryScreen inventoryScreen;

    /** Whether the chest is opened **/
    private boolean open;

    private Vector3 mousePosition = new Vector3();
    private Rectangle mouseBounds;

    public ChestBlock(int inventoryWidth, int inventoryHeight , BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, boolean transparent, float initHealth, float resistance){
        super(inventoryWidth, inventoryHeight, type, drawable, lightBlockingAmount, collides, transparent, initHealth, resistance);
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch sb, float x, float y, float lightValue) {
        super.render(camera, sb, x, y, lightValue);;

        if(this.isOpen()){
            this.inventoryScreen.update();;

            this.inventoryScreen.render(sb);

            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
                this.close();
            }
        }

        if(this.isTapped(camera, x, y)){
           this.open();
        }

    }

    @Override
    public void onPlace(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
        super.onPlace(chunkManager, chunk, chunkTileX, chunkTileY);

        this.inventoryScreen = new InventoryScreen(ScreenManager.getCurrent(), chunkManager.getWorld(), this.getInventory());

        this.inventoryScreen.create();
    }

    /**
     * @return Whether the chest is being tapped
     */
    public boolean isTapped(OrthographicCamera camera, float blockX, float blockY){
        this.mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        this.mousePosition = camera.unproject(mousePosition);

        if(this.mouseBounds == null){
            this.mouseBounds = new Rectangle(mousePosition.x, mousePosition.y, 0, 0);
        }

        this.mouseBounds.set(this.mousePosition.x, this.mousePosition.y, 0, 0);

        if(this.getBody(blockX, blockY).getBounds().overlaps(mouseBounds) && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            return true;
        }

        return false;
    }

    /**
     * Opens the inventory display
     */
    public void open(){
        this.open = true;
    }

    /**
     * Closes the inventory display
     */
    public void close(){
        this.open = false;
    }

    public boolean isOpen(){
        return this.open;
    }


}
