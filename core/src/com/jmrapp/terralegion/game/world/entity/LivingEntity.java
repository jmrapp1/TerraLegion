package com.jmrapp.terralegion.game.world.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.engine.views.drawables.AnimationDrawable;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.world.collision.CollisionInfo;
import com.jmrapp.terralegion.engine.world.collision.CollisionSide;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.engine.world.entity.WorldBody;
import com.jmrapp.terralegion.game.item.impl.ToolItem;
import com.jmrapp.terralegion.game.item.impl.UsableItem;
import com.jmrapp.terralegion.game.utils.LightUtils;

/**
 * Created by Jon on 12/21/15.
 */
public abstract class LivingEntity extends TexturedEntity {

	protected static final ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static final float healthBarWidth = 30, healthBarHeight = 5;
    public enum Direction {
		LEFT, RIGHT
	}

    private Direction facingDirection = Direction.LEFT;
	private boolean canJump = true;
	private float health, maxHealth, jumpVelocity;
	private float lastToolUsedTime, lastDamageReceived = Timer.getGameTimeElapsed();

	public LivingEntity(Drawable drawable, float x, float y, BodyType bodyType, float speed, float maxHealth, float health, float jumpVelocity) {
		super(drawable, x, y, bodyType, speed);
		this.health = health;
		this.maxHealth = maxHealth;
		this.jumpVelocity = jumpVelocity;
	}

	@Override
	public boolean collision(WorldBody obj, CollisionInfo info) {
        boolean isThisCollisionA = info.getCollisionA() == this;
        boolean isThisCollisionB = info.getCollisionB() == this;
        boolean isBottomCollisionA = CollisionSide.BOTTOM == info.getCollisionASide();
        boolean isBottomCollisionB = CollisionSide.BOTTOM == info.getCollisionBSide();
		if ((isThisCollisionA || isThisCollisionB) && (isBottomCollisionA || isBottomCollisionB)) {
            canJump = true;
        }
		return true;
	}

	public void damage(float damage) {
		health -= damage;
		lastDamageReceived = Timer.getGameTimeElapsed();
	}

	public void heal(float amount) {
		health += amount;
		if (health > maxHealth)
			health = maxHealth;
	}

	public boolean isDead() {
		return health <= 0;
	}

	@Override
	public void render(SpriteBatch sb, double lightValue) {
		float value = (float) (lightValue < LightUtils.MIN_LIGHT_VALUE ? LightUtils.MIN_LIGHT_VALUE : lightValue);
		sb.setColor(value, value, value, 1);
        sb.draw(drawable.getTextureRegion(), x, y, drawable.getTextureRegion().getRegionWidth(), drawable.getTextureRegion().getRegionHeight());
		sb.setColor(Color.WHITE);

		if (Timer.getGameTimeElapsed() - lastDamageReceived <= 5f) {
			renderHealthBar(sb);
		}
	}

	public void renderHealthBar(SpriteBatch sb) {
		float renderX = x + (drawable.getWidth() / 2) - (healthBarWidth / 2);
		float renderY = y + (drawable.getHeight() + 5);
		float currentHealthWidth = health * (healthBarWidth / maxHealth);
		if (currentHealthWidth < 0)
			currentHealthWidth = 0;
		sb.end();

		shapeRenderer.setProjectionMatrix(sb.getProjectionMatrix());
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(renderX, renderY, healthBarWidth, healthBarHeight);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(renderX, renderY, currentHealthWidth, healthBarHeight);
		shapeRenderer.end();

		sb.begin();
	}

	public void usedTool() {
		lastToolUsedTime = Timer.getGameTimeElapsed();
	}

	public boolean canUseItem(UsableItem item) {
		return Timer.getGameTimeElapsed() - lastToolUsedTime >= item.getUseDelay();
	}

	public void jump() {
		velY = jumpVelocity;
		canJump = false;
	}

	public boolean canJump() {
		return canJump;
	}

	public float getHealth() {
		return health;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getJumpVelocity() {
		return jumpVelocity;
	}

    public void faceDirection(Direction direction) {
        if(!(drawable instanceof AnimationDrawable) && direction != facingDirection) {
            drawable.getTextureRegion().flip(true, false);
        }
        else if(drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            if(facingDirection != direction && direction == Direction.LEFT) {
                animationDrawable.setAnimationByType(AnimationDrawable.Type.WALK_LEFT);
                animationDrawable.update();
            }
            else if(facingDirection != direction && direction == Direction.RIGHT) {
                animationDrawable.setAnimationByType(AnimationDrawable.Type.WALK_RIGHT);
                animationDrawable.update();
            }
        }
        facingDirection = direction;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

	@Override
	public void addVelocity(float x, float y) {
        if(x < 0 && velX != 0) {
            faceDirection(Direction.RIGHT);
        } else if(x > 0 && velX != 0) {
            faceDirection(Direction.LEFT);
        }
        super.addVelocity(x, y);
    }
}