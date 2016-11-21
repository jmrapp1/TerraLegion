package com.jmrapp.terralegion.game.world.entity.ai.impl;

import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.ai.EntityAction;
import com.jmrapp.terralegion.game.world.entity.ai.EntityMind;

/**
 * Simple wandering mind for peaceful creatures
 * Created by jordanb84 on 11/19/16.
 */
public class PeacefulWanderingMind extends EntityMind {

    private ActionWander wanderAction;

    private boolean jumps;

    public PeacefulWanderingMind(LivingEntity parentEntity, boolean jumps){
        super(parentEntity);
        this.wanderAction.setJumps(jumps);
    }

    @Override
    public EntityAction evaluateNextAction() {
        return this.wanderAction;
    }

    @Override
    public EntityAction setInitialAction() {
        return this.wanderAction;
    }

    @Override
    public void createActions() {
        this.wanderAction = new ActionWander();
    }
}
