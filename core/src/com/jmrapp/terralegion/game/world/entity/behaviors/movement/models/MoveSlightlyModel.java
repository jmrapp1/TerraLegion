// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 04/21/2017 14:00:08
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.models;

import com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions.MoveSlightlyAction;

/** ModelAction class created from MMPM action MoveSlightlyAction. */
public class MoveSlightlyModel extends jbt.model.task.leaf.action.ModelAction {
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

	/**
	 * Constructor. Constructs an instance of MoveSlightlyAction.
	 * 
	 * @param entity
	 *            value of the parameter "entity", or null in case it should be
	 *            read from the context. If null, <code>entityLoc</code> cannot
	 *            be null.
	 * @param entityLoc
	 *            in case <code>entity</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 * @param direction
	 *            value of the parameter "direction", or null in case it should
	 *            be read from the context. If null, <code>directionLoc</code>
	 *            cannot be null.
	 * @param directionLoc
	 *            in case <code>direction</code> is null, this variable
	 *            represents the place in the context where the parameter's
	 *            value will be retrieved from.
	 * @param time
	 *            value of the parameter "time", or null in case it should be
	 *            read from the context. If null, <code>timeLoc</code> cannot be
	 *            null.
	 * @param timeLoc
	 *            in case <code>time</code> is null, this variable represents
	 *            the place in the context where the parameter's value will be
	 *            retrieved from.
	 */
	public MoveSlightlyModel(jbt.model.core.ModelTask guard,
							 java.lang.Object entity, java.lang.String entityLoc,
							 java.lang.Integer direction, java.lang.String directionLoc,
							 java.lang.Float time, java.lang.String timeLoc) {
		super(guard);
		this.entity = entity;
		this.entityLoc = entityLoc;
		this.direction = direction;
		this.directionLoc = directionLoc;
		this.time = time;
		this.timeLoc = timeLoc;
	}

	/**
	 * Returns a
	 * com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions
	 * .MoveSlightlyAction task that is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new MoveSlightlyAction(
				this, executor, parent, this.entity, this.entityLoc,
				this.direction, this.directionLoc, this.time, this.timeLoc);
	}
}