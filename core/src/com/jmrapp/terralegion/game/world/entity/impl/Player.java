package com.jmrapp.terralegion.game.world.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.TexturedDrawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.game.item.inventory.Inventory;
import com.jmrapp.terralegion.game.utils.Direction;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.entity.Drop;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;

public class Player extends LivingEntity {

	private Inventory inventory;

	public Player(float x, float y) {
		super(new TexturedDrawable(ResourceManager.getInstance().getTexture("player")), x, y, BodyType.DYNAMIC, 12f, 100, 100, 6.5f);
		inventory = new Inventory(5, 8);
	}

	public void pickUpDrop(Drop drop, Chunk chunk) {
		if (inventory.addItemStack(drop.getItem(), drop.getStackCount())) {
			chunk.removeEntity(drop);
			drop.destroy();
		}
	}

	@Override
	public void update() {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (canJump())
				jump();
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			addVelocity(-12f, 0);
			faceDirection(Direction.LEFT);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			addVelocity(12f, 0);
			faceDirection(Direction.RIGHT);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
