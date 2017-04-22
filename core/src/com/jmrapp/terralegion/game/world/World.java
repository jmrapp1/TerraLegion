package com.jmrapp.terralegion.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmrapp.terralegion.engine.camera.OrthoCamera;
import com.jmrapp.terralegion.engine.utils.Settings;
import com.jmrapp.terralegion.engine.world.PhysicsWorld;
import com.jmrapp.terralegion.game.io.LoadedWorldInfo;
import com.jmrapp.terralegion.game.io.WorldIO;
import com.jmrapp.terralegion.game.item.ItemManager;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;
import com.jmrapp.terralegion.game.world.entity.impl.Bunny;
import com.jmrapp.terralegion.game.world.entity.impl.Hedgehog;
import com.jmrapp.terralegion.game.world.entity.impl.Player;

public class World {

	private ChunkManager chunkManager;
	private PhysicsWorld physicsWorld;
	private String worldName;
	private Player player;
	private Bunny bunny;
	private long seed;
	
	public World(String worldName, long seed) {
		this.worldName = worldName;
		this.seed = seed;
		chunkManager = new ChunkManager(this, seed);
		physicsWorld = new PhysicsWorld(chunkManager, 0, -12f);
		physicsWorld.setMaxYVelocity(8);
		physicsWorld.setMaxXVelocity(4f);
		physicsWorld.setXFriction(6f);
		player = new Player(1300, ChunkManager.CHUNKS_Y * Chunk.CHUNK_SIZE * ChunkManager.TILE_SIZE - 200);
		player.getInventory().addItemStack(ItemManager.getInstance().getItem(ItemType.WOODEN_PICKAXE), 1);
		player.getInventory().addItemStack(ItemManager.getInstance().getItem(BlockType.TORCH), 25);
		player.getInventory().addItemStack(ItemManager.getInstance().getItem(ItemType.SWORD), 1);
		physicsWorld.setGravityToEntity(player);
		chunkManager.updateChunks(physicsWorld, player.getX(), player.getY());

		WorldIO.saveWorld(this);

		Chunk chunk1 = chunkManager.getChunkFromPos(player.getX(), player.getY());
		for (int i = 0; i < 1; i++) {
			//bunny = new Bunny(player.getX(), player.getY());
			//chunk1.addEntity(bunny);
			//physicsWorld.setGravityToEntity(bunny);

			//Hedgehog hedgehog = new Hedgehog(player.getX(), player.getY());
			//chunk1.addEntity(hedgehog);
			//physicsWorld.setGravityToEntity(hedgehog);
		}
	}
	
	public World(String worldName) {
		this.worldName = worldName;
		chunkManager = new ChunkManager(this);
		
		LoadedWorldInfo worldInfo = WorldIO.loadWorld(this); //Load world
		seed = worldInfo.getSeed();
		chunkManager.loadSeed(seed);
		chunkManager.generateTerrain();
		player = worldInfo.getPlayer();

		physicsWorld = new PhysicsWorld(chunkManager, 0, -12f);
		physicsWorld.setMaxYVelocity(8);
		physicsWorld.setMaxXVelocity(5);
		physicsWorld.setXFriction(2.5f);
		physicsWorld.setGravityToEntity(player);

		chunkManager.updateChunks(physicsWorld, player.getX(), player.getY());
	}
	
	public void update(OrthoCamera camera) {
		chunkManager.updateChunks(physicsWorld, player.getX(), player.getY());
		physicsWorld.updateWorldBody(player);
		physicsWorld.focusCamera(player, Settings.getWidth(), Settings.getHeight(), 50000, 50000, camera);
		chunkManager.updateChunks(physicsWorld, player.getX(), player.getY());
		if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
			Chunk chunk1 = chunkManager.getChunkFromPos(player.getX(), player.getY());
			bunny = new Bunny(player.getX(), player.getY());
			chunk1.addEntity(bunny);
			physicsWorld.setGravityToEntity(bunny);
		}
		DayManager.getInstance().update();
	}
	
	public void render(SpriteBatch sb, OrthoCamera camera) {
		chunkManager.renderOnScreen(sb, camera, camera.getPos().x, camera.getPos().y);
		player.render(sb, chunkManager.getLightValueFromPos(player.getX(), player.getY()));
	}
	
	public ChunkManager getChunkManager() {
		return chunkManager;
	}
	
	public PhysicsWorld getPhysicsWorld() {
		return physicsWorld;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public String getWorldName() {
		return worldName;
	}
	
}
