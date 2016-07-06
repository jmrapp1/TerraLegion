package com.jmrapp.terralegion.game.world.chunk.gen;

import com.jmrapp.terralegion.engine.world.SimplexNoise;
import com.jmrapp.terralegion.game.world.chunk.Chunk;

public interface WorldGenerator {

	void generate(SimplexNoise noise, Chunk chunk);

	void generate(SimplexNoise noise, Chunk chunk, int x, int y);

}
