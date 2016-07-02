package com.jmr.terraria.engine.utils;

import com.badlogic.gdx.Gdx;

public class Timer {

	/** Singleton instance of the class. */
	private static Timer instance = new Timer();

	/** The total time elapsed since the first call to the update() method. */
	private static float timeElapsed;

	/** The total time elapsed since the game timer was started. */
	private static float gameTime;

	/** The time that the game timer was started. */
	private static boolean startGameTime;

	/** Private constructor; prevents construction outside the class (singleton design pattern) */
	private Timer() {
	}

	/**
	 * Updates the overall elapsed time and the game time.
	 */
	public void update() {
		timeElapsed += Gdx.graphics.getDeltaTime();
		if (startGameTime) {
			gameTime += Gdx.graphics.getDeltaTime();
		}
	}

	/**
	 * @return The overall elapsed time
	 */
	public static float getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * @return The game time elapsed since the timer's start
	 */
	public static float getGameTimeElapsed() {
		return gameTime;
	}

	/**
	 * Start the game timer.
	 */
	public static void startGameTime() {
		startGameTime = true;
	}

	/**
	 * Stop the game timer.
	 */
	public static void stopGameTime() {
		startGameTime = false;
	}


	/**
	 *  Resets and turns off the game timer.
	 */
	public static void resetGameTime() {
		gameTime = 0;
		startGameTime = false;
	}

	/**
	 * Singleton method that returns the single instance of the class.
	 *
	 * @return The Timer instance
	 */
	public static Timer getInstance() {
		return instance;
	}
	
}
