package com.jmrapp.terralegion.game.world.chunk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;
import com.jmrapp.terralegion.game.world.DayManager;
import com.jmrapp.terralegion.game.world.block.Block;
import com.jmrapp.terralegion.game.world.block.BlockManager;
import com.jmrapp.terralegion.game.world.block.BlockProperties;
import com.jmrapp.terralegion.game.world.block.BlockPropertiesFactory;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.entity.Drop;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.TexturedEntity;
import com.jmrapp.terralegion.game.utils.LightUtils;
import com.jmrapp.terralegion.game.utils.Vector2Factory;
import javafx.scene.effect.Light;

public class Chunk {

	public static int CHUNK_SIZE = 50;
	
	/** Holds all blocks specified by their block type. */
	private BlockType[][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE];
	
	/** Holds all wall blocks specified by their block type. */
	private BlockType[][] walls = new BlockType[CHUNK_SIZE][CHUNK_SIZE];
	
	/** The lightmap for the chunk. Separated from the block and walls for efficiency. */
	private float[][] lightMap = new float[CHUNK_SIZE][CHUNK_SIZE];
	
	/** Holds block properties for each block in the chunk.
	 * To reduce resources the properties will only be created when a blocks properties are created or modified.
	 */
	private BlockProperties[][] blockProperties = new BlockProperties[CHUNK_SIZE][CHUNK_SIZE];
	
	/** Holds block properties for each wall in the chunk.
	 * To reduce resources the properties will only be created when a walls properties are created or modified.
	 */
	private BlockProperties[][] wallProperties = new BlockProperties[CHUNK_SIZE][CHUNK_SIZE];
	
	/** The highest block in each column to calculate the sun lighting. */
	private int[] highestBlocks = new int[CHUNK_SIZE];

	private Array<TexturedEntity> entities = new Array<TexturedEntity>();

	/** The chunk manager instance. */
	private ChunkManager chunkManager;
	
	/** The column the chunk is located at. */
	private int startX;
	
	/** The row the chunk is located at. */
	private int startY;
	
	/** Whether or not the chunk has been generated through either loading or perlin noise. */
	private boolean isGenerated;
	
	/** Whether individual block properties were loaded in the chunk. */
	private boolean blockPropertiesLoaded;
	
	/** Whether this chunk is the highest chunk on the map. */
	private boolean topChunk;
	
	/** Whether the highest tiles have been calculated for the chunk. */
	private boolean highestTilesFound;

	/** Used to tell whether drop collisions should be tested or not. Saves resources. */
	private boolean blockBrokenFlag = false;

	/**
	 * Manages blocks within the world.
	 * 
	 * @param chunkManager The chunk manager instance
	 * @param startX The column the chunk is located at
	 * @param startY The row the chunk is located at
	 * @param topChunk Whether it is the top chunk on the map
	 */
	public Chunk(ChunkManager chunkManager, int startX, int startY, boolean topChunk) {
		this.startX = startX;
		this.startY = startY;
		this.chunkManager = chunkManager;
		this.topChunk = topChunk;
	}

	public void update(OrthoCamera camera, float centerX, float centerY) {
		for (TexturedEntity entity : entities) {
			if (entity instanceof Drop) {
				Drop drop = (Drop) entity;
				if (ChunkManager.isOnScreen(entity.getX(), entity.getY(), camera, centerX, centerY)) {
					if (blockBrokenFlag || (!blockBrokenFlag && !drop.onGround())) {
						drop.resetOnGround();
						chunkManager.getWorld().getPhysicsWorld().updateWorldBody(entity);
					}
					if (chunkManager.getWorld().getPlayer().getBounds().overlaps(drop.getBounds())) {
						if (drop.canPickup()) {
							chunkManager.getWorld().getPlayer().pickUpDrop(drop, this);
						}
					}
				}
				drop.checkDeathTime(this);
			} else if (entity instanceof LivingEntity) {
				chunkManager.getWorld().getPhysicsWorld().updateWorldBody(entity);
				entity.update();
				if (((LivingEntity) entity).isDead()) {
					entities.removeValue(entity, false);
				}
			} else {
				if (ChunkManager.isOnScreen(entity.getX(), entity.getY(), camera, centerX, centerY)) {
					chunkManager.getWorld().getPhysicsWorld().updateWorldBody(entity);
				}
			}
		}
		blockBrokenFlag = false;
	}
	
