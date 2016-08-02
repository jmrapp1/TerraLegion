package com.jmrapp.terralegion.game.views.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;
import com.jmrapp.terralegion.engine.input.InputController;
import com.jmrapp.terralegion.engine.utils.Settings;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.ui.Label;
import com.jmrapp.terralegion.engine.views.drawables.ui.MultilineLabel;
import com.jmrapp.terralegion.engine.views.drawables.ui.PictureButton;
import com.jmrapp.terralegion.engine.views.screens.Screen;
import com.jmrapp.terralegion.engine.views.screens.ScreenManager;
import com.jmrapp.terralegion.game.world.entity.Drop;
import com.jmrapp.terralegion.game.world.entity.DropManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.item.inventory.Inventory;
import com.jmrapp.terralegion.game.views.ui.ItemBox;
import com.jmrapp.terralegion.game.utils.CachePool;
import com.jmrapp.terralegion.game.utils.Tuple;
import com.jmrapp.terralegion.game.world.World;

/**
 * Created by Jon on 10/8/15.
 */
public class InventoryScreen implements Screen {

	private static final Texture bg = ResourceManager.getInstance().getTexture("inventoryBg");
	private static final TextureRegionDrawable inventoryBoxDrawable = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("inventoryBox")));
	private static final TextureRegionDrawable inventorySelectedBoxDrawable = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("selectedInventoryBox")));

	private static final Array<Table> usedTablesCache = new Array<Table>();
	private static final Array<Group> usedGroupsCache = new Array<Group>();

	private static final Array<InventoryDragListener> dragListenersCache = new Array<InventoryDragListener>(InventoryDragListener.class);
	private static final Array<InventoryDragListener> usedDragListenersCache = new Array<InventoryDragListener>();

	private Stage stage;
	private SpriteBatch stageSb;

	private OrthoCamera camera;
	private final World world;
	private final Inventory inventory;
	private final Screen gameScreen;

	private ItemBox selectedInventoryBox;
	private Label itemNameLabel;
	private MultilineLabel itemInfoLabel;

	private PictureButton cancelBtn = new PictureButton(ResourceManager.getInstance().getDrawable("cancelBtn"), Settings.getWidth() - ResourceManager.getInstance().getDrawable("cancelBtn").getWidth() - 15, Settings.getHeight() - 61 + 10);
	private PictureButton craftingBtn = new PictureButton(ResourceManager.getInstance().getDrawable("unselectedCraftingBtn"), 30, Settings.getHeight() - 61 + 10);
	private PictureButton invBtn = new PictureButton(ResourceManager.getInstance().getDrawable("selectedInvBtn"), (Settings.getWidth() / 2) - 25, Settings.getHeight() - 61 + 10);
	private PictureButton dropBtn = new PictureButton(ResourceManager.getInstance().getDrawable("dropItemBtn"), Settings.getWidth() - 175, 30);
	private PictureButton splitBtn = new PictureButton(ResourceManager.getInstance().getDrawable("splitItemBtn"), Settings.getWidth() - 175, 100);

	private Table table;

	public InventoryScreen(Screen gameScreen, World world, Inventory inventory) {
		this.gameScreen = gameScreen;
		this.world = world;
		this.inventory = inventory;
	}

	@Override
	public void create() {
		camera = new OrthoCamera();
		camera.resize();

		stageSb = new SpriteBatch();
		stage = new Stage(new StretchViewport(Settings.getWidth(), Settings.getHeight()), stageSb);

		ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
		paneStyle.background = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("invStageBg")));
		//paneStyle.vScrollKnob = new TextureRegionDrawable();
		//paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;

		Table invContainer = CachePool.getTable();
		invContainer.setCullingArea(new Rectangle(0, 0, Settings.getWidth(), Settings.getHeight()));
		usedTablesCache.add(invContainer);

		float startX = (Settings.getWidth() / 2) - 255;
		invContainer.setBounds(startX, 0, 370, Settings.getHeight() - 61);

		table = CachePool.getTable();
		table.setCullingArea(new Rectangle(0, 0, Settings.getWidth(), Settings.getHeight()));
		table.addListener(new InventoryButtonClickListener(table));
		usedTablesCache.add(table);

		ScrollPane pane = new ScrollPane(table, paneStyle);
		pane.setCancelTouchFocus(false);
		pane.setCullingArea(new Rectangle(0, 0, Settings.getWidth(), Settings.getHeight()));

		invContainer.add(pane).width(370).height(Settings.getHeight() - 61);
		invContainer.row();
		stage.addActor(invContainer);

		itemNameLabel = new Label("", ResourceManager.getInstance().getFont("font"), startX + 370 + 10, Settings.getHeight() - 61 - 35, false);
		itemInfoLabel = new MultilineLabel("", ResourceManager.getInstance().getFont("font"), startX + 370 + 10, Settings.getHeight() - 61 - 85, false);

		for (int y = 0; y < inventory.getHeight(); y++) {
			for (int x = 0; x < inventory.getWidth(); x++) {

				ItemStack stack = inventory.getItemStack(x, y);
				ItemBox box = new ItemBox(stack, x, y, x + " " + y);
				InventoryDragListener dragListener = getDragListener(box);
				dragListener.setScrollPane(pane);
				dragListener.setTableHolder(table);
				dragListener.setInventory(inventory);
				box.addListener(dragListener);

				Cell<Table> cell = table.add((Table) box).width(60).height(60).padRight(10).padBottom(10);
				if (x == 0) {
					cell.padLeft(10);
				}
				if (y == 0) {
					cell.padTop(10);
				}
			}
			table.row();
		}
		InputController.getInstance().addInputProcessor(stage);
	}

	@Override
	public void update() {
		camera.update();
		stage.act(Gdx.graphics.getDeltaTime());

		if (Gdx.input.justTouched()) {
			float touchX = camera.unprojectXCoordinate(Gdx.input.getX(), Gdx.input.getY());
			float touchY = camera.unprojectYCoordinate(Gdx.input.getX(), Gdx.input.getY());
			if (cancelBtn.isPressed(touchX, touchY)) {
				ScreenManager.setScreen(gameScreen);
			} else if (craftingBtn.isPressed(touchX, touchY)) {
				ScreenManager.setScreen(new CraftingScreen(gameScreen, world, inventory));
			} else if (dropBtn.isPressed(touchX, touchY)) {
				if (selectedInventoryBox != null) {
					ItemStack stack = selectedInventoryBox.getItemStack();
					if (stack != null) {
						Drop drop = DropManager.getInstance().getDrop(stack.getItem().getTypeId(), stack.getStack(), world.getPlayer().getX(), world.getPlayer().getY() + world.getPlayer().getHeight());
						drop.setPickupWaitTime(3f);
						drop.setVelocity(0, 2);
						inventory.setItemStack(null, selectedInventoryBox.getInventoryX(), selectedInventoryBox.getInventoryY());
						world.getChunkManager().getChunkFromPos(world.getPlayer().getX(), world.getPlayer().getY()).addEntity(drop);
						selectedInventoryBox.setItemStack(null);
						selectedInventoryBox.setupBox();
						selectedInventoryBox.setSelected(false);
						selectedInventoryBox = null;
					}
				}
			} else if (splitBtn.isPressed(touchX, touchY)) {
				if (selectedInventoryBox != null) {
					if (selectedInventoryBox.getItemStack() != null) {
						if (!inventory.isFull()) {
							ItemStack itemStack = selectedInventoryBox.getItemStack();
							if (itemStack.getStack() > 1) {
								int stack1, stack2;
								if(itemStack.getStack() % 2 == 0) {
                                    stack1 = itemStack.getStack()/2;
									stack2 = stack1;
								} else {
									stack1 = itemStack.getStack()/2;
									stack2 = stack1 + 1;
								}
								itemStack.setStack(stack1);

								ItemStack itemStack2 = ItemStack.getItemStack(itemStack.getItem(), stack2);
								Tuple<Integer, Integer> openSpot = inventory.getOpenSpot();

								inventory.setItemStack(itemStack, selectedInventoryBox.getInventoryX(), selectedInventoryBox.getInventoryY());
								inventory.setItemStack(itemStack2, openSpot.getObject1(), openSpot.getObject2());
								selectedInventoryBox.setupBox();

								for (Actor actor : table.getChildren()) {
									if (actor instanceof ItemBox) {
										ItemBox box = (ItemBox) actor;
										if (box.getInventoryX() == openSpot.getObject1() && box.getInventoryY() == openSpot.getObject2()) {
											box.setItemStack(itemStack2);
											box.setupBox();
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.draw(bg, 0, 0);
		itemNameLabel.render(sb);
		itemInfoLabel.render(sb);
		cancelBtn.render(sb);
		craftingBtn.render(sb);
		invBtn.render(sb);
		dropBtn.render(sb);
		splitBtn.render(sb);

		stage.draw();
	}

	@Override
	public void dispose() {
		InputController.getInstance().removeInputProcessor(stage);
		stage.dispose();

		for (int i = 0; i < usedTablesCache.size; i++) { //Manage the tables so we don't over use them each time we open the inventory screen
			Table table = usedTablesCache.get(i);
			if (table instanceof ItemBox) {
				((ItemBox)table).cacheObjects();
			} else {
				CachePool.addTable(table);
			}
		}
		usedTablesCache.clear();

		for (int i = 0; i < usedGroupsCache.size; i++) {
			CachePool.addGroup(usedGroupsCache.get(i));
		}
		usedGroupsCache.clear();

		for (int i = 0; i < usedDragListenersCache.size; i++) {
			dragListenersCache.add(usedDragListenersCache.get(i));
		}
		dragListenersCache.clear();
	}

	@Override
	public void resize(int width, int height) {
		camera.resize();
	}

	@Override
	public void onBackPressed() {
		ScreenManager.goBack();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		InputController.getInstance().addInputProcessor(stage);
	}

	private class InventoryButtonClickListener extends ClickListener {

		private Table parent;

		public InventoryButtonClickListener(Table parent) {
			this.parent = parent;
		}

		@Override
		public void clicked(InputEvent event, float xClick, float yClick) {
			for (Actor actor : parent.getChildren()) {
				if (actor instanceof ItemBox) {
					ItemBox box = (ItemBox) actor;
					float boxX = box.getX();
					float boxY = box.getY();
					float boxWidth = box.getWidth();
					float boxHeight = box.getHeight();

					if (xClick >= boxX && xClick <= boxX + boxWidth && yClick >= boxY && yClick <= boxY + boxHeight) { //dropped on itembox
						box.setSelected(true);

						if (selectedInventoryBox != null) {
							selectedInventoryBox.setSelected(false);
						}
						selectedInventoryBox = box;

						ItemStack itemStack = inventory.getItemStack(box.getInventoryX(), box.getInventoryY());

						if (itemStack != null) {
							itemNameLabel.setText(itemStack.getItem().getName());
							itemInfoLabel.setText(itemStack.getItem().toString());
						} else {
							itemNameLabel.setText("");
							itemInfoLabel.setText("");
						}
						break;
					}
				}
			}
		}
	}

	private static InventoryDragListener getDragListener(ItemBox itemBox) {
		if (dragListenersCache.size > 0) {
			InventoryDragListener listener = dragListenersCache.removeIndex(0);
			listener.setItemBox(itemBox);
			usedDragListenersCache.add(listener);
			return listener;
		}
		InventoryDragListener listener = new InventoryDragListener();
		listener.setItemBox(itemBox);
		usedDragListenersCache.add(listener);
		return listener;
	}

}

class InventoryDragListener extends DragListener {

	private ItemBox itemBox;
	private ScrollPane scrollPane;
	private Table tableHolder;
	private Inventory inventory;

	public void drag(InputEvent event, float x, float y, int pointer) {
		if (itemBox.getItemStack() != null) {
			itemBox.setPosition(x - (itemBox.getItemImage().getWidth() / 2), y - (itemBox.getItemImage().getHeight() / 2));
		}
	}

	@Override
	public void dragStop(InputEvent event, float x, float y, int pointer) {
		scrollPane.setScrollingDisabled(false, false);
		if (itemBox.getItemStack() != null) {
			x = itemBox.getX() + x;
			y = itemBox.getY() + y;

			for (Actor actor : tableHolder.getChildren()) {
				if (actor instanceof ItemBox) {
					ItemBox box = (ItemBox) actor;
					if (box != itemBox) {
						float boxX = box.getX();
						float boxY = box.getY();
						float boxWidth = box.getWidth();
						float boxHeight = box.getHeight();

						if (x >= boxX && x <= boxX + boxWidth && y >= boxY && y <= boxY + boxHeight) { //dropped on itembox
							ItemStack stack = itemBox.getItemStack();
							ItemStack boxStack = box.getItemStack();

							if (stack != null && boxStack != null && stack.getItem() == boxStack.getItem()) {
								int remainder = boxStack.addStack(stack.getStack());
								if (remainder > 0) { //there's some left over
									stack.setStack(remainder);
								} else { //there's none left over so remove it
									itemBox.setItemStack(null);
									inventory.setItemStack(null, itemBox.getInventoryX(), itemBox.getInventoryY());
								}
							} else {
								itemBox.setItemStack(boxStack);
								box.setItemStack(stack);
								inventory.setItemStack(stack, box.getInventoryX(), box.getInventoryY());
								inventory.setItemStack(boxStack, itemBox.getInventoryX(), itemBox.getInventoryY());
							}
							itemBox.setupBox(); //re-image the box
							box.setupBox(); //re-image the box
							break;
						}
					}
				}
			}
			itemBox.setOriginalPosition();
		}
	}

	public void dragStart(InputEvent event, float x, float y, int pointer) {
		if (itemBox.getItemStack() != null) {
			scrollPane.setScrollingDisabled(true, true);
			scrollPane.cancel();
			itemBox.setZIndex(700);
		}
	}

	public void setItemBox(ItemBox itemBox) {
		this.itemBox = itemBox;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void setTableHolder(Table table) {
		this.tableHolder = table;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}