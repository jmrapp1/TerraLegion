// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 04/21/2017 14:52:24
// ******************************************************* 
package com.jmrapp.terralegion.game.world.entity.behaviors.movement.bt;

import com.jmrapp.terralegion.game.world.entity.behaviors.movement.models.*;

/**
 * BT library that includes the trees read from the following files:
 * <ul>
 * <li>movement.xbt</li>
 * </ul>
 */
public class MovementBT implements jbt.execution.core.IBTLibrary {
	/** Tree generated from file movement.xbt. */
	private static jbt.model.core.ModelTask Movement;

	/* Static initialization of all the trees. */
	static {
		Movement = new jbt.model.task.decorator.ModelRepeat(
				null,
				new jbt.model.task.composite.ModelSelector(
						null,
						new jbt.model.task.decorator.ModelInverter(
								null,
								new jbt.model.task.decorator.ModelUntilFail(
										null,
										new jbt.model.task.composite.ModelSelector(
												null,
												new jbt.model.task.composite.ModelSequence(
														null,
														new jbt.model.task.decorator.ModelInverter(
																null,
																new BlockedModel(
																		null,
																		null,
																		"texturedEntity",
																		(int) 1,
																		null)),
														new MoveLeftModel(
																null, null,
																"texturedEntity")),
												new jbt.model.task.composite.ModelSequence(
														null,
														new CanJumpModel(
																null,
																null,
																"texturedEntity",
																(int) 1, null),
														new JumpLeftModel(
																null, null,
																"texturedEntity")),
												new jbt.model.task.composite.ModelSequence(
														null,
														new TryBackupJumpModel(
																null,
																null,
																"texturedEntity",
																(int) 1, null),
														new MoveSlightlyModel(
																null,
																null,
																"texturedEntity",
																(int) 2, null,
																(float) 0.5,
																null),
														new JumpLeftModel(
																null, null,
																"texturedEntity"))))),
						new jbt.model.task.decorator.ModelInverter(
								null,
								new jbt.model.task.decorator.ModelUntilFail(
										null,
										new jbt.model.task.composite.ModelSelector(
												null,
												new jbt.model.task.composite.ModelSequence(
														null,
														new jbt.model.task.decorator.ModelInverter(
																null,
																new BlockedModel(
																		null,
																		null,
																		"texturedEntity",
																		(int) 2,
																		null)),
														new MoveRightModel(
																null, null,
																"texturedEntity")),
												new jbt.model.task.composite.ModelSequence(
														null,
														new CanJumpModel(
																null,
																null,
																"texturedEntity",
																(int) 2, null),
														new JumpRightModel(
																null, null,
																"texturedEntity")),
												new jbt.model.task.composite.ModelSequence(
														null,
														new TryBackupJumpModel(
																null,
																null,
																"texturedEntity",
																(int) 2, null),
														new MoveSlightlyModel(
																null,
																null,
																"texturedEntity",
																(int) 1, null,
																(float) 0.5,
																null),
														new JumpRightModel(
																null, null,
																"texturedEntity")))))));

	}

	/**
	 * Returns a behaviour tree by its name, or null in case it cannot be found.
	 * It must be noted that the trees that are retrieved belong to the class,
	 * not to the instance (that is, the trees are static members of the class),
	 * so they are shared among all the instances of this class.
	 */
	public jbt.model.core.ModelTask getBT(String name) {
		if (name.equals("Movement")) {
			return Movement;
		}
		return null;
	}

	/**
	 * Returns an Iterator that is able to iterate through all the elements in
	 * the library. It must be noted that the iterator does not support the
	 * "remove()" operation. It must be noted that the trees that are retrieved
	 * belong to the class, not to the instance (that is, the trees are static
	 * members of the class), so they are shared among all the instances of this
	 * class.
	 */
	public java.util.Iterator<jbt.util.Pair<String, jbt.model.core.ModelTask>> iterator() {
		return new BTLibraryIterator();
	}

	private class BTLibraryIterator
			implements
			java.util.Iterator<jbt.util.Pair<String, jbt.model.core.ModelTask>> {
		static final long numTrees = 1;
		long currentTree = 0;

		public boolean hasNext() {
			return this.currentTree < numTrees;
		}

		public jbt.util.Pair<String, jbt.model.core.ModelTask> next() {
			this.currentTree++;

			if ((this.currentTree - 1) == 0) {
				return new jbt.util.Pair<String, jbt.model.core.ModelTask>(
						"Movement", Movement);
			}

			throw new java.util.NoSuchElementException();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
