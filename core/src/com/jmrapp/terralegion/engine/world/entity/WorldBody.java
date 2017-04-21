package com.jmrapp.terralegion.engine.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.jmrapp.terralegion.engine.world.collision.CollisionInfo;

public abstract class WorldBody {

	protected float x, y, desiredX, desiredY, lastX, lastY, accelX, accelY, velX, velY;
	protected float width, height, friction = -1;
	protected BodyType bodyType;
	protected Rectangle bound, leftBound, rightBound, topBound, bottomBound, desiredBound, topDesiredBound, bottomDesiredBound;
	
	public WorldBody() {
		leftBound = new Rectangle();
		rightBound = new Rectangle();
		topBound = new Rectangle();
		bottomBound = new Rectangle();
		bound = new Rectangle();
		desiredBound = new Rectangle();
		topDesiredBound = new Rectangle();
		bottomDesiredBound = new Rectangle();
	}
	
	public WorldBody(float x, float y, float width, float height, BodyType bodyType) {
		this.x = x;
		this.y = y;
		this.desiredX = x;
		this.desiredY = y;
		this.lastX = x;
		this.lastY = y;
		this.bodyType = bodyType;
		this.width = width;
		this.height = height;
		leftBound = new Rectangle();
		rightBound = new Rectangle();
		topBound = new Rectangle();
		bottomBound = new Rectangle();
		bound = new Rectangle();
		desiredBound = new Rectangle();
		topDesiredBound = new Rectangle();
		bottomDesiredBound = new Rectangle();
	}
	
	public void updateVel() {
		velX += accelX * Gdx.graphics.getDeltaTime();
		velY += accelY * Gdx.graphics.getDeltaTime();
		desiredX += velX;
		desiredY += velY;
	}
	
	public void updatePos() {
		x = desiredX;
		y = desiredY;
		lastX = x;
		lastY = y;
	}
	
	public abstract void update();
	
	public abstract boolean collision(WorldBody obj, CollisionInfo info);
	
	public void setFriction(float f) {
		friction = f;
	}
	
	public float getFriction() {
		return friction;
	}
	
	public void setVelocity(float x, float y) {
		velX = x;
		velY = y;
	}
	
	public void setAcceleration(float x, float y) {
		accelX = x;
		accelY = y;
	}

	public void addVelocity(float x, float y) {
		velX += x * Gdx.graphics.getDeltaTime();
		velY += y * Gdx.graphics.getDeltaTime();
	}

	public void forcePosition(float x, float y) {
		this.x = x;
		this.y = y;
		this.lastX = x;
		this.lastY = y;
		this.desiredX = x;
		this.desiredY = y;
	}

	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
		
	}
	
	public void set(float x, float y, float width, float height, BodyType bodyType) {
		this.x = x;
		this.y = y;
		this.desiredX = x;
		this.desiredY = y;
		this.lastX = x;
		this.lastY = y;
		velX = 0;
		velY = 0;
		accelX = 0;
		accelY = 0;
		this.width = width;
		this.height = height;
		this.bodyType = bodyType;
	}

	public void setDesired(float x, float y) {
		desiredX = x;
		desiredY = y;
	}

	public void resetLastXLocation() {
		desiredX = lastX;
	}
	
	public void resetLastYLocation() {
		desiredY = lastY;
	}
	
	public float getAccelX() {
		return accelX;
	}

	public float getAccelY() {
		return accelY;
	}
	
	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getDesiredX() {
		return desiredX;
	}

	public float getDesiredY() {
		return desiredY;
	}
	
	public BodyType getBodyType() {
		return bodyType;
	}
	
	public Rectangle getBounds() {
		bound.set(x, y, width, height);
		return bound;
	}
	
	public Rectangle getLeftBound() {
		leftBound.set(x - 5, y + 5, 5, height - 10);
		return leftBound;
	}
	
	public Rectangle getRightBound() {
		rightBound.set(x + width, y + 5, 7, height - 10);
		return rightBound;
	}
	
	public Rectangle getBottomBound() {
		bottomBound.set(x, y - 5, width, 5);
		return bottomBound;
	}
	
	public Rectangle getTopBound() {
		topBound.set(x, y + height, width, 5);
		return topBound;
	}
	
	public Rectangle getDesiredBounds() {
		desiredBound.set(desiredX, desiredY, width, height);
		return desiredBound;
	}
	
	public Rectangle getBottomDesiredBound() {
		bottomDesiredBound.set(desiredX, desiredY - 5, width, 5);
		return bottomBound;
	}
	
	public Rectangle getTopDesiredBound() {
		topDesiredBound.set(desiredX, desiredY + height, width, 5);
		return topBound;
	}
}
