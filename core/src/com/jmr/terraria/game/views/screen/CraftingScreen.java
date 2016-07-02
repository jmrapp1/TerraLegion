package com.jmr.terraria.game.views.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jmr.terraria.engine.camera.OrthoCamera;
import com.jmr.terraria.engine.input.InputController;
import com.jmr.terraria.engine.utils.Settings;
import com.jmr.terraria.engine.views.drawables.ResourceManager;
import com.jmr.terraria.engine.views.drawables.ui.Label;
import com.jmr.terraria.engine.views.drawables.ui.MultilineLabel;
import com.jmr.terraria.engine.views.drawables.ui.PictureButton;
import com.jmr.terraria.engine.views.screens.Screen;
import com.jmr.terraria.engine.views.screens.ScreenManager;
import com.jmr.terraria.game.item.ItemCategory;
import com.jmr.terraria.game.item.ItemStack;
import com.jmr.terraria.game.item.crafting.CraftingRecipe;
import com.jmr.terraria.game.item.crafting.CraftingRecipes;
import com.jmr.terraria.game.item.inventory.Inventory;
import com.jmr.terraria.game.views.ui.ItemBox;
import com.jmr.terraria.game.utils.CachePool;
import com.jmr.terraria.game.world.World;


/**
 * Created by Jon on 10/8/15.
 */
public class CraftingScreen implements Screen {

	private static final int MAX_TABLE_WIDTH = 5;

