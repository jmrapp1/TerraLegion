package com.jmr.terraria.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jmr.terraria.engine.camera.OrthoCamera;
import com.jmr.terraria.engine.utils.Settings;
import com.jmr.terraria.engine.utils.Timer;
import com.jmr.terraria.engine.views.screens.Screen;
import com.jmr.terraria.engine.world.SimplexNoise;
import com.jmr.terraria.game.world.chunk.Chunk;

public class NoiseVisualizationScreen implements Screen {

	private OrthoCamera camera;
	private ShapeRenderer renderer = new ShapeRenderer();

	private float[][] map = new float[64][64];
	private float[] hillMap = new float[256];

	@Override
	public void create() {
		camera = new OrthoCamera();
		resize(Settings.getWidth(), Settings.getHeight());
		SimplexNoise noise = new SimplexNoise();

		//USED FOR HILL TERRAIN GENERATION
		for (int x = 0; x < hillMap.length; x++) {
			float freq = 1.0f / (Chunk.CHUNK_SIZE * 1.5f);
			float i = noise.generate(x * freq, x * freq, 2, .35f, 1);
			int finalY = (int) (i * 25) + 24;
			hillMap[x] = finalY;
		}

		//USED FOR ORE AND CAVE GENERATION
		/*for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				float freq = 1.0f / (Chunk.CHUNK_SIZE);
				float i = noise.generate(x * freq, y * freq, 2, .5f, 1f);
				map[x][y] = Math.abs(i);
			}
		}*/
	}

	public void update() {
		camera.update();
		if (Gdx.input.isKeyPressed(Keys.A)) {
			camera.setPosition(camera.getPos().x - 4, camera.getPos().y);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			camera.setPosition(camera.getPos().x + 4, camera.getPos().y);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			camera.setPosition(camera.getPos().x, camera.getPos().y + 4);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			camera.setPosition(camera.getPos().x, camera.getPos().y - 4);
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Filled);

		//USED FOR HILL TERRAIN GENERATION
		renderer.setColor(1, 1, 1, 1);
		for (int x = 0; x < hillMap.length; x++) {
			renderer.rect(x * 10, hillMap[x] * 10, 10, 10);
		}

		//USED FOR ORE AND CAVE GENERATION
		/*for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				float val = map[x][y];
				if (val > .3f) {
					val = 1f;
					renderer.setColor(val, val, val, 1);
					renderer.rect(x * 10, y * 10, 10, 10);
				}
			}
		}*/
		renderer.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.resize();
	}

	@Override
	public void dispose() {
		Timer.stopGameTime();
	}

	@Override
	public void onBackPressed() {
		Timer.stopGameTime();
	}

	@Override
	public void pause() {
		Timer.stopGameTime();
	}

	@Override
	public void resume() {
		Timer.startGameTime();
	}

}
