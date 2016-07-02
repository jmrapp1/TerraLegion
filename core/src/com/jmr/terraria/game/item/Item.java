package com.jmr.terraria.game.item;

import com.jmr.terraria.engine.views.drawables.Drawable;

/**
 * Created by Jon on 10/1/15.
 */
public class Item {

	private int typeId;
	private String name;
	private Drawable icon;
	private int maxItemStack;
	private ItemCategory category;

	public Item(int typeId, ItemCategory category, String name, Drawable icon, int maxItemStack) {
		this.typeId = typeId;
		this.category = category;
		this.name = name;
		this.icon = icon;
		this.maxItemStack = maxItemStack;
	}

	public int getTypeId() {
		return typeId;
	}

	public String getName() {
		return name;
	}

	public Drawable getIcon() {
		return icon;
	}

	public int getMaxItemStack() {
		return maxItemStack;
	}

	public ItemCategory getCategory() {
		return category;
	}

	public String toString() {
		return "";
	}

}
