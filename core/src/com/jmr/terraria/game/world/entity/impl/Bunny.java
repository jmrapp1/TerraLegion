package com.jmr.terraria.game.world.entity.impl;

import com.badlogic.gdx.math.MathUtils;
import com.jmr.terraria.engine.views.drawables.ResourceManager;
import com.jmr.terraria.engine.views.drawables.TexturedDrawable;
import com.jmr.terraria.engine.utils.Timer;
import com.jmr.terraria.engine.world.entity.BodyType;
import com.jmr.terraria.game.world.entity.FriendlyEntity;

/**
 * Created by Jon on 12/19/15.
 */
public class Bunny extends FriendlyEntity {

	private float lastDirectionChange;
	private float moveX = 0;

	public Bunny(float x, float y) {
		super(new TexturedDrawable(ResourceManager.getInstance().getTexture("bunny")), x, y, BodyType.DYNAMIC, 7f, 10, 7, 3.5f);
	}

	@Override
	public void update() {
		if (Timer.getGameTimeElapsed() - lastDirectionChange > 4) {
			if (MathUtils.random(1, 100) > 50) {
				moveX = -speed;
				lastDirectionChange = Timer.getGameTimeElapsed();
			} else {
				moveX = speed;
				lastDirectionChange = Timer.getGameTimeElapsed();
			}
		}
		if (MathUtils.random(1, 100) < 20) {
			if (canJump()) {
				jump();
			}
		}
		addVelocity(moveX, 0);
	}

}

