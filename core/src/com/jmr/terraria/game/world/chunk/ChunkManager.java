package com.jmr.terraria.game.world.chunk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jmr.terraria.engine.camera.OrthoCamera;
import com.jmr.terraria.engine.utils.Settings;
import com.jmr.terraria.engine.world.PhysicsWorld;
import com.jmr.terraria.engine.world.SimplexNoise;
import com.jmr.terraria.game.world.block.BlockManager;
import com.jmr.terraria.game.world.block.BlockProperties;
import com.jmr.terraria.game.world.block.BlockType;
import com.jmr.terraria.game.world.chunk.gen.HillGenerator;
import com.jmr.terraria.game.world.chunk.gen.OreGenerator;
import com.jmr.terraria.game.item.impl.ToolItem;
import com.jmr.terraria.game.utils.ThreadManager;
import com.jmr.terraria.game.utils.Vector2Factory;
import com.jmr.terraria.game.world.World;

public class ChunkManager {

	public static int totalChunksLoaded = 0;
	
	/** The standard size of the tiles */
	public static int TILE_SIZE = 32;
	
	/** The world dimensions for the chunks */
	public static int CHUNKS_X = 36, CHUNKS_Y = 18;
	
	/** Used to generate hills within a chunk */
	private static final OreGenerator oreGenerator = new OreGenerator();

	private static final HillGenerator hillGenerator = new HillGenerator(oreGenerator);

	/** The loaded chunks in the world */
	private Chunk[][] chunks = new Chunk[CHUNKS_X][CHUNKS_Y];
	
	/** Holds all chunks located in a radius around the player */
	private Chunk[][] loadedChunks = new Chunk[3][3];
	
	private SimplexNoise noise = new SimplexNoise();

	private World world;
	
	/**
	 * Used when loading a new world
	 * @param world The world instance
	 * @param seed The world seed
	 */
	public ChunkManager(World world, long seed) {
		this.world = world;
		loadSeed(seed);
		generateTerrain();
	}
	
	/** Used when loading an existing world. */
	public ChunkManager(World world) {
		this.world = world;
	}
	
	public void loadSeed(long seed) {
		noise.genGrad(seed);
	}
	
	public void generateTerrain() {
		for (int y = 0; y < CHUNKS_Y; y++) {
			for (int x = 0; x < CHUNKS_X; x++) {
				if (chunks[x][y] == null) { //Dont overwrite loaded chunks if any
					chunks[x][y] = new Chunk(this, x, y, y == CHUNKS_Y - 1);
				}
			}
		}
	}
	
	private void generateChunk(final Chunk chunk) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if(chunk.getStartY()==CHUNKS_Y-1) { //If it is the top chunk where the hills should be generated
					hillGenerator.generate(noise, chunk);
				} else {
					oreGenerator.generate(noise, chunk);
				}
				//WorldIO.saveChunk(world.getWorldName(),chunk);
				chunk.setGenerated(true);
				totalChunksLoaded += 1;

				chunk.loadBlockProperties();

