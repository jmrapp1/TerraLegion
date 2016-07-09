package com.jmrapp.terralegion.game.world.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.world.collision.CollisionInfo;
import com.jmrapp.terralegion.engine.world.collision.CollisionSide;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.engine.world.entity.WorldBody;
import com.jmrapp.terralegion.game.item.impl.ToolItem;
import com.jmrapp.terralegion.game.utils.LightUtils;

/**
 * Created by Jon on 12/21/15.
 */
public abstract class LivingEntity extends TexturedEntity {

	protected static final ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static final float healthBarWidth = 30, healthBarHeight = 5;
    public static final int LEFT = -1, RIGHT = 1;

    private int facingDirection = LEFT;
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
		if (info.getCollisionA() == this) {
			if (info.getCollisionASide() == CollisionSide.BOTTOM) {
				canJump = true;
			}
		} else if (info.getCollisionB() == this) {
			if (info.getCollisionBSide() == CollisionSide.BOTTOM) {
				canJump = true;
			}
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
		sb.draw(drawable.getTextureRegion(),
				isFlipped() ? x : x+drawable.getTextureRegion().getRegionWidth(),
				y,
				isFlipped() ? drawable.getTextureRegion().getRegionWidth() : -drawable.getTextureRegion().getRegionWidth(),
				drawable.getTextureRegion().getRegionHeight());
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

	public boolean canUseTool(ToolItem item) {
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

    public void faceDirection(final int direction) {
        facingDirection = direction;
    }

    public int getFacingDirection() {
        return facingDirection;
    }

    public boolean isFlipped() {
        return facingDirection == RIGHT;
    }

	@Override
	public void addVelocity(float x, float y) {
        if(x < 0 && velX != 0) {
            faceDirection(LEFT);
        } else if(x > 0 && velX != 0) {
            faceDirection(RIGHT);
        }

        super.addVelocity(x, y);
    }
}