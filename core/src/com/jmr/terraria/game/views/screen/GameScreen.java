package com.jmr.terraria.game.views.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jmr.terraria.engine.camera.OrthoCamera;
import com.jmr.terraria.engine.utils.Settings;
import com.jmr.terraria.engine.utils.Timer;
import com.jmr.terraria.engine.views.screens.Screen;
import com.jmr.terraria.game.io.WorldIO;
import com.jmr.terraria.game.views.ui.GameHud;
import com.jmr.terraria.game.world.World;

public class GameScreen implements Screen {

	private OrthoCamera camera;
	private ShaderProgram program;
	private World world;
	private GameHud gameHud;
	
	public GameScreen(String worldFileName) {
		world = new World(worldFileName);
		world.getPlayer().getInventory().load();
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
			world.getPlayer().getInventory().save();
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
		world.getPlayer().getInventory().save();
		Timer.stopGameTime();
	}

	@Override
	public void pause() {
		WorldIO.saveWorld(world);
		world.getPlayer().getInventory().save();
		Timer.stopGameTime();
	}

	@Override
	public void resume() {
		gameHud.resume();
		Timer.startGameTime();
	}

}
