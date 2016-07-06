package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Container extends UIElement {

	/** Holds all elements within the container */
	private Array<UIElement> elements = new Array<UIElement>();

	/**
	 * Holds elements and draws them relative to the containers location.
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
     */
	public Container(float x, float y) {
		super(x, y);
	}

	/** Add a UI element to the container.
	 *
	 * @param element The UI element
     */
	public void add(UIElement element) {
		elements.add(element);
	}

	/** @return All elements within the container */
	public Array<UIElement> getAll() {
		return elements;
	}

	/** Updates all elements stored in the container. */
	@Override
	public void update() {
		for (UIElement element : elements)
			element.update();
	}

	/** Set the position of the container elements relative to the new coordinates
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
     */
	@Override
	public void setPos(float x, float y) {
		for (UIElement element : elements)
			element.setPos(x + element.getX(), y + element.getY());
	}

	/**
	 * Draws all elements stored in the container
	 * @param sb The spritebatch
     */
	@Override
	public void render(SpriteBatch sb) {
		for (UIElement element : elements)
			element.render(sb);
	}
	
}
