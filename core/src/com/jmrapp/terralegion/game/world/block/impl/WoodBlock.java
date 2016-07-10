package com.jmrapp.terralegion.game.world.block.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.world.block.Block;
import com.jmrapp.terralegion.game.world.block.BlockManager;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

public class WoodBlock extends Block {

    public WoodBlock(BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, boolean transparent, float initHealth, float resistance) {
        super(type, drawable, lightBlockingAmount, collides, transparent, initHealth, resistance);
    }

    /**
     * When the player breaks a wood block from a tree, all the wood blocks above will also break.
     * @param chunkManager
     * @param chunk
     * @param chunkTileX
     * @param chunkTileY
     */
    @Override
    public void onBreak(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
        super.onBreak(chunkManager, chunk, chunkTileX, chunkTileY);
        // get the real position of the block that the player breaks
        int realX = chunkTileX + (chunk.getStartX() * Chunk.CHUNK_SIZE); //Get real world x
        int realY = chunkTileY + (chunk.getStartY() * Chunk.CHUNK_SIZE); //Get real world y
        // get the chunk of the block above the block that the player breaks (can be != chunk)
        Chunk topBlockChunk = chunkManager.getChunkFromTilePos(realX, (realY+1));
        // get the position of the block above in the chunk
        int topChunkTileX = realX - (topBlockChunk.getStartX() * Chunk.CHUNK_SIZE);
        int topChunkTileY = (realY+1) - (topBlockChunk.getStartY() * Chunk.CHUNK_SIZE);
        // get the blocktype of the block above
        BlockType topBlockType = topBlockChunk.getBlock(topChunkTileX, topChunkTileY);
        if(topBlockType == BlockType.WOOD) { // if block above is wood too
            // break the block above
            topBlockChunk.setBlock(BlockType.AIR, topChunkTileX, topChunkTileY, true);
        }
    }
}
