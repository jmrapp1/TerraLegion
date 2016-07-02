package com.jmr.terraria.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteElement extends UIElement {

	private final Sprite sprite;
	
	public SpriteElement(Sprite sprite, float x, float y) {
		super(x, y);
		this.sprite = sprite;
		sprite.setPosition(x, y);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sprite.draw(sb);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public float getWidth() {
		return sprite.getRegionWidth();
	}
	
	public float getHeight() {
		return sprite.getRegionHeight();
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
	
}