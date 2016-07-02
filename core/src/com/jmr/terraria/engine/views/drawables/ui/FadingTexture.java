package com.jmr.terraria.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmr.terraria.engine.utils.Timer;

public class FadingTexture extends UIElement {

	private final Texture texture;
	private float minAmount, speed, lastTime, subAmount, alpha, maxAmount;
	private boolean loop = true;
	private boolean fadeOut = true;
	
	public FadingTexture(Texture texture, float x, float y, float minAmount, float maxAmount, float subAmount, float speed) {
		super(x, y);
		this.texture = texture;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.subAmount = subAmount;
		this.speed = speed;
		alpha = 1f;
	}
	
	public FadingTexture(Texture texture, float x, float y, float minAmount, float maxAmount, float subAmount, float speed, float alpha) {
		super(x, y);
		this.texture = texture;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.subAmount = subAmount;
		this.speed = speed;
		this.alpha = alpha;
	}
	
	public void setLoop(boolean b) {
		loop = b;
	}
	
	public void setFadeOut(boolean b) {
		fadeOut = b;
	}
	
	public void update() {
		if (Timer.getTimeElapsed() - lastTime > speed) {
			if (loop) {
				alpha -= subAmount;
				if (alpha <= minAmount) {
					alpha = minAmount;
					subAmount = -subAmount;
				} else if (alpha >= maxAmount) {
					alpha = maxAmount;
					subAmount = -subAmount;
				}
			} else {
				if (fadeOut) {
					alpha -= subAmount;
					if (alpha <= minAmount) {
						alpha = minAmount;
					}
				} else {
					alpha += subAmount;
					if (alpha >= maxAmount) {
						alpha = maxAmount;
					}
				}
			}
			lastTime = Timer.getTimeElapsed();
		}
	}
	
	public void render(SpriteBatch sb) {
		Color color = sb.getColor();
		sb.setColor(color.r, color.g, color.b, alpha);
		sb.draw(texture, x, y);
		sb.setColor(color);
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
}
