package com.jmrapp.terralegion.game.world.entity.impl;

import com.badlogic.gdx.math.MathUtils;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.TexturedDrawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.game.world.entity.FriendlyEntity;
import com.jmrapp.terralegion.game.world.entity.ai.impl.PeacefulWanderingMind;

/**
 * Created by Jon on 12/19/15.
 */
public class Bunny extends FriendlyEntity {

	private float lastDirectionChange;
	private float moveX = 0;

	public Bunny(float x, float y) {
		super(new TexturedDrawable(ResourceManager.getInstance().getTexture("bunny")), x, y, BodyType.DYNAMIC, 7f, 10, 7, 3.5f, null);
		this.setEntityMind(new PeacefulWanderingMind(this, true));
	}

	@Override
	public void update() {
		super.update();
	}

}