	public void render(OrthoCamera camera, SpriteBatch sb, float centerX, float centerY, int startYDraw, int endYDraw, int startXDraw, int endXDraw) {
		for (int y = startYDraw; y < endYDraw; y++) {
			for (int x = startXDraw; x < endXDraw; x++) {
				if (ChunkManager.isOnScreen(x * ChunkManager.TILE_SIZE + (getStartX() * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), y * ChunkManager.TILE_SIZE + (startY * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), camera, centerX, centerY)) {
					BlockType type = getBlock(x, y);
					if ((type == null || type == BlockType.AIR) || BlockManager.getBlock(type).isTransparent()) {
						BlockType wallType = getWall(x, y);
						if (wallType != null && wallType != BlockType.AIR) {
							BlockManager.getBlock(wallType).render(camera, sb, x * ChunkManager.TILE_SIZE + (startX * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), y * ChunkManager.TILE_SIZE + (startY * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), getLightValue(x, y));
						}
					}
					if (type != null && type != BlockType.AIR) {
						BlockManager.getBlock(type).render(camera, sb, x * ChunkManager.TILE_SIZE + (startX * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), y * ChunkManager.TILE_SIZE + (startY * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE), getLightValue(x, y));
					}
				}
			}
		}
		for (TexturedEntity entity : entities) {
			renderEntity(entity, sb, camera, centerX, centerY);
		}
	}

	public void renderEntity(TexturedEntity entity, SpriteBatch sb, OrthoCamera camera, float centerX, float centerY) {
		if (ChunkManager.isOnScreen(entity.getX(), entity.getY(), camera, centerX, centerY)) {
			int tx = ChunkManager.pixelToTilePosition(entity.getX()) - (startX * CHUNK_SIZE);
			int ty = ChunkManager.pixelToTilePosition(entity.getY()) - (startY * CHUNK_SIZE);
			if (tx >= 0 && tx < CHUNK_SIZE && ty >= 0 && ty < CHUNK_SIZE) {
				entity.render(sb, getLightValue(tx, ty));
			} else { //This entity is in the wrong chunk because it moved out of it
				Chunk chunk = relocateEntity(entity);
				if (chunk != null) {
					chunk.renderEntity(entity, sb, camera, centerX, centerY);
				}
			}
		}
	}

	private Chunk relocateEntity(TexturedEntity entity) {
		Chunk chunk = chunkManager.getChunkFromPos(entity.getX(), entity.getY());
		if (chunk != null) {
			chunk.addEntity(removeEntity(entity));
			return chunk;
		}
		return null;
	}

	public Array<LivingEntity> findLivingEntitiesInRange(float x, float y, float range) {
		Vector2 origin = Vector2Factory.instance.getVector2(x, y);
		Array<LivingEntity> foundEntities = new Array<LivingEntity>();

		for (int i = 0; i < entities.size; i++) {
			TexturedEntity entity = entities.get(i);
			if (entity instanceof LivingEntity) {
				if (origin.dst(entity.getX(), entity.getY()) <= range) {
					foundEntities.add((LivingEntity) entity);
				}
			}
		}

		int i;
		for (int j = 1; j < foundEntities.size; j++) {
			LivingEntity entity1 = foundEntities.get(j);
			float dist = origin.dst(entity1.getX(), entity1.getY());

			for(i = j - 1; (i >= 0) && (origin.dst(foundEntities.get(i).getX(), foundEntities.get(i).getY()) > dist); i--) {
				foundEntities.set(i + 1, foundEntities.get(i));
			}
			foundEntities.set(i + 1, entity1);    // Put the key in its proper location
		}
		return foundEntities;
	}

