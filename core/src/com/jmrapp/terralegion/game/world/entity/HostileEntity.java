package com.jmrapp.terralegion.game.world.entity;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;

/**
 * Created by Jon on 12/21/15.
 */
public abstract class HostileEntity extends LivingEntity {

	public HostileEntity(Drawable drawable, float x, float y, BodyType bodyType, float speed, float maxHealth, float health, float jumpVelocity) {
		super(drawable, x, y, bodyType, speed, maxHealth, health, jumpVelocity);
	}

}
