package com.jmr.terraria.engine.views.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {
	
	public void create();
	
	public void update();
	
	public void render(SpriteBatch sb);
	
	public void dispose();
	
	public void resize(int width, int height);

	public void onBackPressed();
	
	public void pause();
	
	public void resume();
	
}
