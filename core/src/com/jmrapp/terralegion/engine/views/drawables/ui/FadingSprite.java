package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.engine.utils.Timer;

public class FadingSprite extends UIElement {

	private final Sprite sprite;
	private float minAmount, speed, lastTime, subAmount, alpha, maxAmount;
	private boolean loop = true;
	private boolean fadeOut = true;
	
	public FadingSprite(Sprite sprite, float x, float y, float minAmount, float maxAmount, float subAmount, float speed) {
		super(x, y);
		this.sprite = sprite;
		alpha = 1f;
		sprite.setPosition(x, y);
		sprite.setAlpha(alpha);
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.subAmount = subAmount;
		this.speed = speed;
	}
	
	public FadingSprite(Sprite sprite, float x, float y, float minAmount, float maxAmount, float subAmount, float speed, float alpha) {
		super(x, y);
		this.sprite = sprite;
		this.alpha = alpha;
		sprite.setAlpha(alpha);
		sprite.setPosition(x, y);
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.subAmount = subAmount;
		this.speed = speed;
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
			sprite.setAlpha(alpha);
			lastTime = Timer.getTimeElapsed();
		}
	}
	
	public void render(SpriteBatch sb) {
		sprite.draw(sb);
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void setPos(float x, float y) {
		super.setPos(x, y);
		sprite.setPosition(x, y);
	}
	
	@Override
	public void addPos(float x, float y) {
		super.addPos(x, y);
		sprite.setPosition(sprite.getX() + x, sprite.getY() + y);
	}
	
	public Vector2 getPos() {
		return new Vector2(sprite.getX(), sprite.getY());
	}
	
	
}
