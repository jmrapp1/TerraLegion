package com.jmr.terraria.game.item.impl;

import com.jmr.terraria.engine.views.drawables.Drawable;
import com.jmr.terraria.game.world.block.BlockType;
import com.jmr.terraria.game.item.Item;
import com.jmr.terraria.game.item.ItemCategory;
import com.jmr.terraria.game.item.ItemType;

/**
 * Created by Jon on 10/6/15.
 */
public abstract class ToolItem extends Item {

	/** The amount of damage dealt by the item. */
	protected float damage;

	/** How far away the tool can reach from the origin. */
	protected float reach;

	/** The power the tool has when being used. */
	protected float power;

	protected float useDelay;

	protected ItemType type;

	public ToolItem(ItemType type, String name, Drawable icon, int maxItemStack, float damage, float power, float reach, float useDelay) {
		super(type.getId(), ItemCategory.TOOL, name, icon, maxItemStack);
		this.damage = damage;
		this.power = power;
		this.type = type;
		this.reach = reach;
		this.useDelay = useDelay;
	}

	public float getReach() {
		return reach;
	}

	public float getUseDelay() {
		return useDelay;
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
