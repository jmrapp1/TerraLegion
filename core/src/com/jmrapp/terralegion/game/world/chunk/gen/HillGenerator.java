package com.jmrapp.terralegion.game.world.chunk.gen;

import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.engine.world.SimplexNoise;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Random;

public class HillGenerator implements WorldGenerator {

	private OreGenerator oreGen;

	private Random grassRandom = new Random();

	public HillGenerator(OreGenerator oreGen) {
		this.oreGen = oreGen;
	}

	public void generate(SimplexNoise noise, Chunk chunk) {
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			generate(noise, chunk, x, x);
		}

	}

	public void generate(SimplexNoise noise, Chunk chunk, int x, int y) {
		int totalX = x + (Chunk.CHUNK_SIZE * chunk.getStartX());
		float freq = 1.0f / (Chunk.CHUNK_SIZE * 1.25f);
		float i = noise.generate(totalX * freq, totalX * freq, 6, .25f, 1);
		int finalY = (int) (i * 25) + 24;

		//SET GRASS
		chunk.setBlock(BlockType.GRASS, x, finalY, false);
		chunk.setWall(BlockType.DIRT_WALL, x, finalY, false);

		if(this.grassRandom.nextInt(5) == 1){
			chunk.setBlock(BlockType.COVER_GRASS, x, finalY + 1, false);
		}

		//DETERMINE WHETHER TO GENERATE TREE
		float treeFreq = 1.0f / Chunk.CHUNK_SIZE;
		float treeValLast = Math.abs(noise.generate((totalX - 1) * treeFreq, (totalX - 1) * treeFreq, 3, .75f, 1f));
		float treeVal = Math.abs(noise.generate(totalX * treeFreq, totalX * treeFreq, 3, .75f, 1f));
		float treeValNext = Math.abs(noise.generate((totalX + 1) * treeFreq, (totalX + 1) * treeFreq, 3, .75f, 1f));

		if (treeVal > treeValLast && treeVal > treeValNext) { //Peak. Spawn tree
			int treeY = finalY + 1;
			TreeGenerator.generateTree(chunk, x, treeY);
		}

		//GENERATE BELOW
		for (int j = 0; j < finalY; j++) { //Set blocks below hill
			if (finalY - j <= 3) {
				float dirtFreq = 1.0f / (Chunk.CHUNK_SIZE);
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