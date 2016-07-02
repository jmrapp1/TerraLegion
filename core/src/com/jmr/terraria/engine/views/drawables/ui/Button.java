package com.jmr.terraria.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button extends UIElement {

	/** The width */
	protected final float width;

	/** The height */
	protected final float height;

	/**
	 * Button that can be drawn to the screen and test for user input on it.
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param width The width
     * @param height The height
     */
	public Button(float x, float y, float width, float height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	/**
	 * Test if the x and y coordinates from user input are inside of the bounds of the button
	 * @param xClick The x coordinate
	 * @param yClick The y coordinate
     * @return If the coordinates are within the bounds of the button
     */
	public boolean isPressed(float xClick, float yClick) {
		return new Rectangle(x, y, width, height).overlaps(new Rectangle(xClick, yClick, 1, 1));
	}

	/** Called each frame. Not abstract so that the button can be used without it being updated and simply
	 * as a way to test for input within certain bounds.
	 */
	@Override
	public void update() {
		
	}

	/** Called each frame. Not abstract so that the button can be used without it being drawn and simply
	 * as a way to test for input within certain bounds.
	 */
	@Override
	public void render(SpriteBatch sb) {
		
	}
	
}
