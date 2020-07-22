package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MultilineLabel extends UIElement {

	private final BitmapFont font;
	private final boolean center;
	private TextElement[] lines;

	public MultilineLabel(String text, BitmapFont font, float x, float y, boolean center) {
		super(x, y);
		this.font = font;
		this.center = center;
		setText(text);
	}

	public void render(SpriteBatch sb) {
		if (center) {
			for (int i = 0; i < lines.length; i++) {
				float width = lines[i].getWidth();
				float height = lines[i].getHeight();
				lines[i].render(sb, x - (width / 2), y - (height / 2) - (height + 5) * i);
			}
		} else {
			for (int i = 0; i < lines.length; i++) {
				float height = lines[i].getHeight();
				lines[i].render(sb, x, y - (height + 5) * i);
			}
		}
	}

	public void setText(String text) {
		String[] strings = text.split("\n");
		lines = new TextElement[strings.length];
		for (int i = 0; i < strings.length; i++) {
			lines[i] = new TextElement(font, strings[i]);
		}
	}

	public BitmapFont getFont() {
		return font;
	}

	public TextElement[] getText() {
		return lines;
	}

	@Override
	public void update() {
	}

}
