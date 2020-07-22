package com.jmrapp.terralegion.engine.views.drawables.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextElement {

    private BitmapFont font;
    private GlyphLayout textLayout;
    private String text;

    public TextElement(BitmapFont font, String text) {
        this.font = font;
        this.textLayout = new GlyphLayout();
        setText(text);
    }

    public void render(SpriteBatch sb, float x, float y) {
        font.draw(sb, textLayout, x, y);
    }

    public void setText(String text) {
        this.text = text;
        textLayout.setText(font, text);
    }

    public String getText() {
        return text;
    }

    public float getWidth() {
        return textLayout.width;
    }

    public float getHeight() {
        return textLayout.height;
    }

}
