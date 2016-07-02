package com.jmr.terraria.game.item.crafting;

import com.jmr.terraria.game.item.ItemStack;
import com.jmr.terraria.game.item.inventory.Inventory;

/**
 * Created by Jon on 10/15/15.
 */
public class CraftingRecipe {

	private ItemStack craftedItem;
	private ItemStack[] requiredItems;

	public CraftingRecipe(ItemStack craftedItem, ItemStack ... requiredItems) {
		this.craftedItem = craftedItem;
		this.requiredItems = requiredItems;
	}

	public ItemStack getCraftedItemStack() {
		return ItemStack.getItemStack(craftedItem);
	}

	public ItemStack[] getRequiredItems() {
		return requiredItems;
	}

	public boolean canCraft(Inventory inventory) {
		for (ItemStack itemStack : requiredItems)
			if (inventory.getTotalCount(itemStack.getItem()) < itemStack.getStack())
				return false;
		return true;
	}

	/**
	 * Removes all of the required items from the inventory. Assumes that the inventory contains all required items.
	 * @param inventory The inventory to remove the required items from
	 * @return The crafted ItemStack
	 */
	public ItemStack craft(Inventory inventory) {
		for (ItemStack itemStack : requiredItems) {
			inventory.removeItemStack(ItemStack.getItemStack(itemStack));
		}
		return getCraftedItemStack();
	}

}
