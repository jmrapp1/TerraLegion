package com.jmr.terraria.engine.views.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Drawable {

	public void update();
	
	public void render(SpriteBatch sb, Vector2 pos);

	public void render(SpriteBatch sb, float x, float y);

	public TextureRegion getTextureRegion();

	public Drawable getInstance();
	
	public float getWidth();
	
	public float getHeight();
	
	public Object getRaw();
	
}


