package com.jmrapp.terralegion.game.world.block;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.world.entity.WorldBody;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.world.entity.Drop;
import com.jmrapp.terralegion.game.world.entity.DropManager;
import com.jmrapp.terralegion.game.utils.LightUtils;

public class Block {

	protected final BlockType type;
	protected Drawable drawable;
	protected float lightBlockingAmount;
	protected boolean collides;
	protected boolean transparent;
	protected float initHealth;
	protected float resistance;

	public Block(BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, boolean transparent, float initHealth, float resistance) {
		this.type = type;
		this.drawable = drawable;
		this.lightBlockingAmount = lightBlockingAmount;
		this.collides = collides;
		this.transparent = transparent;
		this.initHealth = initHealth;
		this.resistance = resistance;
	}

	public void render(OrthographicCamera camera, SpriteBatch sb, float x, float y, float lightValue) {
		float value = lightValue < LightUtils.MIN_LIGHT_VALUE ? LightUtils.MIN_LIGHT_VALUE : lightValue;
		sb.setColor(value, value, value, 1);
		drawable.render(sb, x, y);
		sb.setColor(Color.WHITE);
	}

	public void onBreak(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
		if (type != BlockType.AIR) {
			int realX = chunkTileX + (chunk.getStartX() * Chunk.CHUNK_SIZE); //Get real world x
			int realY = chunkTileY + (chunk.getStartY() * Chunk.CHUNK_SIZE); //Get real world y
			float lightVal = chunk.getLightValue(chunkTileX, chunkTileY) + lightBlockingAmount; //Get the tile light value
			chunk.setLightValue(LightUtils.MIN_LIGHT_VALUE, chunkTileX, chunkTileY); //set to the smallest so that it gets overwritten
			boolean lightSource = chunk.getBlockProperties(chunkTileX, chunkTileY).hasFlag(BlockProperties.LIT_BY_LIGHT_SOURCE);
			LightUtils.calculateChunkLight(chunkManager, realX, realY, lightVal, lightSource); //update surrounding light values
			float x = (((chunk.getStartX() * Chunk.CHUNK_SIZE) + chunkTileX) * ChunkManager.TILE_SIZE) + MathUtils.random(ChunkManager.TILE_SIZE / 4, ChunkManager.TILE_SIZE / 2);
			float y = (((chunk.getStartY() * Chunk.CHUNK_SIZE) + chunkTileY) * ChunkManager.TILE_SIZE) + (ChunkManager.TILE_SIZE / 2);
			Drop drop = DropManager.getInstance().getDrop(type, 1, x, y); //Send to factory to destroy it
			drop.setVelocity(0, 2);
			chunk.addEntity(drop);
		}
	}

	public boolean onDamage(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY, BlockProperties properties, float toolPower) {
		properties.addHealth(-toolPower);
		if (properties.getHealth() <= 0) {
			chunk.setBlock(BlockType.AIR, chunkTileX, chunkTileY, true);
		}
		return true;
	}

	public float getLightBlockingAmount() {
		return lightBlockingAmount;
	}

	public boolean collides() {
		return collides;
	}

	public boolean isWall() {
		return false;
	}

	public boolean isTransparent() {
		return transparent;
	}

	public float getInitialHealth() {
		return initHealth;
	}

	public float getResistance() {
		return resistance;
	}

	public void onPlace(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
		if (type != BlockType.AIR) {
			int realX = chunkTileX + (chunk.getStartX() * Chunk.CHUNK_SIZE); //Get real world x
			int realY = chunkTileY + (chunk.getStartY() * Chunk.CHUNK_SIZE); //Get real world y
			float lightVal = chunk.getLightValue(chunkTileX, chunkTileY); //Get the tile light value
			//chunk.setLightValue(lightVal, chunkTileX, chunkTileY); //set to the smallest so that it gets overwritten

			boolean lightSource = chunk.getBlockProperties(chunkTileX, chunkTileY).hasFlag(BlockProperties.LIT_BY_LIGHT_SOURCE);

			LightUtils.extinguishLightAmount(chunkManager, realX, realY, lightVal, lightBlockingAmount, lightSource); //update surrounding light values
		}
	}

	public WorldBody getBody(float x, float y) {
		return new BlockBody(x, y);
	}

	public BlockType getType() {
		return type;
	}

}
