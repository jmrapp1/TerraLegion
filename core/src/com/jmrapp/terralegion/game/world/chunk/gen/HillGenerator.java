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

	private boolean sandyGeneration;

	private final int maxCactusHeight = 4;

	public HillGenerator(OreGenerator oreGen) {
		this.oreGen = oreGen;
	}

	public void generate(SimplexNoise noise, Chunk chunk) {
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			generate(noise, chunk, x, x);
		}

	}

	public void generateSandy(SimplexNoise noise, Chunk chunk){
		sandyGeneration = true;
		generate(noise, chunk);
		sandyGeneration = false;
	}

	public void generate(SimplexNoise noise, Chunk chunk, int x, int y) {
		int totalX = x + (Chunk.CHUNK_SIZE * chunk.getStartX());
		float freq = 1.0f / (Chunk.CHUNK_SIZE * 1.25f);
		float i = noise.generate(totalX * freq, totalX * freq, 6, .25f, 1);
		int finalY = (int) (i * 25) + 24;

		//SET GRASS
		chunk.setBlock(sandyGeneration ? BlockType.SAND : BlockType.GRASS, x, finalY, false);
		chunk.setWall(sandyGeneration ? BlockType.SAND_WALL : BlockType.DIRT_WALL, x, finalY, false);

		if(grassRandom.nextInt(5) == 1) {
			if(!sandyGeneration) {
				chunk.setBlock(BlockType.COVER_GRASS, x, finalY + 1, false);
			}else{
				for(int cactus = 0; cactus < grassRandom.nextInt(maxCactusHeight); cactus++) {
					chunk.setBlock(BlockType.CACTUS, x, finalY + (1 + cactus), false);
				}
			}
		}

		//DETERMINE WHETHER TO GENERATE TREE
		if(!sandyGeneration) {
			float treeFreq = 1.0f / Chunk.CHUNK_SIZE;
			float treeValLast = Math.abs(noise.generate((totalX - 1) * treeFreq, (totalX - 1) * treeFreq, 3, .75f, 1f));
			float treeVal = Math.abs(noise.generate(totalX * treeFreq, totalX * treeFreq, 3, .75f, 1f));
			float treeValNext = Math.abs(noise.generate((totalX + 1) * treeFreq, (totalX + 1) * treeFreq, 3, .75f, 1f));

			if (treeVal > treeValLast && treeVal > treeValNext) { //Peak. Spawn tree
				int treeY = finalY + 1;
				TreeGenerator.generateTree(chunk, x, treeY);
			}
		}

		//GENERATE BELOW
		for (int j = 0; j < finalY; j++) { //Set blocks below hill
			if (finalY - j <= 3) {
				float dirtFreq = 1.0f / (Chunk.CHUNK_SIZE);
				float dirtVal = Math.abs(noise.generate(x * dirtFreq, y * dirtFreq, 2, .5f, 1f));
				if (dirtVal > .3f) {
					chunk.setBlock(sandyGeneration ? BlockType.SAND : BlockType.DIRT, x, j, false);
					chunk.setWall(sandyGeneration ? BlockType.SAND_WALL : BlockType.DIRT_WALL, x, j, false);
				} else {
					chunk.setBlock(sandyGeneration ? BlockType.SAND_STONE : BlockType.STONE, x, j, false);
					chunk.setWall(sandyGeneration ? BlockType.SAND_WALL : BlockType.STONE_WALL, x, j, false);
				}
			} else {
				oreGen.generate(noise, chunk, x, j);
			}
		}
	}

}