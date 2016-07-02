package com.jmr.terraria.game.item;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Jon on 10/1/15.
 */
public class ItemStack {

	private static final Array<ItemStack> itemStackQueue = new Array<ItemStack>();

	private Item item;
	private int stack;

	private ItemStack(Item item, int stack) {
		this.item = item;
		this.stack = stack;
	}

	public void set(Item item, int stack) {
		this.item = item;
		this.stack = stack;
	}

	public Item getItem() {
		return item;
	}

	public int getStack() {
		return stack;
	}

	public void setStack(int stack) {
		this.stack = stack;
	}

	/**
	 * Adds to the size of the stack. This method should NOT be used to remove from the itemStack.
	 *
	 * @param amount The amount to add
	 * @return The remainder if it exceeds the max stack size;
	 */
	public int addStack(int amount) {
		stack += amount;
		if (stack > item.getMaxItemStack()) {
			int remainder = stack - item.getMaxItemStack();
			stack = item.getMaxItemStack();
			return remainder;
		}
		return 0;
	}

	/**
	 * Adds to the size of the stack. Merges two itemStacks together. This method should NOT be used to remove from the itemStack
	 * NOTE: Assumes that the itemStack has the same item type as this itemStack.
	 * @param itemStack The itemStack to merge
	 * @return The itemStack that was merged into this itemStack.
	 */
	public ItemStack mergeStack(ItemStack itemStack) {
		stack += itemStack.getStack();
		if (stack > item.getMaxItemStack()) {
			int remainder = stack - item.getMaxItemStack();
			stack = item.getMaxItemStack();
			itemStack.stack = remainder;
		} else {
			itemStack.stack = 0;
		}
		return itemStack;
	}

	/**
	 * Remove from the itemstack.
	 *
	 * @param amount The amount to remove
	 * @return The remainder if more was removed than what was in the stack
	 */
	public int removeStack(int amount) {
		stack -= amount;
		if (stack < 0) {
			int remainder = stack * -1;
			stack = 0;
			return remainder;
		}
		return 0;
	}

	/**
	 * Removes the stack size of itemStack from this itemStack. If it goes below 0 then the itemStack gets the remainder
	 * @param itemStack The itemStack to subtract from this itemStack
	 * @return The itemStack that was used to subtract from this itemStack. Will have a stack size > 0 if there was a remainder
	 */
	public ItemStack removeStack(ItemStack itemStack) {
		stack -= itemStack.getStack();
		if (stack < 0) {
			itemStack.stack = stack * -1; //Get the remainder
			stack = 0;
		} else {
			itemStack.stack = 0;
		}
		return itemStack;
	}

	public void destroy() {
		itemStackQueue.add(this);
	}

	public static ItemStack getItemStack(ItemStack stack) {
		return getItemStack(stack.getItem(), stack.getStack());
	}

	public static ItemStack getItemStack(Item item, int stack) {
		if (itemStackQueue.size > 0) {
			ItemStack itemStack = itemStackQueue.removeIndex(0);
			itemStack.set(item, stack);
			return itemStack;
		}
		return new ItemStack(item, stack);
	}

}
