package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MultilineLabel extends UIElement {

	private final BitmapFont font;
	private final boolean center;
	private String[] text;

	public MultilineLabel(String text, BitmapFont font, float x, float y, boolean center) {
		super(x, y);
		setText(text);
		this.font = font;
		this.center = center;
	}

	public void render(SpriteBatch sb) {
		if (center) {
			for (int i = 0; i < text.length; i++) {
				float width = font.getBounds(text[i]).width;
				float height = font.getBounds(text[i]).height;
				font.draw(sb, text[i], x - (width / 2), y - (height / 2) - (height + 5) * i);
			}
		} else {
			for (int i = 0; i < text.length; i++) {
				float height = font.getBounds(text[i]).height;
				font.draw(sb, text[i], x, y - (height + 5) * i);
			}
		}
	}

	public void setText(String text) {
		this.text = text.split("\n");
	}

	public BitmapFont getFont() {
		return font;
	}


	public String[] getText() {
		return text;
	}

	@Override
	public void update() {
	}

}
