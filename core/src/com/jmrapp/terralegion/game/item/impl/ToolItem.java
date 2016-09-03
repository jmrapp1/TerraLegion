package com.jmrapp.terralegion.game.item.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.Item;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.block.BlockType;

/**
 * Created by Jon on 10/6/15.
 */
public abstract class ToolItem extends UsableItem {

	/** The amount of damage dealt by the item. */
	protected float damage;

	/** The power the tool has when being used. */
	protected float power;

	protected ItemType type;

	public ToolItem(ItemType type, String name, Drawable icon, int maxItemStack, float damage, float power, float reach, float useDelay) {
		super(type.getId(), ItemCategory.TOOL, name, icon, maxItemStack, useDelay);
		this.damage = damage;
		this.power = power;
		this.type = type;
		this.reach = reach;
		this.useDelay = useDelay;
	}

	public abstract boolean canDamageBlock(BlockType type);

	public float getDamage() {
		return damage;
	}

	public float getPower() {
		return power;
	}

	public ItemType getItemType() {
		return type;
	}

	public String toString() {
		return damage + " melee damage\n" + useDelay + " speed\n" + (power * 100) + "% power";
	}

}
