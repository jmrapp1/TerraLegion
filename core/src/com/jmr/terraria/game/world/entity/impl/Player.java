package com.jmr.terraria.game.world.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.jmr.terraria.engine.views.drawables.ResourceManager;
import com.jmr.terraria.engine.views.drawables.TexturedDrawable;
import com.jmr.terraria.engine.world.entity.BodyType;
import com.jmr.terraria.game.world.chunk.Chunk;
import com.jmr.terraria.game.world.entity.Drop;
import com.jmr.terraria.game.item.inventory.Inventory;

public class Player extends com.jmr.terraria.game.world.entity.LivingEntity {

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
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			addVelocity(12f, 0);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}
	
}
