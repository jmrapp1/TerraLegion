package com.jmr.terraria.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Label extends UIElement {
	
	private final BitmapFont font;
	private final boolean center;
	private String text;
	
	public Label(String text, BitmapFont font, float x, float y, boolean center) {
		super(x, y);
		this.text = text;
		this.font = font;
		this.center = center;
	}
	
	public void render(SpriteBatch sb) {
		if (center) {
			float width = font.getBounds(text).width;
			float height = font.getBounds(text).height;
			font.draw(sb, text, x - (width / 2), y - (height / 2));
		} else {
			font.draw(sb, text, x, y);
		}
	}	
	
	public void setText(String text) {
		this.text = text;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	
	public String getText() {
		return text;
	}

	@Override
	public void update() {
	}
	
}
