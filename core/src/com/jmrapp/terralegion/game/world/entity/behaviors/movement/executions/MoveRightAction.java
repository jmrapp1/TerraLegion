// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 04/21/2017 12:42:00
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.executions;

import com.jmrapp.terralegion.game.views.screen.GameScreen;
import com.jmrapp.terralegion.game.world.block.BlockManager;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.MoveRightModel;

/** ExecutionAction class created from MMPM action MoveRightAction. */
public class MoveRightAction extends jbt.execution.task.leaf.action.ExecutionAction {
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
	 * Constructor. Constructs an instance of MoveRightAction that is able to run a
	 * com
	 * .jmrapp.terralegion.game.world.entity.behaviors.movement.models.MoveRightAction.
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
	public MoveRightAction(
			MoveRightModel modelTask,
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
		/*
		 * Do not remove this first line unless you know what it does and you
		 * need not do it.
		 */
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		System.out.println("Move Right spawned");
		getContext().setVariable("tries", 0);
	}

	protected Status internalTick() {
		LivingEntity livingEntity = (LivingEntity) getEntity();
		int tX = ChunkManager.pixelToTilePosition(livingEntity.getX());
		int tY = ChunkManager.pixelToTilePosition(livingEntity.getY());
		Chunk chunk = GameScreen.getCurrentWorld().getChunkManager().getChunkFromTilePos(tX, tY);
		tX %= 50; //Get relative to chunk
		tY %= 50; //Get relative to chunk
		BlockType type = chunk.getBlock(tX + 1, tY);
		if (type != null && !BlockManager.getBlock(type).collides()) {
			livingEntity.addVelocity(4, 0);
			return Status.RUNNING;
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