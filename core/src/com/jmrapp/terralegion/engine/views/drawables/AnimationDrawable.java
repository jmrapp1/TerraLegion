
package com.jmrapp.terralegion.engine.views.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * 
 * @author Simon Gwerder
 *
 */
public class AnimationDrawable implements Drawable {

    private ObjectMap<Type, Animation> animations = new ObjectMap<Type, Animation>();
    private Animation animation;

    public enum Type {
        WALK_NORTH, WALK_EAST, WALK_SOUTH, WALK_WEST
    }

    private float stateTime;

    public void reset() {
        stateTime = 0f;
    }

    public int getKeyFrameIndex() {
        return animation.getKeyFrameIndex(stateTime);
    }

    public void setAnimationByType(Type type) {
        animation = animations.get(type);
    }

    public AnimationDrawable(SpriteSheet sheet) {
        final int startX = 0;
        final int startY = 0;
        final int frames = 3;

        Array<TextureRegion> tempSpritesNorth = AnimationDrawable.grabSprites(sheet, startX, startY, frames);
        Array<TextureRegion> tempSpritesSouth = AnimationDrawable.grabSprites(sheet, startX, startY + 2, frames);
        Array<TextureRegion> tempSpritesWest = AnimationDrawable.grabSprites(sheet, startX, startY + 1, frames, true);
        Array<TextureRegion> tempSpritesEast = AnimationDrawable.grabSprites(sheet, startX, startY + 1, frames);

        AnimationDrawable.addMiddleReversed(tempSpritesNorth, false);
        AnimationDrawable.addMiddleReversed(tempSpritesSouth, false);
        AnimationDrawable.addMiddleReversed(tempSpritesWest, false);
        AnimationDrawable.addMiddleReversed(tempSpritesEast, false);

        animations.put(Type.WALK_SOUTH, new Animation(1f / (frames * 2), tempSpritesSouth));
        animations.put(Type.WALK_NORTH, new Animation(1f / (frames * 2), tempSpritesNorth));
        animations.put(Type.WALK_WEST, new Animation(1f / (frames * 2), tempSpritesWest));
        animations.put(Type.WALK_EAST, new Animation(1f / (frames * 2), tempSpritesEast));

        for(Animation animation : animations.values()) {
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }

        setAnimationByType(Type.WALK_SOUTH);
    }

    private static Array<TextureRegion> grabSprites(SpriteSheet sheet, int startX, int y, int length) {
        return AnimationDrawable.grabSprites(sheet, startX, y, length, false);
    }

    private static Array<TextureRegion> grabSprites(SpriteSheet sheet, int startX, int y, int length, boolean mirrored) {
        Array<TextureRegion> sprites = new Array<TextureRegion>();
        length += startX;
        for (int x = startX; x < length; x++) {
            sprites.add(sheet.getSprite(x, y, mirrored));
        }
        return sprites;
    }

    private static void addMiddleReversed(Array<TextureRegion> frames, boolean keepFirst) {
        if (frames.size < 3) {
            return;
        }
        Array<TextureRegion> middleReversed = new Array<TextureRegion>(frames);
        if (!keepFirst) {
            middleReversed.removeIndex(0);
        }
        middleReversed.removeIndex(middleReversed.size - 1);
        middleReversed.reverse();
        frames.addAll(middleReversed);
    }

    @Override
    public void update() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void render(SpriteBatch sb, Vector2 pos) {
        sb.draw(getTextureRegion(), pos.x, pos.y);
    }

    @Override
    public void render(SpriteBatch sb, float x, float y) {
        sb.draw(getTextureRegion(), x, y);
    }

    @Override
    public TextureRegion getTextureRegion() {
        return animation.getKeyFrame(stateTime);
    }

    @Override
    public Drawable getInstance() {
        return new AnimationDrawable(null); // TODO
    }

    @Override
    public float getWidth() {
        return animation.getKeyFrame(stateTime).getRegionWidth();
    }

    @Override
    public float getHeight() {
        return animation.getKeyFrame(stateTime).getRegionHeight();
    }

    @Override
    public Object getRaw() {
        return animation;
    }

}
