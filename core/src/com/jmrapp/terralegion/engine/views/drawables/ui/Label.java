package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Label extends UIElement {
	
	private final BitmapFont font;
	private final boolean center;
	private TextElement text;
	
	public Label(String text, BitmapFont font, float x, float y, boolean center) {
		super(x, y);
		this.font = font;
		this.center = center;
		this.text = new TextElement(font, text);
	}
	
	public void render(SpriteBatch sb) {
		if (center) {
			float width = text.getWidth();
			float height = text.getHeight();
			text.render(sb, x - (width / 2), y - (height / 2));
		} else {
			text.render(sb, x, y);
		}
	}	
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public BitmapFont getFont() {
		return font;
	}

	public TextElement getText() {
		return text;
	}

	@Override
	public void update() {
	}
	
}
