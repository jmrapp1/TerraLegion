package com.jmrapp.terralegion.game.world.block;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;

public class Wall extends Block {

	public Wall(BlockType type, Drawable drawable, float initHealth, float resistance) {
		super(type, drawable, 0, false, false, initHealth, resistance);
	}
	
	@Override
	public boolean isWall() {
		return true;
	}

}
