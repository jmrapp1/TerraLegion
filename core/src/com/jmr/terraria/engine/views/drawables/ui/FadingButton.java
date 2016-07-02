package com.jmr.terraria.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class FadingButton extends Button {

	protected final FadingTexture texture;
	protected final FadingSprite sprite;

	public FadingButton(FadingTexture texture) {
		super(texture.getX(), texture.getY(), texture.getTexture().getWidth(), texture.getTexture().getHeight());
		this.texture = texture;
		sprite = null;
	}

	public FadingButton(FadingSprite sprite) {
		super(sprite.getPos().x, sprite.getPos().y, sprite.getSprite().getWidth() * sprite.getSprite().getScaleX(), sprite.getSprite().getHeight() * sprite.getSprite().getScaleY());
		this.sprite = sprite;
		this.texture = null;
	}

	@Override
	public boolean isPressed(float xClick, float yClick) {
		return new Rectangle(x, y, width, height).overlaps(new Rectangle(xClick, yClick, 1, 1));
	}

	public void update() {
		if (texture != null)
			texture.update();
		if (sprite != null)
			sprite.update();
	}

	@Override
	public void setPos(float x, float y) {
		super.setPos(x, y);
		texture.setPos(x, y);
	}

	@Override
	public void addPos(float x, float y) {
		super.addPos(x, y);
		texture.addPos(x, y);
	}

	public void render(SpriteBatch sb) {
		if (texture != null)
			texture.render(sb);
		if (sprite != null)
			sprite.render(sb);
	}

	public FadingTexture getFadingTexture() {
		return texture;
	}
	
}
