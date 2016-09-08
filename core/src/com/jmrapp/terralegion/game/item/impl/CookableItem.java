package com.jmrapp.terralegion.game.item.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.item.ItemManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.world.entity.Drop;
import com.jmrapp.terralegion.game.world.entity.DropManager;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.impl.Bunny;

import java.util.Random;

/**
 * Created by jordanb84 on 9/3/16.
 *
 * Cookable items allow the item to be turned into another type when clicked on a furnace.
 * The resulting type is dropped as a droppable item around the furnace.
 */
public class CookableItem extends UsableItem {

    private int returnItem;

    private Random positionRandom;

    public CookableItem(int typeId, int returnItem, ItemCategory category, String name, Drawable icon, int maxItemStack, float useDelay, float reach){
        super(typeId, category, name, icon, maxItemStack, useDelay, reach);
        this.returnItem = returnItem;
        positionRandom = new Random();
    }

    @Override
    public boolean onUse(World world, float touchX, float touchY) {
        ChunkManager chunkManager = world.getChunkManager();

        if(chunkManager.getBlockFromPos(touchX, touchY) == BlockType.STOVE) {
            world.getPlayer().getInventory().removeItemStack(this, 1);
            Drop cookedDrop = DropManager.getInstance().getDrop(returnItem, 1, touchX, touchY);
            cookedDrop.setVelocity(positionRandom.nextInt(10), positionRandom.nextInt(50));
            chunkManager.getChunkFromPos(touchX, touchY).addEntity(cookedDrop);
        }

        return false;
    }

}
