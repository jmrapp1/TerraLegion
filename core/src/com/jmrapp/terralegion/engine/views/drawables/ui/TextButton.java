package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;

public class TextButton extends PictureButton {

	private final BitmapFont font;
	private String text;
	
	public TextButton(String text, Drawable drawable, BitmapFont font, float x, float y) {
		super(drawable, x, y);
		this.text = text;
		this.font = font;
	}
	
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		float width = font.getBounds(text).width;
		float height = font.getBounds(text).height;
		font.draw(sb, text, x + (drawable.getWidth() / 2) - (width / 2), y + (drawable.getHeight() / 2) + (height / 2));
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

}
