package com.jmrapp.terralegion.game.world.block;

import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.world.block.impl.ChestBlock;
import com.jmrapp.terralegion.game.world.block.impl.CropBlock;
import com.jmrapp.terralegion.game.world.block.impl.LightBlock;
import com.jmrapp.terralegion.game.world.block.impl.PlantBlock;
import com.jmrapp.terralegion.game.world.block.impl.WoodBlock;

import java.util.HashMap;

public class BlockManager {

	private static final HashMap<BlockType, Block> blocks = new HashMap<BlockType, Block>();
	private static final BlockType[] blockTypeArray = BlockType.values(); //used becasue calling values() method creates new array each call. Takes up too many resources

	static {
		blocks.put(BlockType.AIR, new Block(BlockType.AIR, null, 0.05f, false, false, 0, 0));
		blocks.put(BlockType.GRASS, new Block(BlockType.GRASS, ResourceManager.getInstance().getDrawable("grass"), .15f, true, false, .5f, .25f));
		blocks.put(BlockType.DIRT, new Block(BlockType.DIRT, ResourceManager.getInstance().getDrawable("dirt"), .15f, true, false, .5f, .25f));
		blocks.put(BlockType.STONE, new Block(BlockType.STONE, ResourceManager.getInstance().getDrawable("stone"), .15f, true, false, .7f, .35f));
		blocks.put(BlockType.DIAMOND, new Block(BlockType.DIAMOND, ResourceManager.getInstance().getDrawable("diamond"), .15f, true, false, .8f, .75f));
		blocks.put(BlockType.COAL, new Block(BlockType.COAL, ResourceManager.getInstance().getDrawable("coalOre"), .15f, true, false, .8f, .35f));
		blocks.put(BlockType.TORCH, new LightBlock(BlockType.TORCH, ResourceManager.getInstance().getDrawable("torch"), .05f, false, 1f, true, .1f, .1f));
		blocks.put(BlockType.STONE_WALL, new Wall(BlockType.STONE_WALL, ResourceManager.getInstance().getDrawable("stoneWall"), .5f, .15f));
		blocks.put(BlockType.DIRT_WALL, new Wall(BlockType.DIRT_WALL, ResourceManager.getInstance().getDrawable("dirtWall"), .5f, .15f));
		blocks.put(BlockType.WOOD, new WoodBlock(BlockType.WOOD, ResourceManager.getInstance().getDrawable("wood"), .15f, false, true, .6f, .3f));
		blocks.put(BlockType.LEAVES, new Block(BlockType.LEAVES, ResourceManager.getInstance().getDrawable("leaves"), .15f, false, false, .5f, .1f));
		blocks.put(BlockType.WOOD_CHEST, new ChestBlock(3, 3, BlockType.WOOD_CHEST, ResourceManager.getInstance().getDrawable("chest"), .15f, true, false, 5f, .1f));
		blocks.put(BlockType.COVER_GRASS, new PlantBlock(BlockType.COVER_GRASS, ResourceManager.getInstance().getDrawable("covergrass"), .01f, false, false, .1f, .1f));
		blocks.put(BlockType.CROP_BLOCK_0, new CropBlock(BlockType.CROP_BLOCK_0, ResourceManager.getInstance().getDrawable("crop0"), 0.1f, false, true, 0.1f, 0.1f));
		blocks.put(BlockType.MUSHROOM_BROWN, new PlantBlock(BlockType.MUSHROOM_BROWN, ResourceManager.getInstance().getDrawable("mushroom0"), .15f, false, false, .5f, .1f));
		blocks.put(BlockType.MUSHROOM_RED, new PlantBlock(BlockType.MUSHROOM_RED, ResourceManager.getInstance().getDrawable("mushroom1"), .15f, false, false, .5f, .1f));
		blocks.put(BlockType.MUSHROOM, new PlantBlock(BlockType.MUSHROOM, ResourceManager.getInstance().getDrawable("mushroom2"), .15f, false, false, .5f, .1f));
		blocks.put(BlockType.STOVE, new Block(BlockType.STOVE, ResourceManager.getInstance().getDrawable("stove"), .1f, false, false, 1, 1f));
		blocks.put(BlockType.SAND, new Block(BlockType.SAND, ResourceManager.getInstance().getDrawable("sand"), .0f, true, false, .5f, .25f));
		blocks.put(BlockType.CACTUS, new PlantBlock(BlockType.CACTUS, ResourceManager.getInstance().getDrawable("cactus"), .01f, false, false, .1f, .1f));
		blocks.put(BlockType.GLASS, new Block(BlockType.GLASS, ResourceManager.getInstance().getDrawable("glass"), .15f, true, true, .1f, .1f));
		blocks.put(BlockType.SAND_WALL, new Wall(BlockType.SAND_WALL, ResourceManager.getInstance().getDrawable("sandWall"), .5f, .15f));
		blocks.put(BlockType.SAND_STONE, new Block(BlockType.SAND_STONE, ResourceManager.getInstance().getDrawable("sandStone"), .15f, true, true, .7f, .35f));
		blocks.put(BlockType.GLASS_HARD, new Block(BlockType.GLASS_HARD, ResourceManager.getInstance().getDrawable("glassHard"), .15f, true, true, .1f, .5f));
		blocks.put(BlockType.APPLE_LEAVES, new Block(BlockType.APPLE_LEAVES, ResourceManager.getInstance().getDrawable("appleLeaves"), .15f, false, false, .5f, .1f));
		blocks.put(BlockType.CACTUS_WALL, new Wall(BlockType.CACTUS_WALL, ResourceManager.getInstance().getDrawable("cactusWall"), 1f, .1f));
		blocks.put(BlockType.FENCE_WOOD, new Block(BlockType.FENCE_WOOD, ResourceManager.getInstance().getDrawable("fence_wood"), .15f, false, true, .7f, .35f));
		blocks.put(BlockType.FENCE_STONE, new Block(BlockType.FENCE_STONE, ResourceManager.getInstance().getDrawable("fence_stone"), .15f, false, true, .7f, .35f));
	}
	
	public static Block getBlock(BlockType type) {
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
