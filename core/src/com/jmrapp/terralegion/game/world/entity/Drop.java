package com.jmrapp.terralegion.game.world.entity;

import com.jmrapp.terralegion.engine.world.collision.CollisionInfo;
import com.jmrapp.terralegion.engine.world.collision.CollisionSide;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.engine.world.entity.WorldBody;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.item.Item;

import java.util.HashMap;

/**
 * Created by Jon on 9/30/15.
 */
public class Drop extends TexturedEntity {

    private float DEATH_TIME = 60;
    private static HashMap<Integer, Drop> dropQueue = new HashMap<Integer, Drop>();

    private Item item;
    private boolean onGround;
    private float startTime, pickupWaitTime;
    private int stackCount;

    public Drop(Item item) {
        super(item.getIcon(), 0, 0, BodyType.DYNAMIC, 0f);
        this.item = item;
    }

    public Drop(Item item, Drawable dropIcon) {
        super(dropIcon, 0, 0, BodyType.DYNAMIC, 0f);
        this.item = item;
    }

    private Drop(Item item, int stackCount, float x, float y) {
        super(item.getIcon(), x, y, BodyType.DYNAMIC, 0f);
        this.item = item;
        this.stackCount = stackCount;
        this.startTime = Timer.getGameTimeElapsed();
    }

    private Drop(Item item, Drawable dropIcon, int stackCount, float x, float y) {
        super(dropIcon, x, y, BodyType.DYNAMIC, 0f);
        this.item = item;
        this.stackCount = stackCount;
        this.startTime = Timer.getGameTimeElapsed();
    }

    public void update() {
    }

    @Override
    public boolean collision(WorldBody obj, CollisionInfo info) {
        if (info.getCollisionA() == this) {
            if (info.getCollisionASide() == CollisionSide.BOTTOM) {
                onGround = true;
            }
        } else {
            if (info.getCollisionBSide() == CollisionSide.BOTTOM) {
                onGround = true;
            }
        }
        return true;
    }

    public boolean canPickup() {
        return Timer.getGameTimeElapsed() - startTime > pickupWaitTime;
    }

    public void setPickupWaitTime(float time) {
        pickupWaitTime = time;
    }

    public void checkDeathTime(Chunk chunk) {
        if (Timer.getGameTimeElapsed() - startTime > DEATH_TIME) {
            chunk.removeEntity(this);
            destroy();
        }
    }

    public Item getItem() {
        return item;
    }

    public int getStackCount() {
        return stackCount;
    }

    public void resetOnGround() {
        onGround = false;
    }

    public boolean onGround() {
        return onGround;
    }

    private void set(int stackCount, float x, float y) {
        this.stackCount = stackCount;
        this.x = x;
        this.y = y;
        this.desiredX = x;
        this.desiredY = y;
        this.lastX = x;
        this.lastY = y;
        this.startTime = Timer.getGameTimeElapsed();
        this.onGround = false;
    }

    public void destroy() {
        dropQueue.put(item.getTypeId(), this);
    }

    public Drop getInstance(int id, int stackCount, float x, float y) {
        if (dropQueue.containsKey(id)) {
            Drop drop = dropQueue.remove(id);
            drop.set(stackCount, x, y);
            return drop;
        }
        if (drawable != item.getIcon()) { //If there is a custom drop drawable set which is not the same as the icon drawable
            return new Drop(item, drawable, stackCount, x, y);
        } else {
            return new Drop(item, stackCount, x, y);
        }
    }

}
