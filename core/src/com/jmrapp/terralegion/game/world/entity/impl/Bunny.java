package com.jmrapp.terralegion.game.world.entity.impl;

import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.TexturedDrawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.game.world.entity.FriendlyEntity;
import com.jmrapp.terralegion.game.world.entity.behaviors.movement.bt.MovementBT;
import jbt.execution.core.*;
import jbt.model.core.ModelTask;

/**
 * Created by Jon on 12/19/15.
 */
public class Bunny extends FriendlyEntity {

	private final IBTLibrary btLibrary = new MovementBT();
	private final IContext context = ContextFactory.createContext(btLibrary);

	private final ModelTask movementTree;
	private final IBTExecutor btExecutor;

	private boolean first = true;

	public Bunny(float x, float y) {
		super(new TexturedDrawable(ResourceManager.getInstance().getTexture("bunny")), x, y, BodyType.DYNAMIC, 7f, 10, 7, 5f);
		context.setVariable("texturedEntity", this);
		movementTree = btLibrary.getBT("Movement");
		btExecutor = BTExecutorFactory.createBTExecutor(movementTree, context);
	}

	@Override
	public void update() {
		super.update();
		if (first) {
			btExecutor.tick();
			first = false;
		}
		if (btExecutor.getStatus() == ExecutionTask.Status.RUNNING) {
			btExecutor.tick();
		}
	}

	@Override
	public String toString() {
		return String.valueOf(hashCode());
	}
}

