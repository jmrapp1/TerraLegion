package com.jmrapp.terralegion.game.item.crafting;

import com.badlogic.gdx.utils.Array;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.item.ItemManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.block.BlockType;

import java.util.HashMap;

/**
 * Created by Jon on 10/15/15.
 */
public class CraftingRecipes {

	private static final CraftingRecipes instance = new CraftingRecipes();

	private HashMap<Integer, CraftingRecipe> recipes = new HashMap<Integer, CraftingRecipe>();
	private HashMap<ItemCategory, Array<CraftingRecipe>> cachedCategoryRecipes = new HashMap<ItemCategory, Array<CraftingRecipe>>();

	public CraftingRecipes() {
		recipes.put(BlockType.TORCH.getId(), new CraftingRecipe(ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.TORCH), 3), ItemStack.getItemStack(ItemManager.getInstance().getItem(ItemType.COAL), 1), ItemStack.getItemStack(ItemManager.getInstance().getItem(ItemType.STICK), 2)));
		recipes.put(ItemType.WOODEN_PICKAXE.getId(), new CraftingRecipe(ItemStack.getItemStack(ItemManager.getInstance().getItem(ItemType.WOODEN_PICKAXE), 1), ItemStack.getItemStack(ItemManager.getInstance().getItem(ItemType.STICK), 3), ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.STONE), 5)));
		recipes.put(BlockType.FENCE_WOOD.getId(), new CraftingRecipe(ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.FENCE_WOOD), 1), ItemStack.getItemStack(ItemManager.getInstance().getItem(ItemType.STICK), 5)));
		recipes.put(BlockType.FENCE_STONE.getId(), new CraftingRecipe(ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.FENCE_STONE), 1), ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.STONE), 5)));
	}

	/**
	 * Find all items that are craftable that are in a specific category.
	 * To save time after the first run, cache the results and return them in the future
	 *
	 * @param category The category
	 * @return
	 */
	public Array<CraftingRecipe> getCraftableItems(ItemCategory category) {
		if (!cachedCategoryRecipes.containsKey(category)) {
			Array<CraftingRecipe> recipesFound = new Array<CraftingRecipe>();
			for (CraftingRecipe recipe : recipes.values()) {
				if (recipe.getCraftedItemStack().getItem().getCategory() == category) {
					recipesFound.add(recipe);
				}
			}
			cachedCategoryRecipes.put(category, recipesFound);
			return recipesFound;
		}
		return cachedCategoryRecipes.get(category);
	}

	public static CraftingRecipes getInstance() {
		return instance;
	}

}
