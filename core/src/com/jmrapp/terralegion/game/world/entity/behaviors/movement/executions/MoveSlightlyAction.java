// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 04/21/2017 14:00:08
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions;

import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.MoveSlightlyModel;

/** ExecutionAction class created from MMPM action MoveSlightlyAction. */
public class MoveSlightlyAction extends
		jbt.execution.task.leaf.action.ExecutionAction {
	/**
	 * Value of the parameter "entity" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Object entity;
	/**
	 * Location, in the context, of the parameter "entity" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String entityLoc;
	/**
	 * Value of the parameter "direction" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Integer direction;
	/**
	 * Location, in the context, of the parameter "direction" in case its value
	 * is not specified at construction time. null otherwise.
	 */
	private java.lang.String directionLoc;
	/**
	 * Value of the parameter "time" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private java.lang.Float time;
	/**
	 * Location, in the context, of the parameter "time" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private java.lang.String timeLoc;

	private float startTime = 0;

	/**
	 * Constructor. Constructs an instance of MoveSlightlyAction that is able to run a
	 * com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.
	 * MoveSlightlyAction.
	 * 
	 * @param entity
	 *            value of the parameter "entity", or null in case it should be
	 *            read from the context. If null,
	 *            <code>entityLoc<code> cannot be null.
	 * @param entityLoc
	 *            in case <code>entity</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param direction
	 *            value of the parameter "direction", or null in case it should
	 *            be read from the context. If null,
	 *            <code>directionLoc<code> cannot be null.
	 * @param directionLoc
	 *            in case <code>direction</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 * @param time
	 *            value of the parameter "time", or null in case it should be
	 *            read from the context. If null,
	 *            <code>timeLoc<code> cannot be null.
	 * @param timeLoc
	 *            in case <code>time</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public MoveSlightlyAction(
			MoveSlightlyModel modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent, java.lang.Object entity,
			java.lang.String entityLoc, java.lang.Integer direction,
			java.lang.String directionLoc, java.lang.Float time,
			java.lang.String timeLoc) {
		super(modelTask, executor, parent);

		this.entity = entity;
		this.entityLoc = entityLoc;
		this.direction = direction;
		this.directionLoc = directionLoc;
		this.time = time;
		this.timeLoc = timeLoc;
	}

	/**
	 * Returns the value of the parameter "entity", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Object getEntity() {
		if (this.entity != null) {
			return this.entity;
		} else {
			return (java.lang.Object) this.getContext().getVariable(
					this.entityLoc);
		}
	}

	/**
	 * Returns the value of the parameter "direction", or null in case it has
	 * not been specified or it cannot be found in the context.
	 */
	public java.lang.Integer getDirection() {
		if (this.direction != null) {
			return this.direction;
		} else {
			return (java.lang.Integer) this.getContext().getVariable(
					this.directionLoc);
		}
	}

	/**
	 * Returns the value of the parameter "time", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public java.lang.Float getTime() {
		if (this.time != null) {
			return this.time;
		} else {
			return (java.lang.Float) this.getContext()
					.getVariable(this.timeLoc);
		}
	}

	protected void internalSpawn() {
		this.getExecutor().requestInsertionIntoList(jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);

		System.out.println("Move slightly spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		LivingEntity livingEntity = (LivingEntity) getEntity();
		if (startTime != 0) { //If in jump
			if (Timer.getGameTimeElapsed() - startTime >= getTime()) { //If its been 3 seconds
				return Status.SUCCESS;
			} else {
				applyMovement(livingEntity);
				return Status.RUNNING;
			}
		}
		applyMovement(livingEntity);
		startTime = Timer.getGameTimeElapsed();
		System.out.println("Move Slightly Started");
		return Status.RUNNING;
	}

	private void applyMovement(LivingEntity livingEntity) {
		if (getDirection() == 1) {
			livingEntity.addVelocity(-4, 0);
		} else if (getDirection() == 2) {
			livingEntity.addVelocity(4, 0);
		}
	}

	protected void internalTerminate() {

	}

	protected void restoreState(jbt.execution.core.ITaskState state) {

	}

	protected jbt.execution.core.ITaskState storeState() {
		return null;
	}

	protected jbt.execution.core.ITaskState storeTerminationState() {
		return null;
	}
}