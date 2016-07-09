package com.jmrapp.terralegion.game.world.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.engine.world.entity.WorldBody;
import com.jmrapp.terralegion.game.utils.LightUtils;

public abstract class TexturedEntity extends WorldBody {

	protected Drawable drawable;
	protected float speed;
	
	public TexturedEntity(Drawable drawable, float x, float y, BodyType bodyType, float speed) {
		super(x, y, drawable.getWidth(), drawable.getHeight(), bodyType);
		this.drawable = drawable;
		this.speed = speed;
	}

	public void render(SpriteBatch sb, double lightValue) {
		float value = (float) (lightValue < LightUtils.MIN_LIGHT_VALUE ? LightUtils.MIN_LIGHT_VALUE : lightValue);
		sb.setColor(value, value, value, 1);
		drawable.render(sb, x, y);
		sb.setColor(Color.WHITE);
	}

	public float getSpeed() {
		return speed;
	}

}
