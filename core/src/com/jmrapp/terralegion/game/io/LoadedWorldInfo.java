package com.jmrapp.terralegion.game.io;

import com.jmrapp.terralegion.game.world.entity.impl.Player;

public class LoadedWorldInfo {

	private Player player;
	private int worldChunkSizeWidth, worldChunkSizeHeight;
	private long seed;
	
	public LoadedWorldInfo(Player player, long seed, int worldChunkSizeWidth, int worldChunkSizeHeight) {
		this.player = player;
		this.seed = seed;
		this.worldChunkSizeWidth = worldChunkSizeWidth;
		this.worldChunkSizeHeight = worldChunkSizeHeight;
	}

	public Player getPlayer() {
		return player;
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
