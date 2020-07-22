package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;

public class TextButton extends PictureButton {

	private final BitmapFont font;
	private TextElement text;
	
	public TextButton(String text, Drawable drawable, BitmapFont font, float x, float y) {
		super(drawable, x, y);
		this.font = font;
		this.text = new TextElement(font, text);
	}
	
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		float width = text.getWidth();
		float height = text.getHeight();
		text.render(sb, x + (drawable.getWidth() / 2) - (width / 2), y + (drawable.getHeight() / 2) + (height / 2));
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public TextElement getText() {
		return text;
	}

}