				if (chunk.isTopChunk() && !chunk.isHighestTilesFound()) { //Used to calculate the sun's lighting
					chunk.findHighestTiles();
					chunk.calculateSun();
				}
			}
		};
		ThreadManager.getInstance().runThread(runnable);
	}
	
	public void updateChunks(PhysicsWorld world, float centerX, float centerY) {
		if (loadedChunks[1][1] == null || getChunkFromPos(centerX, centerY) != loadedChunks[1][1]) {
			int tx = pixelToTilePosition(centerX);
			int ty = pixelToTilePosition(centerY);
			
			int chunkX = tx / Chunk.CHUNK_SIZE;
			int chunkY = ty / Chunk.CHUNK_SIZE;
			
			loadedChunks[1][1] = getAndLoadChunk(chunkX, chunkY);
			loadedChunks[0][0] = getAndLoadChunk(chunkX - 1, chunkY - 1); //top left			
			loadedChunks[1][0] = getAndLoadChunk(chunkX, chunkY - 1); //top middle
			loadedChunks[2][0] = getAndLoadChunk(chunkX + 1, chunkY - 1); //top right
			loadedChunks[0][1] = getAndLoadChunk(chunkX - 1, chunkY); //middle left
			loadedChunks[2][1] = getAndLoadChunk(chunkX + 1, chunkY); // middle right
			loadedChunks[0][2] = getAndLoadChunk(chunkX - 1, chunkY + 1); //bottom left
			loadedChunks[1][2] = getAndLoadChunk(chunkX, chunkY + 1); //bottom middle
			loadedChunks[2][2] = getAndLoadChunk(chunkX + 1, chunkY + 1); //bottom middle
			
			System.out.println("Changed chunk: " + chunkX + " " + chunkY);
		}
	}
	
	/**
	 * Gets a chunk given a position but if it is outside the bounds it will return null.
	 * @param x The chunk x position
	 * @param y The chunk y position
	 * @return The chunk or null if it's outside of the bounds
	 */
	private Chunk getChunk(int x, int y) {
		if (x < CHUNKS_X && x >= 0 && y >= 0 && y < CHUNKS_Y)
			return chunks[x][y];
		return null;
	}
	
	private Chunk getAndLoadChunk(int x, int y) {
		if (x < CHUNKS_X && x >= 0 && y >= 0 && y < CHUNKS_Y) {
			Chunk chunk = chunks[x][y];

			if (!chunk.isGenerated()) {
				generateChunk(chunk);
				System.out.println("Generated");
			}
			/*if (chunk != null && !chunk.blockPropertiesLoaded()) { //this will generate the lights and such
				chunk.loadBlockProperties();
				System.out.println("Loaded properties");
			}
			brb
			if (chunk.isTopChunk() && !chunk.isHighestTilesFound()) { //Used to calculate the sun's lighting
				chunk.findHighestTiles();
				chunk.calculateSun();
			}*/
			return chunk;
		}
		return null;
	}

	public void renderOnScreen(SpriteBatch sb, OrthoCamera camera, float centerX, float centerY) {

		int tx = pixelToTilePosition(centerX);
		int ty = pixelToTilePosition(centerY);

		int chunkX = tx / Chunk.CHUNK_SIZE; //Get start chunk X
		int chunkY = ty / Chunk.CHUNK_SIZE; //Get start chunk Y

		int chunkPosXMin = 0;
		int chunkPosXMax = Chunk.CHUNK_SIZE;
		int chunkPosYMin = 0;
		int chunkPosYMax = Chunk.CHUNK_SIZE;

		int leftOverlap = 0;
		int rightOverlap = 0;
		int topOverlap = 0;
		int bottomOverlap = 0;

		if (loadedChunks[1][1] != null)
			loadedChunks[1][1].render(camera, sb, centerX, centerY, chunkPosYMin, chunkPosYMax, Chunk.CHUNK_SIZE - leftOverlap, Chunk.CHUNK_SIZE);

		if (loadedChunks[1][1] != null) {
			chunkPosXMin = tx - (chunkX * Chunk.CHUNK_SIZE) - ((Settings.getWidth() / 2) / TILE_SIZE) - 1;
			chunkPosXMax = chunkPosXMin + (Settings.getWidth() / TILE_SIZE) + 3;

			chunkPosYMin = ty - (chunkY * Chunk.CHUNK_SIZE) - ((Settings.getHeight() / 2) / TILE_SIZE) - 1;
			chunkPosYMax = chunkPosYMin + (Settings.getHeight() / TILE_SIZE) + 2;

			if (chunkPosXMin < 0) {
				leftOverlap = chunkPosXMin * -1; //get overlap
				chunkPosXMin = 0;
			} else if (chunkPosXMax > Chunk.CHUNK_SIZE) {
				rightOverlap = chunkPosXMax - Chunk.CHUNK_SIZE;
				chunkPosXMax = Chunk.CHUNK_SIZE;
			}

			if (chunkPosYMin < 0) {
				bottomOverlap = chunkPosYMin * -1;
				chunkPosYMin = 0;
			} else if (chunkPosYMax > Chunk.CHUNK_SIZE) {
				topOverlap = chunkPosYMax - Chunk.CHUNK_SIZE;
				chunkPosYMax = Chunk.CHUNK_SIZE;
			}

			loadedChunks[1][1].update(camera, centerX, centerY);
			loadedChunks[1][1].render(camera, sb, centerX, centerY, chunkPosYMin, chunkPosYMax, chunkPosXMin, chunkPosXMax);
		}
		if (leftOverlap > 0) {
			if (loadedChunks[0][1] != null) {//Left
				loadedChunks[0][1].update(camera, centerX, centerY);
				loadedChunks[0][1].render(camera, sb, centerX, centerY, chunkPosYMin, chunkPosYMax, Chunk.CHUNK_SIZE - leftOverlap, Chunk.CHUNK_SIZE);
			}
			if (bottomOverlap > 0) { //bottom left
				if (loadedChunks[0][0] != null) {
					loadedChunks[0][0].update(camera, centerX, centerY);
					loadedChunks[0][0].render(camera, sb, centerX, centerY, Chunk.CHUNK_SIZE - bottomOverlap, Chunk.CHUNK_SIZE, Chunk.CHUNK_SIZE - leftOverlap, Chunk.CHUNK_SIZE); //Render bottom left at the top right corner only
				}
			}
			if (topOverlap > 0) { //top left
				if (loadedChunks[0][2] != null) {
					loadedChunks[0][2].update(camera, centerX, centerY);
					loadedChunks[0][2].render(camera, sb, centerX, centerY, 0, topOverlap, Chunk.CHUNK_SIZE - leftOverlap, Chunk.CHUNK_SIZE);
				}
			}
		}
		if (rightOverlap > 0) { //Right
			if (loadedChunks[2][1] != null) {
				loadedChunks[2][1].update(camera, centerX, centerY);
				loadedChunks[2][1].render(camera, sb, centerX, centerY, chunkPosYMin, chunkPosYMax, 0, rightOverlap);
			}
			if (bottomOverlap > 0) { //bottom right
				if (loadedChunks[2][0] != null) {
					loadedChunks[2][0].update(camera, centerX, centerY);
					loadedChunks[2][0].render(camera, sb, centerX, centerY, Chunk.CHUNK_SIZE - bottomOverlap, Chunk.CHUNK_SIZE, 0, rightOverlap); //Render bottom right at the top left corner only
				}
			}
			if (topOverlap > 0) { //top right
				if (loadedChunks[2][2] != null) {
					loadedChunks[2][2].update(camera, centerX, centerY);
					loadedChunks[2][2].render(camera, sb, centerX, centerY, 0, topOverlap, 0, rightOverlap); //Render the top right chunk at the bottom left
				}
			}
		}
		if (topOverlap > 0) {
			if (loadedChunks[1][2] != null) {
				loadedChunks[1][2].update(camera, centerX, centerY);
				loadedChunks[1][2].render(camera, sb, centerX, centerY, 0, topOverlap, chunkPosXMin, chunkPosXMax);
			}
		}
		if (bottomOverlap > 0) {
			if (loadedChunks[1][0] != null) {
				loadedChunks[1][0].update(camera, centerX, centerY);
				loadedChunks[1][0].render(camera, sb, centerX, centerY, Chunk.CHUNK_SIZE - bottomOverlap, Chunk.CHUNK_SIZE, chunkPosXMin, chunkPosXMax);
			}
		}
	}
	
	public boolean damageBlock(float x, float y, float toolPower) {
		int tx = pixelToTilePosition(x);
		int ty = pixelToTilePosition(y);
		return damageBlock(tx, ty, toolPower);
	}

	public boolean damageBlock(int tileX, int tileY, float toolPower) {
		if (isWithinWorld(tileX, tileY)) {
			Chunk chunk = getChunkFromTilePos(tileX, tileY);
			int chunkTileX = tileX - (chunk.getStartX() * Chunk.CHUNK_SIZE);
			int chunkTileY = tileY - (chunk.getStartY() * Chunk.CHUNK_SIZE);

			if (chunk.getBlock(chunkTileX, chunkTileY) != BlockType.AIR) {
				BlockProperties properties = chunk.getBlockProperties(chunkTileX, chunkTileY);
				return BlockManager.getBlock(chunk.getBlock(chunkTileX, chunkTileY)).onDamage(this, chunk, chunkTileX, chunkTileY, properties, toolPower);
			}
		}
		return false;
	}
	
	/**
	 * Determines whether a specific point is being viewed on the screen. It also accounts for a few of the blocks outside of the viewport.
	 * @param x The tile X (in pixels)
	 * @param y The tile Y (in pixels)
	 * @param camera The camera
	 * @param centerX The x position
	 * @param centerY The y position
	 * @return whether the location is viewed on the screen
	 */
	public static boolean isOnScreen(float x, float y, OrthoCamera camera, float centerX, float centerY) {
		float startX = centerX - (Settings.getWidth() / 2);
		float startY = centerY - (Settings.getHeight() / 2);
		return x >= startX - (TILE_SIZE * 2) && y >= startY - (TILE_SIZE * 2) && x <= startX + Settings.getWidth() + TILE_SIZE && y <= startY + Settings.getHeight() + TILE_SIZE;
	}
	
	/**
	 * Gets a chunk from a tile position (not a pixel position)
	 * @param x The tile X
	 * @param y The tile Y
	 * @return The chunk that the position is located in
	 */
	public Chunk getChunkFromTilePos(int x, int y) {
		return getChunk(x / Chunk.CHUNK_SIZE, y / Chunk.CHUNK_SIZE);
	}
	
	/**
	 * Gets a chunk from a pixel position
	 * @param x The x position
	 * @param y The y position
	 * @return The chunk that the position is located in
	 */
	public Chunk getChunkFromPos(float x, float y) {
		int tx = (int) x / TILE_SIZE;
		int ty = (int) y / TILE_SIZE;
		return getChunk(tx / Chunk.CHUNK_SIZE, ty / Chunk.CHUNK_SIZE);
	}

	public Vector2 findBlock(ToolItem tool, float x, float y, Vector2 direction, float maxReach) {
		if (direction.x == 0 && direction.y == 0) {
			return null;
		}
		Vector2 position = Vector2Factory.instance.getVector2(x, y);
		Vector2 origin = Vector2Factory.instance.getVector2(x, y);
		Vector2 v = findBlock(tool, position, direction, origin, maxReach, 0, 0);
		Vector2Factory.instance.destroy(position);
		Vector2Factory.instance.destroy(origin);
		return v;
	}

	/**
	 * Recursive method that searches in a specified direction for a block.
	 *
	 * @param position The last position checked
	 * @param direction The direction to search
	 * @param origin The original position to check against the maxReach
	 * @param maxReach The max pixels that the search can go
	 * @return The tile position of a block if found; null if nothing found within reach
	 */
	private Vector2 findBlock(ToolItem tool, Vector2 position, Vector2 direction, Vector2 origin, float maxReach, float xCount, float yCount) {
		xCount += direction.x; //Add direction
		yCount += direction.y; //Add direction
		boolean newVal = false; //Flag to see if we switch blocks

		if (xCount >= 1 || xCount <= -1) { //If we went left or right of the last tile
			xCount = 0; //reset
			position.add((direction.x < 0 ? -1 : 1) * ChunkManager.TILE_SIZE, 0); //Go over a tile
			newVal = true; //set flag
		}

		if (yCount >= 1 || yCount <= -1) { //If we went up or down from the last tile
			yCount = 0; //reset
			position.add(0, (direction.y < 0 ? -1 : 1) * ChunkManager.TILE_SIZE); //Go vertical
			newVal = true; //set flag
		}

		if (origin.dst(position) > maxReach) { //If we're too far from the origin then return nothing
			return null;
		}

		if (!newVal) { //If we didn't change blocks we're checking, go to the next revolution
			return findBlock(tool, position, direction, origin, maxReach, xCount, yCount);
		}

		int tx = pixelToTilePosition(position.x);
		int ty = pixelToTilePosition(position.y);

		BlockType type = getBlockFromTilePos(tx, ty);
		if ((tool != null && type != BlockType.AIR)) { // we found a block
			if (tool.canDamageBlock(type)) {
				return Vector2Factory.instance.getVector2(tx, ty); //Return the tile position
			} else {
				return null;
			}
		} else if (tool == null && type != BlockType.AIR) { //Doing a search without a tool bounding the selection
			return Vector2Factory.instance.getVector2(tx, ty); //Return the tile position
		} else {
			return findBlock(tool, position, direction, origin, maxReach, xCount, yCount); //Keep searching
		}
	}

	public Vector2 findFarthestAirBlock(float x, float y, Vector2 direction, float maxReach) {
		if (direction.x == 0 && direction.y == 0) {
			return null;
		}
		Vector2 position = Vector2Factory.instance.getVector2(x, y);
		Vector2 origin = Vector2Factory.instance.getVector2(x, y);
		Vector2 v = findFarthestAirBlock(position, direction, origin, maxReach, 0, 0, null);
		Vector2Factory.instance.destroy(position);
		Vector2Factory.instance.destroy(origin);
		return v;
	}

	/**
	 * Recursive method that searches in a specified direction for a block.
	 *
	 * @param position The last position checked
	 * @param direction The direction to search
	 * @param origin The original position to check against the maxReach
	 * @param maxReach The max pixels that the search can go
	 * @return The tile position of a block if found; null if nothing found within reach
	 */
	private Vector2 findFarthestAirBlock(Vector2 position, Vector2 direction, Vector2 origin, float maxReach, float xCount, float yCount, Vector2 farthestAirFound) {
		xCount += direction.x; //Add direction
		yCount += direction.y; //Add direction
		boolean newVal = false; //Flag to see if we switch blocks
		if (xCount >= 1 || xCount <= -1) { //If we went left or right of the last tile
			xCount = 0; //reset
			position.add((direction.x < 0 ? -1 : 1) * ChunkManager.TILE_SIZE, 0); //Go over a tile
			newVal = true; //set flag
		}
		if (yCount >= 1 || yCount <= -1) { //If we went up or down from the last tile
			yCount = 0; //reset
			position.add(0, (direction.y < 0 ? -1 : 1) * ChunkManager.TILE_SIZE); //Go vertical
			newVal = true; //set flag
		}

		if (origin.dst(position) > maxReach) { //If we're too far from the origin then return air location if we found it
			return farthestAirFound;
		}

		if (!newVal) { //If we didn't change blocks we're checking, go to the next revolution
			return findFarthestAirBlock(position, direction, origin, maxReach, xCount, yCount, farthestAirFound);
		}

		int tx = pixelToTilePosition(position.x);
		int ty = pixelToTilePosition(position.y);

		BlockType type = getBlockFromTilePos(tx, ty);
		if (type == BlockType.AIR) { // we found air!
			if (farthestAirFound == null)
				farthestAirFound = Vector2Factory.instance.getVector2(tx, ty); //Set the air block and keep searching for farther air blocks
			else
				farthestAirFound.set(tx, ty);
			return findFarthestAirBlock(position, direction, origin, maxReach, xCount, yCount, farthestAirFound); //Keep searching
		} else {
			return farthestAirFound;
		}
	}
	 
	public double getLightValueFromPos(float x, float y) {
		int tx = pixelToTilePosition(x);
		int ty = pixelToTilePosition(y);
		Chunk chunk = getChunkFromPos(x, y);
		if (chunk != null) {
			int chunkX = tx - (chunk.getStartX() * Chunk.CHUNK_SIZE);
			int chunkY = ty - (chunk.getStartY() * Chunk.CHUNK_SIZE);
			
			if (chunk.isTopChunk()) {
				int topY = chunk.getHighestTile(chunkX);
				if (topY < chunkY) {
					return 1; //Sun light value
				}
			}
			return chunk.getLightValue(chunkX, chunkY);
		}
		return 1; //Sun light value
	}
	
	/**
	 * Gets the tile position given a vector position
	 * @param x The x position in pixels
	 * @param y The y position in pixels
	 * @return The tile position
	 */

	public static boolean isWithinWorld(int x, int y) {
		return x >= 0 && y >= 0 && x < (CHUNKS_X * Chunk.CHUNK_SIZE) && y < (CHUNKS_Y * Chunk.CHUNK_SIZE);
	}

	public static int pixelToTilePosition(float p) {
		return (int) p / TILE_SIZE;
	}

	/*public static Vector2 getTilePosition(float x, float y) {
		return new Vector2(x / TILE_SIZE, y / TILE_SIZE);//so just here?, most likely yeah.
	}*/

	public static float tileToPixelPosition(int t) {
		return t * TILE_SIZE;
	}

	/*public static Vector2 tileToPixelPosition(int x, int y) {
		return new Vector2(x * TILE_SIZE, y * TILE_SIZE);
	}*/
	
	public void setChunk(Chunk chunk, int x, int y) {
		chunks[x][y] = chunk;
	}
	
	public BlockType getBlockFromTilePos(int x, int y) {
		Chunk chunk = getChunkFromTilePos(x, y);
		if (chunk != null) {
			int xOffset = chunk.getStartX() * Chunk.CHUNK_SIZE;
			int yOffset = chunk.getStartY() * Chunk.CHUNK_SIZE;
			return chunk.getBlock(x - xOffset, y - yOffset);
		}
		return null;
	}
	
	public BlockType getBlockFromPos(float x, float y) {
		return getBlockFromTilePos(pixelToTilePosition(x), pixelToTilePosition(y));
	}
	
	public Chunk[][] getLoadedChunks() {
		return loadedChunks;
	}
	
	public void setBlock(BlockType type, float x, float y, boolean callEvents) {
		int tx = pixelToTilePosition(x);
		int ty = pixelToTilePosition(y);
		setBlock(type, tx, ty, callEvents);
	}

	public void setBlock(BlockType type, int tx, int ty, boolean callEvents) {
		Chunk chunk = getChunkFromTilePos(tx, ty);
		if (chunk != null) {
			int xOffset = chunk.getStartX() * Chunk.CHUNK_SIZE;
			int yOffset = chunk.getStartY() * Chunk.CHUNK_SIZE;
			chunk.setBlock(type, tx - xOffset, ty - yOffset, callEvents);
		}
	}
	
	public Chunk[][] getChunks() {
		return chunks;
	}

	public World getWorld() {
		return world;
	}
	
}