	public TexturedEntity getEntity(String id) {
		for (TexturedEntity entity : entities) {
			if (entity.toString().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public boolean isGenerated() {
		return isGenerated;
	}
	
	public boolean isTopChunk() {
		return topChunk;
	}
	
	public void setGenerated(boolean val) {
		this.isGenerated = val;
	}
	
	public boolean blockPropertiesLoaded() {
		return blockPropertiesLoaded;
	}

	public boolean isHighestTilesFound() {
		return highestTilesFound;
	}

	public BlockProperties getBlockProperties(int x, int y) {
		BlockProperties properties = blockProperties[x][y];
		if (properties == null) {
			Block block = BlockManager.getBlock(blocks[x][y]);
			properties = BlockPropertiesFactory.instance.getBlockProperties(block.getInitialHealth(), (byte)0);
			blockProperties[x][y] = properties;
		}
		return properties;
	}
	
	public void setBlockProperties(BlockProperties properties, int x, int y) {
		blockProperties[x][y] = properties;
	}
	
	public BlockProperties getWallProperties(int x, int y) {
		BlockProperties properties = wallProperties[x][y];
		if (properties == null) {
			if (walls[x][y] != BlockType.AIR) {
				Block block = BlockManager.getBlock(walls[x][y]);
				properties = BlockPropertiesFactory.instance.getBlockProperties(block.getInitialHealth(), (byte)0);
				wallProperties[x][y] = properties;
			}
		}
		return properties;
	}
	
	public void setWallProperties(BlockProperties properties, int x, int y) {
		wallProperties[x][y] = properties;
	}
	
	public void loadBlockProperties() {
		blockPropertiesLoaded = true;
		for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
			for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
				BlockType type = blocks[x][y];
				if (type == null) {
					type = BlockType.AIR;
					blocks[x][y] = type;
				}
				Block block = BlockManager.getBlock(type);
				if (block.getClass() != Block.class) { //If there are no special properties
					block.onPlace(chunkManager, this, x, y);
				}
			}
		}
	}
	
	/** Goes through each column and finds the highest tile. Used for generating the sun on the ground.
	 *  Only used on the highest chunks though because it wont matter for the others.
	 *  @return whether a new tile was found
	 */
	public void findHighestTiles() {
		if (!highestTilesFound) { //if we didn't go through each column yet
			for (int x = 0; x < CHUNK_SIZE; x++) { //Go through each column
				findHighestTile(x);
			}
			highestTilesFound = true;
		}
	}
	
	/** Finds the highest tile in a specific column.
	 * 
	 * @param x The column to check
	 * @return if a new highest tile was found
	 */
	public boolean findHighestTile(int x) {
		for (int y = CHUNK_SIZE - 1; y >= highestBlocks[x]; y--) { //Go down the column up till the current highest tile
			BlockType type = blocks[x][y];
			if (type != null && type != BlockType.AIR) {
				//<TODO compare the height of the tile next to it (may not be needed anymore) />
				if (BlockManager.getBlock(type).collides()) {
					highestBlocks[x] = MathUtils.clamp(y + 1, 0, Chunk.CHUNK_SIZE - 1);
				} else {
					setLightValue(1f, x, y); //Set higher transparent blocks to have 1f light
					continue;
				}
				return true;
			}
		}
		return false;
	}
	
	/** Calculate the sun on all columns. */
	public void calculateSun() {
		for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
			calculateSun(x);
		}
	}
	
