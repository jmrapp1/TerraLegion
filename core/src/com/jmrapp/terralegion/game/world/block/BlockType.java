package com.jmrapp.terralegion.game.world.block;

public enum BlockType {

	AIR(0), DIRT(1), GRASS(2), STONE(3), DIAMOND(4), TORCH(5), STONE_WALL(6), COAL(8), DIRT_WALL(9),
    WOOD(10), LEAVES(11), WOOD_CHEST(12);

    private static final BlockType[] blockTypeArray = values();
	private final int id;
	
    BlockType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BlockType getBlockType(int id) {
        for (BlockType type : blockTypeArray) {
            if (type.getId() == id)
                return type;
        }
        return AIR;
    }

}
