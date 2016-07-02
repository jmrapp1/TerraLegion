package com.jmr.terraria.engine.views.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResourceManager {

	private static ResourceManager instance = new ResourceManager();
	private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	private final HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private final HashMap<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
	private final HashMap<String, Drawable> drawables = new HashMap<String, Drawable>();

	private ResourceManager() {
	}
	
	public void loadTexture(String id, String file) {
		Texture t = new Texture(Gdx.files.internal(file));
		t.setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		textures.put(id, t);
	}
	
	public Sprite loadSprite(String id, String file) {
		Sprite sprite = new Sprite(new Texture(Gdx.files.internal(file)));
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		sprites.put(id, sprite);
		return sprite;
	}

	public void loadTexturedDrawable(String id, String file) {
		Texture t = new Texture(Gdx.files.internal(file));
		t.setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		drawables.put(id, new TexturedDrawable(t));
	}

	public void loadAtlasRegionDrawable(String id, TextureAtlas atlas, String region) {
		TextureAtlas.AtlasRegion atlasRegion = atlas.findRegion(region);
		drawables.put(id, new AtlasRegionDrawable(atlasRegion));
	}
	
	public Sprite loadSprite(String id, String file, float scaleX, float scaleY) {
		Sprite sprite = new Sprite(new Texture(Gdx.files.internal(file)));
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		sprite.setScale(scaleX, scaleY);
		sprite.setOrigin(0, 0);
		sprites.put(id, sprite);
		return sprite;
	}
	
	public void loadFont(String id, String fileFNT, String filePNG, Color color, float scale) {
		fonts.put(id, getFont(id, fileFNT, filePNG, color, scale));
	}
	
	public void loadFont(String id, String file, Color color, int size) {
		fonts.put(id, getFont(id, file, color, size));
	}
	
	public BitmapFont getFont(String id, String fileFNT, String filePNG, Color color, float scale) {
		BitmapFont font = new BitmapFont(Gdx.files.internal(fileFNT),Gdx.files.internal(filePNG),false);
		font.setColor(color);
		font.setScale(scale);
		return font;
	}
	
	public BitmapFont getFont(String id, String file, Color color, int size) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(file));
		BitmapFont font = gen.generateFont(size);
		font.setColor(color);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return font;
	}
	
	public void loadFont(String id, BitmapFont font) {
		fonts.put(id, font);
	}
	
	public BitmapFont getFont(String id) {
		return fonts.get(id);
	}
	
	public Texture getTexture(String id) {
		return textures.get(id);
	}

	public Drawable getDrawable(String id) {
		return drawables.get(id);
	}
	
	public Sprite getSprite(String id) {
		return sprites.get(id);
	}
	
	public void dispose() {
		Iterator it = textures.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ((Texture)pair.getValue()).dispose();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    it = fonts.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ((BitmapFont)pair.getValue()).dispose();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public static ResourceManager getInstance() {
		return instance;
	}
	
}
