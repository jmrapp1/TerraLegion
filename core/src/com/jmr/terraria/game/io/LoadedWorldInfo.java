package com.jmr.terraria.game.io;

public class LoadedWorldInfo {

	private float playerX, playerY;
	private int worldChunkSizeWidth, worldChunkSizeHeight;
	private long seed;
	
	public LoadedWorldInfo(float playerX, float playerY, long seed, int worldChunkSizeWidth, int worldChunkSizeHeight) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.seed = seed;
		this.worldChunkSizeWidth = worldChunkSizeWidth;
		this.worldChunkSizeHeight = worldChunkSizeHeight;
	}
	
	public float getPlayerX() {
		return playerX;
	}

	public float getPlayerY() {
		return playerY;
	}
	
	public long getSeed() {
		return seed;
	}
	
	public int getWorldChunkSizeWidth() {
		return worldChunkSizeWidth;
	}
	
	public int getWorldChunkSizeHeight() {
		return worldChunkSizeHeight;
	}
	
}
