package com.jmrapp.terralegion.game.world.block.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.utils.Direction;
import com.jmrapp.terralegion.game.world.block.Block;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

public class GrassBlock extends Block {

    public GrassBlock(BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, boolean transparent, float initHealth, float resistance) {
        super(type, drawable, lightBlockingAmount, collides, transparent, initHealth, resistance);
    }

    @Override
    public void onPlace(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
        super.onPlace(chunkManager, chunk, chunkTileX, chunkTileY);
        chunk.setReplaceNearby(chunkTileX, chunkTileY, Direction.BOTTOM, BlockType.GRASS, BlockType.DIRT);
    }
}
