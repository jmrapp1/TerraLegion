package com.jmrapp.terralegion.game.world.block.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.world.block.Block;
import com.jmrapp.terralegion.game.world.block.BlockManager;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.utils.LightUtils;

public class LightBlock extends Block {

	private float lightAmount;
	
	public LightBlock(BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, float lightAmount, boolean transparent, float initHealth, float resistance) {
		super(type, drawable, lightBlockingAmount, collides, transparent, initHealth, resistance);
		this.lightAmount = lightAmount;
	}

	@Override
	public void onBreak(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
		super.onBreak(chunkManager, chunk, chunkTileX, chunkTileY);
		int worldX = chunkTileX + (chunk.getStartX() * Chunk.CHUNK_SIZE);
		int worldY = chunkTileY + (chunk.getStartY() * Chunk.CHUNK_SIZE);
		LightUtils.extinguishLight(chunkManager, worldX, worldY, lightAmount - (lightBlockingAmount - BlockManager.getBlock(BlockType.AIR).getLightBlockingAmount()), true);
	}
	
	@Override
	public void onPlace(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
		super.onPlace(chunkManager, chunk, chunkTileX, chunkTileY);
		int worldX = chunkTileX + (chunk.getStartX() * Chunk.CHUNK_SIZE);
		int worldY = chunkTileY + (chunk.getStartY() * Chunk.CHUNK_SIZE);
		LightUtils.calculateChunkLight(chunkManager, worldX, worldY, lightAmount, true);
	}

	@Override
	public void onLoad(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
		onPlace(chunkManager, chunk, chunkTileX, chunkTileY);
	}
	
	
}
