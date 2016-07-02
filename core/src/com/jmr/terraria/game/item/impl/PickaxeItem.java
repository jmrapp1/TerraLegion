package com.jmr.terraria.game.item.impl;

import com.jmr.terraria.engine.views.drawables.Drawable;
import com.jmr.terraria.game.item.ItemType;
import com.jmr.terraria.game.world.block.BlockType;

/**
 * Created by Jon on 10/6/15.
 */
public class PickaxeItem extends ToolItem {

	public PickaxeItem(ItemType type, String name, Drawable icon, int maxItemStack, float damage, float power, float reach, float useDelay) {
		super(type, name, icon, maxItemStack, damage, power, reach, useDelay);
	}

	public boolean canDamageBlock(BlockType type) {
		return true;
	}

	@Override
	public String toString() {
		return damage + " melee damage\n" + useDelay + " speed\n" + (power * 100) + "% pickaxe power";
	}

}
