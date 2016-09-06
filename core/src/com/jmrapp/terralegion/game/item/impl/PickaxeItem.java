package com.jmrapp.terralegion.game.item.impl;

import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

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

	@Override
	public boolean onUse(World world, float touchX, float touchY) {
		BlockType type = world.getChunkManager().getBlockFromPos(touchX, touchY);
			if (this.canDamageBlock(type)) {
				if (world.getPlayer().canUseItem(this) && world.getChunkManager().damageBlock(touchX, touchY, this.getPower())) {
					world.getPlayer().usedTool();
				}
				return true;
			}

		return false;
	}
}
