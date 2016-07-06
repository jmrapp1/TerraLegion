package com.jmrapp.terralegion.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;

public class InputController implements GestureListener {

	/** Singleton instance of the class */
	private static InputController instance = new InputController();

	/** Holds listeners that wait for an input event */
	private static Array<InputListener> listeners = new Array<InputListener>();

	/** Holds all input processors and allows each to take input simultaneously */
	private final InputMultiplexer processors = new InputMultiplexer();

	/** Singleton constructor */
	private InputController() {
		processors.addProcessor(new GestureDetector(this)); //Add a gesture detector to the input processor list
		Gdx.input.setInputProcessor(processors); //Set the processors
	}

	/**
	 * Add an InputListener to accept user input events.
	 *
	 * @param listener The input listener
	 */
	public static void addListener(InputListener listener) {
		listeners.add(listener);
	}

	/**
	 * Check for input within a certain camera's viewport. This will automatically
	 * convert the input position to a position relative to the camera.
	 * @param cam
	 */
	public void checkInput(OrthoCamera cam) {
		if (Gdx.input.isTouched()) { //If it's touched
			int button = getButtonPressed(); //Get the button
			Vector2 pos = cam.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY()); //Get relative coordinates
			for (InputListener l : listeners) //Notify all listeners
				if(l.onMousePressed(cam, pos.x, pos.y, button))
					break;
		}
	}

	/**
	 * Returns the last input that was pressed.
	 *
	 * @return an integer representing either Buttons.LEFT, Buttons.MIDDLE, or Buttons.RIGHT. -1 if not one of the buttons.
	 */
	private int getButtonPressed() {
		if (Gdx.input.isButtonPressed(Buttons.LEFT))
			return Buttons.LEFT;
		else if (Gdx.input.isButtonPressed(Buttons.MIDDLE))
			return Buttons.MIDDLE;
		else if (Gdx.input.isButtonPressed(Buttons.RIGHT))
			return Buttons.RIGHT;
		return -1;
	}

	/** Remove all input listeners. */
	public void clearListeners() {
		listeners.clear();
	}

	/**
	 * Singleton method that returns the single instance of the class.
	 *
	 * @return The single class instance
	 */
	public static InputController getInstance() {
		return instance;
	}

	/**
	 * Add an input processor to accept input events
	 *
	 * @param ip The processor
	 */
	public void addInputProcessor(InputProcessor ip) {
		processors.addProcessor(ip);
		Gdx.input.setInputProcessor(processors);
	}

	/**
	 * Remove an input processor
	 *
	 * @param ip The processor
	 */
	public void removeInputProcessor(InputProcessor ip) {
		processors.removeProcessor(ip);
		Gdx.input.setInputProcessor(processors);
	}

	/** Clear all processors except for the GestureDetector. */
	public void clearProcessors() {
		processors.clear();
		processors.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(processors);
	}

	@Override
	public boolean fling(float arg0, float arg1, int arg2) {
		return false;
	}

	@Override
	public boolean longPress(float arg0, float arg1) {
		return false;
	}

	@Override
	public boolean pan(float arg0, float arg1, float arg2, float arg3) {
		return false;
	}

	@Override
	public boolean panStop(float arg0, float arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 arg0, Vector2 arg1, Vector2 arg2, Vector2 arg3) {
		return false;
	}

	@Override
	public boolean tap(float arg0, float arg1, int arg2, int button) {
		for (InputListener l : listeners) //Notify all listeners of the tap
			if(l.onMouseClicked(Gdx.input.getX(), Gdx.input.getY(), button))
				break;
		return false;
	}

	@Override
	public boolean touchDown(float arg0, float arg1, int arg2, int arg3) {
		return false;
	}

	@Override
	public boolean zoom(float arg0, float arg1) {
		return false;
	}

}
