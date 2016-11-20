package com.jmrapp.terralegion.game.world.entity.ai;

import com.jmrapp.terralegion.game.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Acts as a means of managing a list of entity actions and choosing the next action when appropriate
 * Created by jordanb84 on 11/18/16.
 */
public abstract class EntityMind {

    private List<EntityAction> actions = new ArrayList<EntityAction>();

    private EntityAction currentAction;

    private final LivingEntity parentEntity;

    public EntityMind(LivingEntity parentEntity){
        this.parentEntity = parentEntity;

        this.createActions();

        this.currentAction = this.setInitialAction();
        System.out.println("Set action to " + this.currentAction + "/" + this.setInitialAction());
    }

    /**
     * Updates the current action and chooses the next action if the current
     * has completed one cycle
     */
    public void update(){
        boolean currentActionCycleCompleted = (this.currentAction.perform(this.parentEntity));

        if(currentActionCycleCompleted){
            this.currentAction = this.evaluateNextAction();
        }
    }

    /**
     * For setting up actions before the constructor uses them
     */
    public abstract void createActions();

    public abstract EntityAction setInitialAction();

    /**
     * Registers a new action for the mind
     */
    private void registerAction(EntityAction action){
        this.actions.add(action);
    }

    /**
     * Decides the next action for the entity
     * @return The selected action
     */
    public abstract EntityAction evaluateNextAction();

}
