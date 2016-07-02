package com.jmr.terraria.game.world.block;

import com.jmr.terraria.engine.world.collision.CollisionInfo;
import com.jmr.terraria.engine.world.entity.BodyType;
import com.jmr.terraria.engine.world.entity.WorldBody;
import com.jmr.terraria.game.world.chunk.ChunkManager;

public class BlockBody extends WorldBody {
	
	public BlockBody() {
		super();
	}
	
	public BlockBody(float x, float y, BodyType bodyType) {
		super(x, y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, bodyType);
	}

	public BlockBody(float x, float y) {
		super(x, y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE, BodyType.STATIC);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean collision(WorldBody obj, CollisionInfo info) {
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof BlockBody) {
			BlockBody tb = (BlockBody) o;
			return tb.getBodyType() == bodyType && tb.getX() == x && tb.getY() == y;
		}
		return false;
	}

}
