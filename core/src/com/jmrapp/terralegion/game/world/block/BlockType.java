package com.jmrapp.terralegion.game.world.block;

public enum BlockType {

	AIR(0), DIRT(1), GRASS(2), STONE(3), DIAMOND(4), TORCH(5), STONE_WALL(6), COAL(8), DIRT_WALL(9),
    WOOD(10), LEAVES(11), WOOD_CHEST(14), COVER_GRASS(15), CROP_BLOCK_0(16), CROP_BLOCK_1(17), CROP_BLOCK_2(18), CROP_BLOCK_3(19),
    MUSHROOM_BROWN(21), MUSHROOM_RED(22), MUSHROOM(23), STOVE(28), SAND(30), CACTUS(31), GLASS(32), SAND_WALL(33), SAND_STONE(34), GLASS_HARD(35), APPLE_LEAVES(36), CACTUS_WALL(37), FENCE_WOOD(40),
    FENCE_STONE(41);

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
