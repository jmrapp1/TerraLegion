package com.jmrapp.terralegion.engine.world.collision;

import com.badlogic.gdx.utils.Array;
import com.jmrapp.terralegion.engine.world.entity.WorldBody;

public class CollisionInfoFactory {

	public static final CollisionInfoFactory instance = new CollisionInfoFactory();
	private Array<CollisionInfo> infos = new Array<CollisionInfo>();

	private CollisionInfoFactory() {
	}
	
	public CollisionInfo getCollisionInfo(WorldBody wa, WorldBody wb, CollisionSide ca, CollisionSide cb) {
		if (infos.size > 0) {
			CollisionInfo info = infos.removeIndex(0);
			info.set(wa, wb, ca, cb);
			return info;
		}
		return new CollisionInfo(wa, wb, ca, cb);
	}
	
	public void destroy(CollisionInfo info) {
		infos.add(info);
	}
	
}
