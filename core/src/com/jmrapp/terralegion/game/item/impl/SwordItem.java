package com.jmrapp.terralegion.game.item.impl;

import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.block.BlockType;

/**
 * Created by Jon on 10/6/15.
 */
public class SwordItem extends CombatItem {

	public SwordItem(ItemType type, String name, Drawable icon, int maxItemStack, float damage, float power, float reach, float useDelay) {
		super(type, name, icon, maxItemStack, damage, power, reach, useDelay);
	}

	public boolean canDamageBlock(BlockType type) {
		return false;
	}

	@Override
	public boolean onUse(World world, float touchX, float touchY) {
		world.getChunkManager().getChunkFromPos(touchX, touchY).damageEntity(touchX, touchY, 0.1f);
		return false;
	}
}
