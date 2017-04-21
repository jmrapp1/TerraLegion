package com.jmrapp.terralegion.game.world.entity.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.jmrapp.terralegion.engine.views.drawables.AnimationDrawable;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.SpriteSheet;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.game.item.ItemManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.item.inventory.Inventory;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.entity.Drop;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;

public class Player extends LivingEntity {

	private Inventory inventory;

	private final float walkingVelocity = 12f;

	public Player(float x, float y) {
		super(createAnimationDrawable(), x, y, BodyType.DYNAMIC, 12f, 100, 100, 6.5f);
		inventory = new Inventory(5, 8);

		this.inventory.addItemStack(ItemManager.getInstance().getItem(BlockType.WOOD_CHEST), 1);
		this.inventory.addItemStack(ItemManager.getInstance().getItem(BlockType.STOVE), 1);
		this.inventory.addItemStack(ItemManager.getInstance().getItem(ItemType.FISH), 25);
		this.inventory.addItemStack(ItemManager.getInstance().getItem(BlockType.FENCE_WOOD), 5);
		this.inventory.addItemStack(ItemManager.getInstance().getItem(BlockType.FENCE_STONE), 5);
	}

    private static AnimationDrawable createAnimationDrawable() {
        return new AnimationDrawable(new SpriteSheet(ResourceManager.getInstance().getTexture("playerAnimated"), 32, 36, 4, 4));
    }

	public void pickUpDrop(Drop drop, Chunk chunk) {
		if (inventory.addItemStack(drop.getItem(), drop.getStackCount())) {
			chunk.removeEntity(drop);
			drop.destroy();
		}
	}

	@Override
	public void update() {
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			if (canJump())
				jump();
		}
		if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
			addVelocity(-walkingVelocity, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
			addVelocity(walkingVelocity, 0);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
