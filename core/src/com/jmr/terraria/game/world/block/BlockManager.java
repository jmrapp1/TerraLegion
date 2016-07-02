package com.jmr.terraria.game.world.block;

import com.jmr.terraria.engine.views.drawables.ResourceManager;
import com.jmr.terraria.game.world.block.impl.LightBlock;

import java.util.HashMap;

public class BlockManager {

	private static final HashMap<BlockType, com.jmr.terraria.game.world.block.Block> blocks = new HashMap<BlockType, com.jmr.terraria.game.world.block.Block>();
	private static final BlockType[] blockTypeArray = BlockType.values(); //used becasue calling values() method creates new array each call. Takes up too many resources

	static {
		blocks.put(BlockType.AIR, new com.jmr.terraria.game.world.block.Block(BlockType.AIR, null, 0.05f, false, false, 0, 0));
		blocks.put(BlockType.GRASS, new com.jmr.terraria.game.world.block.Block(BlockType.GRASS, ResourceManager.getInstance().getDrawable("grass"), .15f, true, false, .5f, .25f));
		blocks.put(BlockType.DIRT, new com.jmr.terraria.game.world.block.Block(BlockType.DIRT, ResourceManager.getInstance().getDrawable("dirt"), .15f, true, false, .5f, .25f));
		blocks.put(BlockType.STONE, new com.jmr.terraria.game.world.block.Block(BlockType.STONE, ResourceManager.getInstance().getDrawable("stone"), .15f, true, false, .7f, .35f));
		blocks.put(BlockType.DIAMOND, new com.jmr.terraria.game.world.block.Block(BlockType.DIAMOND, ResourceManager.getInstance().getDrawable("diamond"), .15f, true, false, .8f, .75f));
		blocks.put(BlockType.COAL, new com.jmr.terraria.game.world.block.Block(BlockType.COAL, ResourceManager.getInstance().getDrawable("coal"), .15f, true, false, .8f, .35f));
		blocks.put(BlockType.TORCH, new LightBlock(BlockType.TORCH, ResourceManager.getInstance().getDrawable("torch"), .05f, false, 1f, true, .1f, .1f));
		blocks.put(BlockType.STONE_WALL, new Wall(BlockType.STONE_WALL, ResourceManager.getInstance().getDrawable("stoneWall"), .5f, .15f));
		blocks.put(BlockType.DIRT_WALL, new Wall(BlockType.DIRT_WALL, ResourceManager.getInstance().getDrawable("dirtWall"), .5f, .15f));
		blocks.put(BlockType.WOOD, new com.jmr.terraria.game.world.block.Block(BlockType.WOOD, ResourceManager.getInstance().getDrawable("wood"), .15f, true, false, .6f, .3f));
		blocks.put(BlockType.LEAVES, new com.jmr.terraria.game.world.block.Block(BlockType.LEAVES, ResourceManager.getInstance().getDrawable("leaves"), .15f, true, false, .5f, .1f));
	}
	
	public static com.jmr.terraria.game.world.block.Block getBlock(BlockType type) {
		return blocks.get(type);
	}

	public static BlockType getBlockType(int id) {
		for (BlockType type : blockTypeArray) {
			if (type.getId() == id) {
				return type;
			}
		}
		return null;
	}

}
