package com.jmrapp.terralegion.engine.views.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AtlasRegionDrawable implements Drawable {

	private final TextureAtlas.AtlasRegion atlasRegion;
	private TextureRegion textureRegion;

	public AtlasRegionDrawable(TextureAtlas.AtlasRegion atlasRegion) {
		this.atlasRegion = atlasRegion;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(SpriteBatch sb, Vector2 pos) {
		sb.draw(atlasRegion, pos.x, pos.y);
	}

	@Override
	public void render(SpriteBatch sb, float x, float y) {
		sb.draw(atlasRegion, x, y);
	}

	@Override
	public void render(SpriteBatch sb, float x, float y, final boolean flipped) {
		render(sb, x, y);
	}

	public TextureAtlas.AtlasRegion getTexture() {
		return atlasRegion;
	}

	@Override
	public TextureRegion getTextureRegion() {
		return atlasRegion;
	}

	@Override
	public Drawable getInstance() {
		return new AtlasRegionDrawable(atlasRegion);
	}

	@Override
	public float getWidth() {
		return atlasRegion.packedWidth;
	}

	@Override
	public float getHeight() {
		return atlasRegion.packedHeight;
	}

	@Override
	public Object getRaw() {
		return atlasRegion;
	}

}
