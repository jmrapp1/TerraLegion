package com.jmr.terraria.game.world.block;

public enum BlockType {

	AIR(0), DIRT(1), GRASS(2), STONE(3), DIAMOND(4), TORCH(5), STONE_WALL(6), COAL(8), DIRT_WALL(9),
    WOOD(10), LEAVES(11);
	
	private final int id;
	
	private BlockType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
