
package com.jmrapp.terralegion.engine.views.drawables;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * 
 * @author Simon Gwerder
 *
 */
public class SpriteSheet implements Disposable {
    private Texture texture;
    private boolean ownsTexture;
    private TextureRegion[][] sprites, mirrored;
    private int spriteWidth, spriteHeight, gapWidth, gapHeight;
    private int rowCount, columnCount;
    
    public SpriteSheet(FileHandle file, int spriteWidth, int spriteHeight, int gapWidth, int gapHeight) {
        load(file, spriteWidth, spriteHeight, gapWidth, gapHeight);
    }
    
    public SpriteSheet(Texture texture, int spriteWidth, int spriteHeight, int gapWidth, int gapHeight) {
        load(texture, spriteWidth, spriteHeight, gapWidth, gapHeight);
    }
    
    public void load(FileHandle file, int spriteWidth, int spriteHeight, int gapWidth, int gapHeight) {
        load(new Texture(file), spriteWidth, spriteHeight, gapWidth, gapHeight);
        ownsTexture = true;
    }
    
    public void load(Texture texture, int spriteWidth, int spriteHeight, int gapWidth, int gapHeight) {
        dispose();
        
        this.texture = texture;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.gapWidth = gapWidth;
        this.gapHeight = gapHeight;
        
        final int width = texture.getWidth();
        final int height = texture.getHeight();
        rowCount = ((width - spriteWidth) / (spriteWidth + gapWidth)) + 1;
        columnCount = ((height - spriteHeight) / (spriteHeight + gapHeight)) + 1;
        if ((height - spriteHeight) % (spriteHeight + gapHeight) != 0) {
            columnCount++;
        }
        
        sprites = new TextureRegion[columnCount][rowCount];
        mirrored = new TextureRegion[columnCount][rowCount];
        for (int column = 0; column < columnCount; column++) {
            for (int row = 0; row < rowCount; row++) {
                sprites[column][row] = extractSprite(row, column);
                TextureRegion mirroredSprite = new TextureRegion(sprites[column][row]);
                mirroredSprite.flip(true, false);
                mirrored[column][row] = mirroredSprite;
            }
        }
    }
    
    private TextureRegion extractSprite(int row, int column) {
        int x = row * (spriteWidth + gapWidth) + gapWidth;
        int y = column * (spriteHeight + gapHeight) + gapHeight;
        return new TextureRegion(texture, x, y, spriteWidth, spriteHeight);
    }
    
    @Override
    public void dispose() {
        if (ownsTexture) {
            texture.dispose();
        }
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    public int getSpriteWidth() {
        return spriteWidth;
    }
    
    public int getSpriteHeight() {
        return spriteHeight;
    }
    
    /**
     * Returns sprite count horizontally.
     */
    public int getRowCount() {
        return rowCount;
    }
    
    /**
     * Returns sprite count vertically.
     */
    public int getColumnCount() {
        return columnCount;
    }
    
    /**
     * Returns sprite at specified row and column.
     * 
     * @param row
     *            Horizontal position of the sprite.
     * @param column
     *            Vertical position of the sprite.
     */
    public TextureRegion getSprite(int row, int column) {
        return getSprite(row, column, false);
    }
    
    /**
     * Returns sprite at specified row and column.
     * 
     * @param row
     *            Horizontal position of the sprite.
     * @param column
     *            Vertical position of the sprite.
     * @param mirror
     *            True to get horizontally flipped sprite image.
     */
    public TextureRegion getSprite(int row, int column, boolean mirror) {
        return mirror ? mirrored[column][row] : sprites[column][row];
    }
}
