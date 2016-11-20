package com.jmrapp.terralegion.game.world.entity.ai.impl;

import com.badlogic.gdx.math.MathUtils;
import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.ai.EntityAction;

import java.util.Random;

/**
 * Basic wandering action, which makes the entity wander randomly in both directions
 * Created by root on 11/18/16.
 */
public class ActionWander extends EntityAction {

    private float lastDirectionChange;

    private Random movementRandom = new Random();

    private boolean jumps;

    @Override
    public boolean perform(LivingEntity parentEntity){
        float moveX = 0;

        if (Timer.getGameTimeElapsed() - lastDirectionChange > 4) {
            if (MathUtils.random(1, 100) > 50) {
                moveX = -parentEntity.getSpeed();
                lastDirectionChange = Timer.getGameTimeElapsed();
            } else {
                moveX = parentEntity.getSpeed();
                lastDirectionChange = Timer.getGameTimeElapsed();
            }
        }

        if ((this.jumps) && MathUtils.random(1, 100) < 20) {
            if (parentEntity.canJump()) {
                parentEntity.jump();
            }
        }

        parentEntity.addVelocity(moveX, 0);
        return true;
    }

    public void setJumps(boolean jumps){
        this.jumps = jumps;
    }
}
