package com.jmr.terraria.game.item.impl;

import com.jmr.terraria.engine.views.drawables.Drawable;
import com.jmr.terraria.game.world.block.BlockType;
import com.jmr.terraria.game.item.Item;
import com.jmr.terraria.game.item.ItemCategory;

/**
 * Created by Jon on 10/1/15.
 */
public class BlockItem extends Item {

	private BlockType type;

	public BlockItem(BlockType type, ItemCategory category, String name, Drawable icon, int maxStack) {
		super(type.getId(), category, name, icon, maxStack);
		this.type = type;
	}

	public BlockType getBlockType() {
		return type;
	}

	public String toString() {
		return "Block\nCan be placed";
	}

}
