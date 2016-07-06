package com.jmrapp.terralegion.test;

import com.jmrapp.terralegion.game.item.ItemManager;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.item.inventory.Inventory;
import com.jmrapp.terralegion.game.world.block.BlockType;

/**
 * Created by Jon on 7/1/16.
 */
public class InventoryTest {


	public static void main(String[] args){
		Inventory inventory = new Inventory(5, 5);
		ItemStack itemStack = ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.DIRT), 8);
		inventory.addItemStack(itemStack);
		System.out.println("Added itemstack");
		print(inventory);
		inventory.setItemStack(ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.DIRT), 4), 1, 0);
		System.out.println("Added itemstack");
		print(inventory);
		inventory.addItemStack(ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.STONE), 64));
		System.out.println("Added itemstack");
		print(inventory);
		inventory.removeItemStack(ItemStack.getItemStack(ItemManager.getInstance().getItem(BlockType.DIRT), 11));
		System.out.println("Removed itemstack");
		print(inventory);
		inventory.addItemStack(ItemManager.getInstance().getItem(BlockType.DIRT), 1);
		System.out.println("Added itemstack");
		print(inventory);
	}

	private static void print(Inventory inventory) {
		for (int y = 0; y < 5; y++) {
			String line = "[";
			for (int x = 0; x < 5; x++) {
				if (inventory.getItemStack(x, y) != null) {
					ItemStack is = inventory.getItemStack(x, y);
					line += " " + is.getItem().getTypeId() + ":" + is.getStack() + ",";
				} else {
					line += " empty,";
				}
			}
			line += "]";
			System.out.println(line);
		}
	}

}
