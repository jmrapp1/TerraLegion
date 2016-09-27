package com.jmrapp.terralegion.game.item;

import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.item.impl.BlockItem;
import com.jmrapp.terralegion.game.item.impl.CookableItem;
import com.jmrapp.terralegion.game.item.impl.FishItem;
import com.jmrapp.terralegion.game.item.impl.PickaxeItem;
import com.jmrapp.terralegion.game.item.impl.SeedItem;
import com.jmrapp.terralegion.game.item.impl.SwordItem;
import com.jmrapp.terralegion.game.item.impl.UsableItem;
import com.jmrapp.terralegion.game.world.block.BlockType;

import java.util.HashMap;

/**
 * Created by Jon on 10/1/15.
 */
public class ItemManager {

	private static final ItemManager instance = new ItemManager();
	private final HashMap<Integer, Item> items = new HashMap<Integer, Item>();
	private final ItemType[] itemTypeArray = ItemType.values();

	private ItemManager() {
		items.put(BlockType.DIRT.getId(), new BlockItem(BlockType.DIRT, ItemCategory.BLOCK, "Dirt", ResourceManager.getInstance().getDrawable("dirt"), 64));
		items.put(BlockType.GRASS.getId(), new BlockItem(BlockType.GRASS, ItemCategory.BLOCK, "Grass", ResourceManager.getInstance().getDrawable("grass"), 64));
		items.put(BlockType.STONE.getId(), new BlockItem(BlockType.STONE, ItemCategory.BLOCK, "Stone", ResourceManager.getInstance().getDrawable("stone"), 64));
		items.put(BlockType.TORCH.getId(), new BlockItem(BlockType.TORCH, ItemCategory.FURNITURE, "Torch", ResourceManager.getInstance().getDrawable("torch"), 64));
		items.put(BlockType.DIAMOND.getId(), new BlockItem(BlockType.DIAMOND, ItemCategory.BLOCK, "Diamond Ore", ResourceManager.getInstance().getDrawable("diamond"), 64));
		items.put(BlockType.COAL.getId(), new CookableItem(BlockType.COAL.getId(), ItemType.COAL.getId(), ItemCategory.BLOCK, "Coal Ore", ResourceManager.getInstance().getDrawable("coalOre"), 64, 1, 128));
		items.put(ItemType.WOODEN_PICKAXE.getId(), new PickaxeItem(ItemType.WOODEN_PICKAXE, "Wooden Pickaxe", ResourceManager.getInstance().getDrawable("woodenPickaxe"), 1, 4, 1.0f, 100, .5f));
		//items.put(BlockType.WOOD.getId(), new Item(ItemType.STICK.getId(), ItemCategory.MISC, "Stick", ResourceManager.getInstance().getDrawable("stick"), 64));
		items.put(BlockType.LEAVES.getId(), new BlockItem(BlockType.LEAVES, ItemCategory.BLOCK, "Leaves", ResourceManager.getInstance().getDrawable("leaves"), 64));
		items.put(ItemType.SWORD.getId(), new SwordItem(ItemType.SWORD, "Fiery Sword", ResourceManager.getInstance().getDrawable("sword"), 1, 6, 1.0f, 50, 100.0f));
		items.put(ItemType.STICK.getId(), new Item(ItemType.STICK.getId(), ItemCategory.MISC, "Stick", ResourceManager.getInstance().getDrawable("stick"), 64));
		items.put(BlockType.WOOD_CHEST.getId(), new BlockItem(BlockType.WOOD_CHEST, ItemCategory.BLOCK, "Wooden Chest", ResourceManager.getInstance().getDrawable("chest"), 64));
		items.put(BlockType.COVER_GRASS.getId(), new BlockItem(BlockType.COVER_GRASS, ItemCategory.BLOCK, "Cover Grass", ResourceManager.getInstance().getDrawable("covergrass"), 64));
		items.put(ItemType.SEED.getId(), new SeedItem(ItemType.SEED.getId(), ItemCategory.MISC, "Seed", ResourceManager.getInstance().getDrawable("seed"), 64, 1.0f));
		items.put(BlockType.MUSHROOM_BROWN.getId(), new BlockItem(BlockType.MUSHROOM_BROWN, ItemCategory.MISC, "Brown Mushroom", ResourceManager.getInstance().getDrawable("mushroom0"), 64));
		items.put(BlockType.MUSHROOM_RED.getId(), new BlockItem(BlockType.MUSHROOM_RED, ItemCategory.MISC, "Red Mushroom", ResourceManager.getInstance().getDrawable("mushroom1"), 64));
		items.put(BlockType.MUSHROOM.getId(), new BlockItem(BlockType.MUSHROOM, ItemCategory.MISC, "Mushroom", ResourceManager.getInstance().getDrawable("mushroom2"), 64));
		items.put(ItemType.FISH.getId(), new FishItem(ItemType.FISH.getId(), ItemType.FISH_COOKED.getId(), ItemCategory.FOOD, "Fish", ResourceManager.getInstance().getDrawable("fish"), 64, 1, 128));
		items.put(ItemType.FISH_COOKED.getId(), new Item(ItemType.FISH_COOKED.getId(), ItemCategory.FOOD, "Fish", ResourceManager.getInstance().getDrawable("fish_cooked"), 64));
		items.put(BlockType.STOVE.getId(), new BlockItem(BlockType.STOVE, ItemCategory.MISC, "Stove", ResourceManager.getInstance().getDrawable("stove"), 64));
		items.put(BlockType.SAND.getId(), new CookableItem(BlockType.SAND.getId(), BlockType.GLASS.getId(), ItemCategory.BLOCK, "Sand", ResourceManager.getInstance().getDrawable("sand"), 64, 1f, 128f));
		items.put(BlockType.CACTUS.getId(), new CookableItem(BlockType.CACTUS.getId(), BlockType.CACTUS_WALL.getId(), ItemCategory.BLOCK, "Cactus", ResourceManager.getInstance().getDrawable("cactus"), 64, 1, 128));
		items.put(BlockType.GLASS.getId(), new BlockItem(BlockType.GLASS, ItemCategory.BLOCK, "Glass", ResourceManager.getInstance().getDrawable("glass"), 64));
		items.put(BlockType.SAND_STONE.getId(), new CookableItem(BlockType.SAND_STONE.getId(), BlockType.GLASS_HARD.getId(), ItemCategory.BLOCK, "Sand Stone", ResourceManager.getInstance().getDrawable("sandStone"), 64, 1, 128));
		items.put(BlockType.GLASS_HARD.getId(), new BlockItem(BlockType.GLASS_HARD, ItemCategory.BLOCK, "Tough Glass", ResourceManager.getInstance().getDrawable("glassHard"), 64));
		items.put(ItemType.APPLE.getId(), new Item(ItemType.APPLE.getId(), ItemCategory.FOOD, "Apple", ResourceManager.getInstance().getDrawable("apple"), 64));
		items.put(ItemType.COAL.getId(), new Item(ItemType.COAL.getId(), ItemCategory.MISC, "Coal", ResourceManager.getInstance().getDrawable("coal"), 64));
		items.put(BlockType.CACTUS_WALL.getId(), new BlockItem(BlockType.CACTUS_WALL, ItemCategory.BLOCK, "Cactus Wall", ResourceManager.getInstance().getDrawable("cactusWall"), 64));
		items.put(BlockType.FENCE_WOOD.getId(), new BlockItem(BlockType.FENCE_WOOD, ItemCategory.FURNITURE, "Wooden Fence", ResourceManager.getInstance().getDrawable("fence_wood"), 64));
		items.put(BlockType.FENCE_STONE.getId(), new BlockItem(BlockType.FENCE_STONE, ItemCategory.FURNITURE, "Stone Fence", ResourceManager.getInstance().getDrawable("fence_stone"), 64));
	}

	public Item getItem(ItemType type) {
		return items.get(type.getId());
	}

	public Item getItem(BlockType type) {
		return items.get(type.getId());
	}

	public Item getItem(int id) {
		return items.get(id);
	}

	public ItemType getItemType(int id) {
		for (ItemType type : itemTypeArray)
			if (type.getId() == id)
				return type;
		return null;
	}

	public static ItemManager getInstance() {
		return instance;
	}

}
