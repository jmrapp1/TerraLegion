package com.jmrapp.terralegion.engine.views.drawables;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpriteDrawable implements Drawable {

	private final Sprite sprite;

	public SpriteDrawable(Sprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public void update() {

	}

	@Override
	public void render(SpriteBatch sb, Vector2 pos) {
		sprite.setPosition(pos.x, pos.y);
		sprite.draw(sb);
	}

	@Override
	public void render(SpriteBatch sb, float x, float y) {
		sprite.setPosition(x, y);
		sprite.draw(sb);
	}

	@Override
	public void render(SpriteBatch sb, float x, float y, final boolean flipped) {
		render(sb, x, y);
	}

	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public TextureRegion getTextureRegion() {
		return new TextureRegion(sprite);
	}

	@Override
	public Drawable getInstance() {
		return new SpriteDrawable(sprite);
	}

	@Override
	public float getWidth() {
		return sprite.getWidth() * sprite.getScaleX();
	}

	@Override
	public float getHeight() {
		return sprite.getHeight() * sprite.getScaleY();
	}

	@Override
	public Object getRaw() {
		return sprite;
	}
	
}
