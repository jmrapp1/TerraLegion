package com.jmrapp.terralegion.engine.views.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {
	
	void create();
	
	void update();
	
	void render(SpriteBatch sb);
	
	void dispose();
	
	void resize(int width, int height);

	void onBackPressed();
	
	void pause();
	
	void resume();
	
}