	/** Calculate the lighting from the sun on a specific column.
	 * 
	 * @param x The column to calculate on
	 */
	private void calculateSun(int x) {
		int highestY = highestBlocks[x];
		float lightValue = lightMap[x][highestY];
		if (lightValue <= .7f) {
			LightUtils.calculateChunkLight(chunkManager, x + (startX * CHUNK_SIZE), highestY + (startY * CHUNK_SIZE), 1f + BlockManager.getBlock(blocks[x][highestY]).getLightBlockingAmount(), false);
		}
	}
	
	public int getHighestTile(int x) {
		return highestBlocks[x];
	}

	public void addEntity(TexturedEntity entity) {
		entities.add(entity);
	}

	public TexturedEntity removeEntity(TexturedEntity entity) {
		if (entities.removeValue(entity, false))
			return entity;
		return null;
	}
	
	public BlockType getBlock(int x, int y) {
		if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
			BlockType type = blocks[x][y];
			if (type == null) {
				blocks[x][y] = BlockType.AIR;
				return BlockType.AIR;
			}
			return type;
		}
		return null;
	}
	
	public BlockType getWall(int x, int y) {
		if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
			BlockType type = walls[x][y];
			if (type == null) {
				walls[x][y] = BlockType.AIR;
				return BlockType.AIR;
			}
			return type;
		}
		return null;
	}
	
	public void setBlock(BlockType type, int x, int y, boolean callEvents) {
		if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
			BlockType oldType = blocks[x][y];
			blocks[x][y] = type;
			
			if (blockProperties[x][y] != null) {
				BlockPropertiesFactory.instance.destroy(blockProperties[x][y]);
			}
			blockProperties[x][y] = null;

			blockBrokenFlag = true;

			if (callEvents) {
				
				if (topChunk) { //If we're the top chunk we want to use this to calculate the sun
					if (findHighestTile(x)) //Find the new highest tile
						calculateSun(x);
				}
				
				if (oldType != null) {
					BlockManager.getBlock(oldType).onBreak(chunkManager, this, x, y);
				}
				BlockManager.getBlock(type).onPlace(chunkManager, this, x, y);
			}
		}
	}

	public void resetBlockBrokenFlag() {
		blockBrokenFlag = true;
	}

	public void setWall(BlockType type, int x, int y, boolean callEvents) {
		if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
			if (BlockManager.getBlock(type).isWall()) {
				BlockType oldType = walls[x][y];
				walls[x][y] = type;
				
				if (wallProperties[x][y] != null) {
					BlockPropertiesFactory.instance.destroy(wallProperties[x][y]);
				}
				wallProperties[x][y] = null;
				
				if (callEvents) {
					if (oldType != null) {
						BlockManager.getBlock(oldType).onBreak(chunkManager, this, x, y);
					}
					BlockManager.getBlock(type).onPlace(chunkManager, this, x, y);
				}
			}
		}
	}
	
	public float[][] getLightMap() {
		return lightMap;
	}
	
	public float getLightValue(int x, int y) {
		if (isTopChunk() && highestBlocks[x] < y) //if we're above the highest block
			return DayManager.getInstance().getWorldLightValue(); //make it the highest light value
		float value = lightMap[x][y];

		boolean isLit = false;
		if (blockProperties[x][y] != null)
			if (blockProperties[x][y].hasFlag(BlockProperties.LIT_BY_LIGHT_SOURCE))
				isLit = true;

		if (!isLit) //if it's not lit (hah), then see if the day value is lower than the current value
			if (DayManager.getInstance().getWorldLightValue() < value)
				value = DayManager.getInstance().getWorldLightValue();

		return value;
	}
	
	public void setLightValue(float value, int x, int y) {
		lightMap[x][y] = value;
	}
	
	public BlockType[][] getBlocks() {
		return blocks;
	}

	/**
	 * Damages a single entity at the given position
     */
	public void damageEntity(float x, float y, float damage) {
		for(TexturedEntity entity : entities) {
			if(entity instanceof LivingEntity && entity.getBounds().overlaps(new Rectangle(x, y, 0, 0))) {
				((LivingEntity ) entity).damage(damage);
				break;
			}
		}
	}

}