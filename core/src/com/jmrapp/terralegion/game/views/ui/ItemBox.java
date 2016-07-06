package com.jmrapp.terralegion.game.views.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.utils.CachePool;

/**
 * Created by Jon on 12/15/15.
 */
public class ItemBox extends Table {

	private static final TextureRegionDrawable inventoryBoxDrawable = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("inventoryBox")));
	private static final TextureRegionDrawable inventorySelectedBoxDrawable = new TextureRegionDrawable(new TextureRegion(ResourceManager.getInstance().getTexture("selectedInventoryBox")));

	private Group btnGroup;
	private ImageButton btn;
	private Label itemLabel;
	private Image itemImage;
	private ImageButton.ImageButtonStyle style;

	private ItemStack stack;
	private int inventoryX, inventoryY;
	private String extra;
	private float originalX, originalY, originalLabelX, originalLabelY;

	public ItemBox(ItemStack stack, int inventoryX, int inventoryY) {
		this(stack, inventoryX, inventoryY, "");
	}

	public ItemBox(ItemStack stack, String extra) {
		this(stack, -1, -1, extra);
	}

	public ItemBox(ItemStack stack, int inventoryX, int inventoryY, String extra) {
		this.stack = stack;
		this.inventoryX = inventoryX;
		this.inventoryY = inventoryY;
		this.extra = extra;

		btnGroup = CachePool.getGroup();
		btnGroup.setBounds(0, 0, 60, 60);

		style = new ImageButton.ImageButtonStyle();
		style.up = inventoryBoxDrawable;
		btn = new ImageButton(style);
		btn.setBounds(0, 0, 60, 60);
		btn.setName(extra);
		btnGroup.addActor(btn);

		setupBox();
		add(btnGroup);
	}

	public void setupBox() {
		if (stack != null) {
			Label.LabelStyle labelStyle = new Label.LabelStyle();
			labelStyle.font = ResourceManager.getInstance().getFont("smallFont");
			labelStyle.fontColor = Color.WHITE;

			if (itemLabel == null) {
				itemLabel = new Label(String.valueOf(stack.getStack()), labelStyle);
			} else {
				itemLabel.setText(String.valueOf(stack.getStack()));
			}

			float width = itemLabel.getWidth();
			itemLabel.setBounds(5, 60 - 15, width, 15);
			if (itemImage == null) {
				itemImage = new Image(stack.getItem().getIcon().getTextureRegion());
			} else {
				itemImage.setDrawable(new TextureRegionDrawable(stack.getItem().getIcon().getTextureRegion()));
			}
			itemImage.setVisible(true);
			itemImage.setBounds(30 - (itemImage.getWidth() / 2), 30 - (itemImage.getHeight() / 2), itemImage.getWidth(), itemImage.getHeight());

			originalX = itemImage.getX();
			originalY = itemImage.getY();
			originalLabelX = itemLabel.getX();
			originalLabelY = itemLabel.getY();

			btnGroup.addActor(itemLabel);
			btnGroup.addActor(itemImage);
		} else {
			if (itemLabel != null) {
				itemLabel.setText("");
			}
			if (itemImage != null) {
				itemImage.setVisible(false);
			}
		}
	}

	public void setSelected(boolean b) {
		if (b)
			btn.getStyle().up = inventorySelectedBoxDrawable;
		else
			btn.getStyle().up = inventoryBoxDrawable;
	}

	public void setOriginalPosition() {
		itemImage.setPosition(originalX, originalY);
		itemLabel.setPosition(originalLabelX, originalLabelY);
	}

	public void cacheObjects() {
		CachePool.addGroup(btnGroup);
	}

	public Image getItemImage() {
		return itemImage;
	}

	public int getInventoryX() {
		return inventoryX;
	}

	public int getInventoryY() {
		return inventoryY;
	}

	public ItemStack getItemStack() {
		return stack;
	}

	public void setItemStack(ItemStack stack) {
		this.stack = stack;
	}

	public String getExtra() {
		return extra;
	}

	@Override
	public void setPosition(float x, float y) {
		if (itemLabel != null)
			itemLabel.setPosition(x - 10, y + 30);
		if (itemImage != null)
			itemImage.setPosition(x, y);
	}
}
