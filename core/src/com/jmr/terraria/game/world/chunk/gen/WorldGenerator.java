package com.jmr.terraria.game.world.chunk.gen;

import com.jmr.terraria.engine.world.SimplexNoise;
import com.jmr.terraria.game.world.chunk.Chunk;

public interface WorldGenerator {

	void generate(SimplexNoise noise, Chunk chunk);

	void generate(SimplexNoise noise, Chunk chunk, int x, int y);

}
