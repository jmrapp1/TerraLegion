package com.jmrapp.terralegion.game.world.entity.ai;

import com.jmrapp.terralegion.game.world.entity.LivingEntity;

/**
 * Acts as a component of an entity mind to perform a particular type of action for an entity
 *
 * Created by jordanb84 on 11/18/16.
 */
public abstract class EntityAction {

    /**
     * Performs the action
     * @return Whether the action has completed one cycle. Should return true when it's completed a cycle to tell the mind it can reevaluate the current action.
     */
    public abstract boolean perform(LivingEntity parentEntity);

}
