package com.jmrapp.terralegion.game.world.chunk.gen;

import com.jmrapp.terralegion.engine.world.SimplexNoise;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;

public class OreGenerator implements WorldGenerator {

	@Override
	public void generate(SimplexNoise noise, Chunk chunk) {
		for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
			for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
				generate(noise, chunk, x, y);
			}
		}
	}

	public void generate(SimplexNoise noise, Chunk chunk, int x, int y) {
		int totalX = x + (Chunk.CHUNK_SIZE * chunk.getStartX());
		int totalY = y + (Chunk.CHUNK_SIZE * chunk.getStartY());

		float caveFreq = 1.0f / (Chunk.CHUNK_SIZE / 3.85f);
		float caveVal = Math.abs(noise.generate(totalX * caveFreq, totalY * caveFreq, 4, .5f, 1f));

		if (caveVal <= .35f) {

			float oreFreq = 1.0f / (Chunk.CHUNK_SIZE / 3.5f);
			float oreVal = Math.abs(noise.generate(x * oreFreq, y * oreFreq, 2, .9f, 1f));

			if (oreVal >= .5f) {
				chunk.setBlock(BlockType.COAL, x, y, false);
				chunk.setWall(BlockType.STONE_WALL, x, y, false);
			} else {
				float dirtFreq = 1.0f / (Chunk.CHUNK_SIZE);
				float dirtVal = Math.abs(noise.generate(x * dirtFreq, y * dirtFreq, 2, .5f, 1f));
				if (dirtVal > .3f) {
					chunk.setBlock(BlockType.DIRT, x, y, false);
					chunk.setWall(BlockType.DIRT_WALL, x, y, false);
				} else {
					chunk.setBlock(BlockType.STONE, x, y, false);
					chunk.setWall(BlockType.STONE_WALL, x, y, false);
				}
			}
		} else {
			chunk.setWall(BlockType.STONE_WALL, x, y, false);
		}
	}

}
