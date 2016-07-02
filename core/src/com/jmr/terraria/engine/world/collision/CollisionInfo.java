package com.jmr.terraria.engine.world.collision;

import com.jmr.terraria.engine.world.entity.WorldBody;

public class CollisionInfo {
	
	private WorldBody wa, wb;
	private CollisionSide ca, cb;
	
	public CollisionInfo(WorldBody wa, WorldBody wb, CollisionSide ca, CollisionSide cb) {
		this.wa = wa;
		this.wb = wb;
		this.ca = ca;
		this.cb = cb;
	}

	public void set(WorldBody wa, WorldBody wb, CollisionSide ca, CollisionSide cb) {
		this.wa = wa;
		this.wb = wb;
		this.ca = ca;
		this.cb = cb;
	}
	
	public WorldBody getCollisionA() {
		return wa;
	}
	
	public WorldBody getCollisionB() {
		return wb;
	}
	
	public CollisionSide getCollisionASide() {
		return ca;
	}
	
	public CollisionSide getCollisionBSide() {
		return cb;
	}
	
}
