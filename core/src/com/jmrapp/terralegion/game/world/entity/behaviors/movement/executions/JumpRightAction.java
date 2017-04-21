// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 04/21/2017 12:42:00
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions;

import com.jmrapp.terralegion.engine.utils.Timer;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.JumpRightModel;

/** ExecutionAction class created from MMPM action JumpRightAction. */
public class JumpRightAction extends jbt.execution.task.leaf.action.ExecutionAction {
	/**
	 * Value of the parameter "entity" in case its value is specified at
	 * construction time. null otherwise.
	 */
	private Object entity;
	/**
	 * Location, in the context, of the parameter "entity" in case its value is
	 * not specified at construction time. null otherwise.
	 */
	private String entityLoc;

	private float startTime;
	private int tries;

	/**
	 * Constructor. Constructs an instance of JumpRightAction that is able to run a
	 * com
	 * .jmrapp.terralegion.game.world.entity.behaviors.movement.models.JumpRightAction.
	 * 
	 * @param entity
	 *            value of the parameter "entity", or null in case it should be
	 *            read from the context. If null,
	 *            <code>entityLoc<code> cannot be null.
	 * @param entityLoc
	 *            in case <code>entity</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public JumpRightAction(
			JumpRightModel modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent, Object entity,
			String entityLoc) {
		super(modelTask, executor, parent);

		this.entity = entity;
		this.entityLoc = entityLoc;
	}

	/**
	 * Returns the value of the parameter "entity", or null in case it has not
	 * been specified or it cannot be found in the context.
	 */
	public Object getEntity() {
		if (this.entity != null) {
			return this.entity;
		} else {
			return (Object) this.getContext().getVariable(
					this.entityLoc);
		}
	}

	protected void internalSpawn() {
		this.getExecutor().requestInsertionIntoList(jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		System.out.println("Jump Right spawned");
		startTime = 0;
	}

	protected Status internalTick() {
		LivingEntity livingEntity = (LivingEntity) getEntity();
		if (startTime != 0) { //If in jump
			if (Timer.getGameTimeElapsed() - startTime >= 1f) { //If its been 3 seconds
				return Status.SUCCESS;
			} else {
				livingEntity.addVelocity(8, 0);
				return Status.RUNNING;
			}
		}
		if (tries > 3) {
			return Status.FAILURE;
		}
		if (!livingEntity.canJump()) {
			return Status.FAILURE;
		}
		livingEntity.addVelocity(8, 0);
		livingEntity.jump();
		startTime = Timer.getGameTimeElapsed();
		return Status.RUNNING;
	}

	protected void internalTerminate() {
	}

	protected void restoreState(jbt.execution.core.ITaskState state) {
		if (getContext().getVariable("tries") != null) { //If the saved
			tries = (Integer) getContext().getVariable("tries");
		} else {
			tries = 0;
		}
	}

	protected jbt.execution.core.ITaskState storeState() {
		getContext().setVariable("tries", tries + 1);
		if (tries > 3)
			getContext().setVariable("backupJumpRight", true);
		else
			getContext().setVariable("backupJumpRight", false);
		return null;
	}

	protected jbt.execution.core.ITaskState storeTerminationState() {
		return null;
	}
}