package com.jmrapp.terralegion.game.item.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

/**
 * Will be used for farming crops.
 *
 * Created by jordanb84 on 8/5/16.
 */
public class SeedItem extends UsableItem {

    public SeedItem(int typeId, ItemCategory category, String name, Drawable icon, int maxItemStack, float useDelay){
        super(typeId, category, name, icon, maxItemStack, useDelay);
    }

    @Override
    public boolean onUse(World world, float touchX, float touchY) {
        ChunkManager manager = world.getChunkManager();

        int tileX = manager.pixelToTilePosition(touchX);
        int tileY = manager.pixelToTilePosition(touchY);

        BlockType plantBlock = manager.getBlockFromTilePos(tileX, tileY);
        BlockType aboveBlock = manager.getBlockFromTilePos(tileX, tileY + 1);

        if(plantBlock == BlockType.GRASS || plantBlock == BlockType.DIRT && aboveBlock == BlockType.AIR){
            manager.setBlock(BlockType.CROP_BLOCK_0, tileX, tileY + 1, true);

            world.getPlayer().getInventory().removeItemStack(this, 1);
        }

        return false;
    }
}