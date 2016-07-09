package com.jmrapp.terralegion.engine.views.drawables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Drawable {

	void update();
	
	void render(SpriteBatch sb, Vector2 pos);

	void render(SpriteBatch sb, float x, float y);

	TextureRegion getTextureRegion();

	Drawable getInstance();
	
	float getWidth();
	
	float getHeight();
	
	Object getRaw();
	
}


