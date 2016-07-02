package com.jmr.terraria.game.world.chunk.gen;

import com.jmr.terraria.engine.world.SimplexNoise;
import com.jmr.terraria.game.world.block.BlockType;

public class HillGenerator implements WorldGenerator {

	private OreGenerator oreGen;

	public HillGenerator(OreGenerator oreGen) {
		this.oreGen = oreGen;
	}

	public void generate(SimplexNoise noise, com.jmr.terraria.game.world.chunk.Chunk chunk) {
		for (int x = 0; x < com.jmr.terraria.game.world.chunk.Chunk.CHUNK_SIZE; x++) {
			generate(noise, chunk, x, x);
		}

	}

	public void generate(SimplexNoise noise, com.jmr.terraria.game.world.chunk.Chunk chunk, int x, int y) {
		int totalX = x + (com.jmr.terraria.game.world.chunk.Chunk.CHUNK_SIZE * chunk.getStartX());
		float freq = 1.0f / (com.jmr.terraria.game.world.chunk.Chunk.CHUNK_SIZE * 1.25f);
		float i = noise.generate(totalX * freq, totalX * freq, 6, .25f, 1);
		int finalY = (int) (i * 25) + 24;

		//SET GRASS
		chunk.setBlock(BlockType.GRASS, x, finalY, false);
		chunk.setWall(BlockType.DIRT_WALL, x, finalY, false);

		//DETERMINE WHETHER TO GENERATE TREE
		float treeFreq = 1.0f / com.jmr.terraria.game.world.chunk.Chunk.CHUNK_SIZE;
		float treeValLast = Math.abs(noise.generate((totalX - 1) * treeFreq, (totalX - 1) * treeFreq, 3, .75f, 1f));
		float treeVal = Math.abs(noise.generate(totalX * treeFreq, totalX * treeFreq, 3, .75f, 1f));
		float treeValNext = Math.abs(noise.generate((totalX + 1) * treeFreq, (totalX + 1) * treeFreq, 3, .75f, 1f));

		if (treeVal > treeValLast && treeVal > treeValNext) { //Peak. Spawn tree
			int treeY = finalY + 1;
			chunk.setBlock(BlockType.WOOD, x, treeY, false);
			chunk.setBlock(BlockType.WOOD, x, treeY + 1, false);
			chunk.setBlock(BlockType.WOOD, x, treeY + 2, false);
			chunk.setBlock(BlockType.WOOD, x, treeY + 3, false);
			chunk.setBlock(BlockType.LEAVES, x, treeY + 4, false);
			chunk.setBlock(BlockType.LEAVES, x - 1, treeY + 3, false);
			chunk.setBlock(BlockType.LEAVES, x + 1, treeY + 3, false);
		}

		//GENERATE BELOW
		for (int j = 0; j < finalY; j++) { //Set blocks below hill
			if (finalY - j <= 3) {
				float dirtFreq = 1.0f / (com.jmr.terraria.game.world.chunk.Chunk.CHUNK_SIZE);
				float dirtVal = Math.abs(noise.generate(x * dirtFreq, y * dirtFreq, 2, .5f, 1f));
				if (dirtVal > .3f) {
					chunk.setBlock(BlockType.DIRT, x, j, false);
					chunk.setWall(BlockType.DIRT_WALL, x, j, false);
				} else {
					chunk.setBlock(BlockType.STONE, x, j, false);
					chunk.setWall(BlockType.STONE_WALL, x, j, false);
				}
			} else {
				oreGen.generate(noise, chunk, x, j);
			}
		}
	}

}