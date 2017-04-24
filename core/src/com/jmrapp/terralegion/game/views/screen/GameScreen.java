package com.jmrapp.terralegion.game.views.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;
import com.jmrapp.terralegion.engine.utils.Settings;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.engine.views.screens.Screen;
import com.jmrapp.terralegion.engine.views.screens.ScreenManager;
import com.jmrapp.terralegion.game.io.WorldIO;
import com.jmrapp.terralegion.game.views.ui.GameHud;
import com.jmrapp.terralegion.game.world.World;

public class GameScreen implements Screen {

	private OrthoCamera camera;
	private ShaderProgram program;
	private static World world;
	private GameHud gameHud;
	
	public GameScreen(String worldFileName) {
		world = new World(worldFileName);
	}
	
	public GameScreen(String worldFileName, long seed) {
		world = new World(worldFileName, seed);
	}
	
	@Override
	public void create() {
		//Load UI
		gameHud = new GameHud(this, world);

		ShaderProgram.pedantic = false;
		program = new ShaderProgram(Gdx.files.internal("shaders/sepia.vsh"), Gdx.files.internal("shaders/sepia.fsh"));
		
		if (!program.isCompiled()) {
			System.out.println(program.getLog());
		}
		
		camera = new OrthoCamera();
		Timer.startGameTime();
		resize(Settings.getWidth(), Settings.getHeight());
	}

	public void update() {		
		camera.update();
		gameHud.update(camera);

		if (Gdx.input.isKeyJustPressed(Keys.I)) {
			System.out.println("Saving");
			WorldIO.saveWorld(world);
		} else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			System.out.println("Saving");
			WorldIO.saveWorld(world);
			ScreenManager.goBack();
		}

		world.update(camera);
	}
	
	@Override
	public void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameHud.renderBackground();
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		world.render(sb, camera);
		sb.end();

		gameHud.render(camera);
	}
	
	@Override
	public void resize(int width, int height) {
		camera.resize();
		gameHud.resize(width, height);
	}

	@Override
	public void dispose() {
		gameHud.dispose();
		Timer.stopGameTime();
	}

	@Override
	public void onBackPressed() {
		WorldIO.saveWorld(world);
		Timer.stopGameTime();
	}

	@Override
	public void pause() {
		WorldIO.saveWorld(world);
		Timer.stopGameTime();
	}

	@Override
	public void resume() {
		gameHud.resume();
		Timer.startGameTime();
	}

	public static World getCurrentWorld() { //Terribly ugly, but short term needed for AI behavior testing
		return world;
	}

}
