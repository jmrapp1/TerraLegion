// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 04/21/2017 14:00:08
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions;

import com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.TryBackupJumpModel;

/** ExecutionCondition class created from MMPM condition TryBackupJumpAction. */
public class TryBackupJumpAction extends
		jbt.execution.task.leaf.condition.ExecutionCondition {
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
	 * Constructor. Constructs an instance of TryBackupJumpAction that is able to run
	 * a com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.
	 * TryBackupJumpAction.
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
	 */
	public TryBackupJumpAction(
			TryBackupJumpModel modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent, java.lang.Object entity,
			java.lang.String entityLoc, java.lang.Integer direction,
			java.lang.String directionLoc) {
		super(modelTask, executor, parent);

		this.entity = entity;
		this.entityLoc = entityLoc;
		this.direction = direction;
		this.directionLoc = directionLoc;
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

	protected void internalSpawn() {
		this.getExecutor().requestInsertionIntoList(jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		System.out.println("Try backup jump spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		if (getDirection() == 1) {
			if (getContext().getVariable("backupJumpLeft") != null) {
				boolean tryIt = (Boolean) getContext().getVariable("backupJumpLeft");
				if (tryIt) {
					System.out.println("Try backup jump LEFT");
					return Status.SUCCESS;
				}
			}
		} else {
			if (getContext().getVariable("backupJumpRight") != null) {
				boolean tryIt = (Boolean) getContext().getVariable("backupJumpRight");
				if (tryIt) {
					System.out.println("Try backup jump Right");
					return Status.SUCCESS;
				}
			}
		}
		return Status.FAILURE;
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