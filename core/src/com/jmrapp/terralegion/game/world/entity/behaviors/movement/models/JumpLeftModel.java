// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 04/21/2017 12:42:00
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.models;

import com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions.JumpLeftAction;

/** ModelAction class created from MMPM action JumpLeftAction. */
public class JumpLeftModel extends jbt.model.task.leaf.action.ModelAction {
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

	/**
	 * Constructor. Constructs an instance of JumpLeftAction.
	 * 
	 * @param entity
	 *            value of the parameter "entity", or null in case it should be
	 *            read from the context. If null, <code>entityLoc</code> cannot
	 *            be null.
	 * @param entityLoc
	 *            in case <code>entity</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public JumpLeftModel(jbt.model.core.ModelTask guard, Object entity,
						 String entityLoc) {
		super(guard);
		this.entity = entity;
		this.entityLoc = entityLoc;
	}

	/**
	 * Returns a
	 * com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions
	 * .JumpLeftAction task that is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new JumpLeftAction(
				this, executor, parent, this.entity, this.entityLoc);
	}
}