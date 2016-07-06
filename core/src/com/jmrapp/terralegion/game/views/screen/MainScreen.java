package com.jmrapp.terralegion.game.views.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;
import com.jmrapp.terralegion.engine.utils.Settings;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.SpriteDrawable;
import com.jmrapp.terralegion.engine.views.drawables.ui.TextButton;
import com.jmrapp.terralegion.engine.views.screens.Screen;
import com.jmrapp.terralegion.engine.views.screens.ScreenManager;

public class MainScreen implements Screen {

	private Sprite bg = ResourceManager.getInstance().getSprite("menuBg");
	private OrthoCamera camera = new OrthoCamera();
	private TextButton newWorldBtn = new TextButton("New World", new SpriteDrawable(ResourceManager.getInstance().getSprite("btnBg")), ResourceManager.getInstance().getFont("font"), Settings.getWidth() / 2 - 125, 250);
	private TextButton loadWorldBtn = new TextButton("Load World", new SpriteDrawable(ResourceManager.getInstance().getSprite("btnBg")), ResourceManager.getInstance().getFont("font"), Settings.getWidth() / 2 - 125, 175);
	
	@Override
	public void create() {
		bg.setPosition(0, 0);
	}

	@Override
	public void update() {
		camera.update();
		if (Gdx.input.isTouched()) {
			float touchX = camera.unprojectXCoordinate(Gdx.input.getX(), Gdx.input.getY());
			float touchY = camera.unprojectYCoordinate(Gdx.input.getX(), Gdx.input.getY());
			if (newWorldBtn.isPressed(touchX, touchY)) {
				ScreenManager.setScreen(new GameScreen("MyWorld", 123456789));
			} else if (loadWorldBtn.isPressed(touchX, touchY)) {
				ScreenManager.setScreen(new GameScreen("MyWorld"));
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		bg.draw(sb);
		newWorldBtn.render(sb);
		loadWorldBtn.render(sb);
		sb.end();
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		camera.resize();
	}

	@Override
	public void onBackPressed() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

}
