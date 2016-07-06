package com.jmrapp.terralegion.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Jon on 12/21/15.
 */
public class DayManager {

	private static final DayManager instance = new DayManager();

	private float worldLightValue = 1f;

	private DayManager() {
	}

	public void update() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
			worldLightValue += 0.05f;
			if (worldLightValue > 1)
				worldLightValue = 1;
		} else if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			worldLightValue -= 0.05f;
			if (worldLightValue < 0)
				worldLightValue = 0;
		}
	}

	public float getWorldLightValue() {
		return worldLightValue;
	}

	public static DayManager getInstance() {
		return instance;
	}

}
