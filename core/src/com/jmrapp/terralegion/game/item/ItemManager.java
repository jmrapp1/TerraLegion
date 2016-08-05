package com.jmrapp.terralegion.game.item;

import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.item.impl.BlockItem;
import com.jmrapp.terralegion.game.item.impl.PickaxeItem;
import com.jmrapp.terralegion.game.item.impl.SwordItem;
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
		items.put(BlockType.COAL.getId(), new BlockItem(BlockType.COAL, ItemCategory.BLOCK, "Coal Ore", ResourceManager.getInstance().getDrawable("coal"), 64));
		items.put(ItemType.WOODEN_PICKAXE.getId(), new PickaxeItem(ItemType.WOODEN_PICKAXE, "Wooden Pickaxe", ResourceManager.getInstance().getDrawable("woodenPickaxe"), 1, 4, 1.0f, 100, .5f));
		//items.put(BlockType.WOOD.getId(), new Item(ItemType.STICK.getId(), ItemCategory.MISC, "Stick", ResourceManager.getInstance().getDrawable("stick"), 64));
		items.put(BlockType.LEAVES.getId(), new BlockItem(BlockType.LEAVES, ItemCategory.BLOCK, "Leaves", ResourceManager.getInstance().getDrawable("leaves"), 64));
		items.put(ItemType.SWORD.getId(), new SwordItem(ItemType.SWORD, "Fiery Sword", ResourceManager.getInstance().getDrawable("sword"), 1, 6, 1.0f, 50, 1.0f));
		items.put(ItemType.STICK.getId(), new Item(ItemType.STICK.getId(), ItemCategory.MISC, "Stick", ResourceManager.getInstance().getDrawable("stick"), 64));
		items.put(BlockType.WOOD_CHEST.getId(), new BlockItem(BlockType.WOOD_CHEST, ItemCategory.BLOCK, "Wooden Chest", ResourceManager.getInstance().getDrawable("chest"), 64));
		items.put(BlockType.COVER_GRASS.getId(), new BlockItem(BlockType.COVER_GRASS, ItemCategory.BLOCK, "Cover Grass", ResourceManager.getInstance().getDrawable("covergrass"), 64));
		items.put(ItemType.SEED.getId(), new Item(ItemType.SEED.getId(), ItemCategory.MISC, "Seed", ResourceManager.getInstance().getDrawable("seed"), 64));
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
