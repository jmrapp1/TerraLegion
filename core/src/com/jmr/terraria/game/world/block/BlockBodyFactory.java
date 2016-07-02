package com.jmr.terraria.game.world.block;

import com.badlogic.gdx.utils.Array;
import com.jmr.terraria.engine.world.entity.BodyType;
import com.jmr.terraria.game.world.chunk.ChunkManager;

public class BlockBodyFactory {

	public static final BlockBodyFactory instance = new BlockBodyFactory();
	private Array<BlockBody> bodies = new Array<BlockBody>();
	
	private BlockBodyFactory() {
	}
	
	public BlockBody getBody(float x, float y, BodyType bodyType) {
		if (bodies.size > 0) {
			BlockBody body = bodies.removeIndex(0);
			body.set(x, y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, bodyType);
			return body;
		}
		return new BlockBody(x, y, bodyType);
	}
	
	public BlockBody getBody() {
		if (bodies.size > 0) {
			BlockBody body = bodies.removeIndex(0);
			return body;
		}
		return new BlockBody();
	}
	
	public void destroy(BlockBody body) {
		bodies.add(body);
	}
	
}