	private static final Texture bg = ResourceManager.getInstance().getTexture("craftingBg");
	private static final TextureRegionDrawable inventoryBoxDrawable = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("inventoryBox")));
	private static final TextureRegionDrawable inventorySelectedBoxDrawable = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("selectedInventoryBox")));

	private CategoryButton toolCategoryBtn = new CategoryButton(ItemCategory.TOOL, ResourceManager.getInstance().getTexture("unselectedToolCategoryBtn"), ResourceManager.getInstance().getTexture("selectedToolCategoryBtn"), 30, Settings.getHeight() - 120);
	private CategoryButton furnitureCategoryBtn = new CategoryButton(ItemCategory.FURNITURE, ResourceManager.getInstance().getTexture("unselectedToolCategoryBtn"), ResourceManager.getInstance().getTexture("selectedToolCategoryBtn"), 30, Settings.getHeight() - 170);

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
	private Table craftingTable;
	private ScrollPane pane;

	private ItemCategory currentCategory;
	private CraftingRecipe selectedRecipe;

	private PictureButton cancelBtn = new PictureButton(ResourceManager.getInstance().getDrawable("cancelBtn"), Settings.getWidth() - ResourceManager.getInstance().getDrawable("cancelBtn").getWidth() - 15, Settings.getHeight() - 61 + 10);
	private PictureButton craftingBtn = new PictureButton(ResourceManager.getInstance().getDrawable("selectedCraftingBtn"), 30, Settings.getHeight() - 61 + 10);
	private PictureButton invBtn = new PictureButton(ResourceManager.getInstance().getDrawable("unselectedInvBtn"), (Settings.getWidth() / 2) - 25, Settings.getHeight() - 61 + 10);
	private PictureButton craftBtn = new PictureButton(ResourceManager.getInstance().getDrawable("craftBtn"), Settings.getWidth() - 200, 25);

	public CraftingScreen(Screen gameScreen, World world, Inventory inventory) {
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

		//CATEGORY BUTTONS
		stage.addActor(toolCategoryBtn.getImageButton());
		stage.addActor(furnitureCategoryBtn.getImageButton());

		ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
		paneStyle.background = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("invStageBg")));
		//paneStyle.vScrollKnob = new TextureRegionDrawable();
		//paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;

		Table craftingContainer = CachePool.getTable();
		usedTablesCache.add(craftingContainer);
		float startX = (Settings.getWidth() / 2) - 255;
		craftingContainer.setBounds(startX, 0, 370, Settings.getHeight() - 61);

		craftingTable = CachePool.getTable();
		stage.addListener(new CraftingButtonClickListener(stage, craftingTable, craftingContainer.getX(), craftingContainer.getY()));
		usedTablesCache.add(craftingTable);

		pane = new ScrollPane(craftingTable, paneStyle);
		craftingContainer.add(pane).width(370).height(Settings.getHeight() - 61);
		craftingContainer.row();
		stage.addActor(craftingContainer);

		itemNameLabel = new Label("", ResourceManager.getInstance().getFont("font"), startX + 370 + 10, Settings.getHeight() - 61 - 35, false);
		itemInfoLabel = new MultilineLabel("", ResourceManager.getInstance().getFont("font"), startX + 370 + 10, Settings.getHeight() - 61 - 85, false);

		populateCraftableItems(toolCategoryBtn.getCategory());

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
			} else if (invBtn.isPressed(touchX, touchY)) {
				ScreenManager.setScreen(new InventoryScreen(gameScreen, world, inventory));
			} else if (selectedRecipe != null && craftBtn.isPressed(touchX, touchY)) {
				inventory.addItemStack(selectedRecipe.craft(inventory));
				updateSelectedRecipeInfo();
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(bg, 0, 0);
		itemNameLabel.render(sb);
		itemInfoLabel.render(sb);
		cancelBtn.render(sb);
		craftingBtn.render(sb);
		invBtn.render(sb);
		if (selectedRecipe != null)
			craftBtn.render(sb);
		sb.end();

		stage.draw();
	}

	@Override
	public void dispose() {
		InputController.getInstance().removeInputProcessor(stage);
		stage.dispose();

		for (int i = 0; i < usedTablesCache.size; i++) { //Manage the tables so we don't over use them each time we open the inventory screen
			CachePool.addTable(usedTablesCache.get(i));
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

	private void populateCraftableItems(ItemCategory category) {
		craftingTable.clear();
		currentCategory = category;
		itemNameLabel.setText("");
		itemInfoLabel.setText("");

		Array<CraftingRecipe> recipes = CraftingRecipes.getInstance().getCraftableItems(category);

		int y = 0;
		for (int i = 0; i < recipes.size; i++) {
			ItemStack stack = recipes.get(i).getCraftedItemStack();
			ItemBox box = new ItemBox(stack, String.valueOf(i));
			Cell<Table> cell = craftingTable.add((Table) box).width(60).height(60).padRight(10).padBottom(10);
			if (i % MAX_TABLE_WIDTH == 0) {
				cell.padLeft(10);
			}
			if (y == 0) {
				cell.padTop(10);
			}
			if ((i + 1) % MAX_TABLE_WIDTH == 0) {
				craftingTable.row();
				y++;
			}
		}
	}

	private void updateSelectedRecipeInfo() {
		ItemStack itemStack = selectedRecipe.getCraftedItemStack();
		if (itemStack != null) {
			itemNameLabel.setText(itemStack.getItem().getName());
			String text = "";
			for (ItemStack stack : selectedRecipe.getRequiredItems()) {
				text += stack.getItem().getName() + "(" + inventory.getTotalCount(stack.getItem()) + "/" + stack.getStack() + ")\n";
			}
			itemInfoLabel.setText(text);
		}
	}

	private class CraftingButtonClickListener extends ClickListener {

		private Stage stage;
		private Table craftingHolder;
		private float tableOffsetX, tableOffsetY;

		public CraftingButtonClickListener(Stage stage, Table craftingHolder, float tableOffsetX, float tableOffsetY) {
			this.stage = stage;
			this.craftingHolder = craftingHolder;
			this.tableOffsetX = tableOffsetX;
			this.tableOffsetY = tableOffsetY;
		}

		@Override
		public void clicked(InputEvent event, float xClick, float yClick) {
			for (Actor actor : stage.getActors()) {
				if (actor instanceof ImageButton) {
					ImageButton btn = (ImageButton) actor;

					float boxX = btn.getX();
					float boxY = btn.getY();
					float boxWidth = btn.getWidth();
					float boxHeight = btn.getHeight();

					if (xClick >= boxX && xClick <= boxX + boxWidth && yClick >= boxY && yClick <= boxY + boxHeight) { //dropped on itembox
						String[] split = actor.getName().split(" ");
						if (split[0].equals("category")) { //It's a category button so switch categories
							if (btn == toolCategoryBtn.getImageButton()) {
								populateCraftableItems(toolCategoryBtn.getCategory());
							} else if (btn == furnitureCategoryBtn.getImageButton()) {
								populateCraftableItems(furnitureCategoryBtn.getCategory());
							}
							return;
						}
					}
				}
			}

			for (Actor actor : craftingHolder.getChildren()) {
				if (actor instanceof ItemBox) {
					ItemBox box = (ItemBox) actor;
					if (box != selectedInventoryBox) {
						float boxX = box.getX() + tableOffsetX;
						float boxY = box.getY() + tableOffsetY;
						float boxWidth = box.getWidth();
						float boxHeight = box.getHeight();

						if (xClick >= boxX && xClick <= boxX + boxWidth && yClick >= boxY && yClick <= boxY + boxHeight) { //dropped on itembox
							box.setSelected(true);

							if (selectedInventoryBox != null) {
								selectedInventoryBox.setSelected(false);
							}
							selectedInventoryBox = box;

							//selected a craftable item
							Array<CraftingRecipe> recipes = CraftingRecipes.getInstance().getCraftableItems(currentCategory);
							int index = Integer.parseInt(box.getExtra());
							selectedRecipe = recipes.get(index);
							updateSelectedRecipeInfo();

							break;
						}
					}
				}
			}
		}
	}

	private class CategoryButton {

		private ImageButton cachedButton;
		private ItemCategory category;
		private Texture upTexture, downTexture;
		private float x, y;

		public CategoryButton(ItemCategory category, Texture upTexture, Texture downTexture, float x, float y) {
			this.category = category;
			this.upTexture = upTexture;
			this.downTexture = downTexture;
			this.x = x;
			this.y = y;
		}

		public ItemCategory getCategory() {
			return category;
		}

		public ImageButton getImageButton() {
			if (cachedButton == null) {
				ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
				style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
				style.down = new TextureRegionDrawable(new TextureRegion(downTexture));
				cachedButton = new ImageButton(style);
				cachedButton.setBounds(x, y, upTexture.getWidth(), upTexture.getHeight());
				cachedButton.setName("category ");
				return cachedButton;
			}
			return cachedButton;
		}

	}

}

