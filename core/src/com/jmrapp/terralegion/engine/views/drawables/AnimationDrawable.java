
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
        WALK_UP, WALK_RIGHT, WALK_DOWN, WALK_LEFT
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

        Array<TextureRegion> tempSpritesUp = AnimationDrawable.grabSprites(sheet, startX, startY, frames);
        Array<TextureRegion> tempSpritesDown = AnimationDrawable.grabSprites(sheet, startX, startY + 2, frames);
        Array<TextureRegion> tempSpritesLeft = AnimationDrawable.grabSprites(sheet, startX, startY + 1, frames, true);
        Array<TextureRegion> tempSpritesRight = AnimationDrawable.grabSprites(sheet, startX, startY + 1, frames);

        AnimationDrawable.addMiddleReversed(tempSpritesUp, false);
        AnimationDrawable.addMiddleReversed(tempSpritesDown, false);
        AnimationDrawable.addMiddleReversed(tempSpritesLeft, false);
        AnimationDrawable.addMiddleReversed(tempSpritesRight, false);

        animations.put(Type.WALK_DOWN, new Animation(1f / (frames * 1.5f), tempSpritesDown));
        animations.put(Type.WALK_UP, new Animation(1f / (frames * 1.5f), tempSpritesUp));
        animations.put(Type.WALK_LEFT, new Animation(1f / (frames * 1.5f), tempSpritesLeft));
        animations.put(Type.WALK_RIGHT, new Animation(1f / (frames * 1.5f), tempSpritesRight));

        for(Animation animation : animations.values()) {
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }

        setAnimationByType(Type.WALK_DOWN); // default animation
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
        return new AnimationDrawable(null); // TODO might cause NPE
    }

    @Override
    public float getWidth() {
        return animation.getKeyFrame(stateTime).getRegionWidth() - 2; // TODO fix size of player_animated.png to fit within 1 unit
    }

    @Override
    public float getHeight() {
        return animation.getKeyFrame(stateTime).getRegionHeight() - 4;  // TODO fix size of player_animated.png to fit within 1 unit
    }

    @Override
    public Object getRaw() {
        return animation;
    }

}
