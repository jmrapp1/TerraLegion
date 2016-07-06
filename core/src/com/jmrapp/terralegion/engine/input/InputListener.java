package com.jmrapp.terralegion.engine.input;

import com.jmrapp.terralegion.engine.camera.OrthoCamera;

public interface InputListener {

	/**
	 * Called when a mouse or finger presses the screen.
	 *
	 * @param cam The game camera associated with the press
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param button The mouse button pressed
     * @return Whether to stop other events listeners from being notified
     */
	boolean onMousePressed(OrthoCamera cam, float x, float y, int button);

	/**
	 * Called when a mouse or finger presses and releases on the screen
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param button The mouse button pressed
	 * @return Whether to stop other events listeners from being notified
	 */
	boolean onMouseClicked(float x, float y, int button);
	
}
