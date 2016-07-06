package com.jmrapp.terralegion.game.item.inventory;

import com.jmrapp.terralegion.game.item.Item;
import com.jmrapp.terralegion.game.item.ItemStack;
import com.jmrapp.terralegion.game.utils.Tuple;

/**
 * Created by Jon on 10/1/15.
 */
public class Inventory {

	private ItemStack[][] inventory;
	private int width, height;
	private boolean wasChanged;

	public Inventory(int width, int height) {
		inventory = new ItemStack[width][height];
		this.width = width;
		this.height = height;
	}

	public void setItemStack(ItemStack itemStack, int x, int y) {
		inventory[x][y] = itemStack;
		wasChanged = true;
	}

	public ItemStack getItemStack(int x, int y) {
		return inventory[x][y];
	}

	public void destroyItemStack(int x, int y) {
		if (inventory[x][y] != null) {
			inventory[x][y].destroy();
		}
		inventory[x][y] = null;
		wasChanged = true;
	}

	public boolean removeItemStack(Item item, int count) {
		boolean changeFlag = false;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inventory[x][y] != null && inventory[x][y].getItem().getTypeId() == item.getTypeId()) {
					ItemStack stack = inventory[x][y]; //Get the itemstack in its place
					int remainder = stack.removeStack(count);
					wasChanged = true;
					if (remainder > 0) { //If there is more to be removed then it means the current itemstack is a stack size of 0
						if (stack.getStack() == 0) { //Double check it is 0
							stack.destroy(); //Destroy it
							inventory[x][y] = null; //Set it to null
							changeFlag = true;
						}
						count -= (count - remainder);
					} else if (remainder == 0) {
						if (stack.getStack() == 0) { //Double check it is 0
							stack.destroy(); //Destroy it
							inventory[x][y] = null; //Set it to null
						}
						return true;
					} else { //Else there was no remainder and we
						return true;
					}
				}
			}
		}
		return changeFlag;
	}

	public boolean removeItemStack(ItemStack itemStack) {
		boolean changeFlag = false;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inventory[x][y] != null && inventory[x][y].getItem().getTypeId() == itemStack.getItem().getTypeId()) {
					ItemStack stack = inventory[x][y]; //Get the itemstack in its place
					wasChanged = true;
					if (stack.removeStack(itemStack).getStack() > 0) { //If there is more to be removed then it means the current itemstack in the inventory is a stack size of 0
						if (stack.getStack() == 0) { //Double check it is 0
							stack.destroy(); //Destroy it
							inventory[x][y] = null; //Set it to null
							changeFlag = true;
						}
					} else if (stack.getStack() == 0) {
						if (stack.getStack() == 0) { //Double check it is 0
							stack.destroy(); //Destroy it
							inventory[x][y] = null; //Set it to null
						}
						return true;
					} else { //Else there was no remainder and we
						return true;
					}
				}
			}
		}
		return changeFlag;
	}

	/**
	 * Add an itemStack to the inventory.
	 *
	 * @param itemStack The itemStack to add
	 * @return Whether it was added. False if the inventory is full.
	 */
	public boolean addItemStack(ItemStack itemStack){
		int openX = -1, openY = -1; //Open spot holder
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inventory[x][y] != null) { //If it is not empty
					if (inventory[x][y].getItem().getTypeId() == itemStack.getItem().getTypeId()) { //if it is the same item
						int count = itemStack.getStack(); //hold original count
						inventory[x][y].mergeStack(itemStack); //Merge. Will set the remainder to the current stack
						if (itemStack.getStack() != count) {
							if (itemStack.getStack() > 0) { //There was a remainder and less than the original so add it to the inventory
								return addItemStack(itemStack);
							} else { //There was no remainder so destroy the itemStack
								itemStack.destroy();
								wasChanged = true;
								return true;
							}
						}
					}
				} else { //It's an open spot
					if (openX == -1 && openY == -1){ //Didn't process yet
						openX = x;
						openY = y;
					}
				}
			}
		}
		if (openX != -1 && openY != -1) { //Does not contain the item so lets add it
			inventory[openX][openY] = itemStack;
			wasChanged = true;
			return true;
		}
		return false;
	}

	public Tuple<Integer, Integer> getOpenSpot() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inventory[x][y] == null) {
					return new Tuple<Integer, Integer>(x, y);
				}
			}
		}
		return null;
	}

	public boolean addItemStack(Item item, int count){
		int openX = -1, openY = -1, remainder = 0; //Open spot holder
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inventory[x][y] != null) { //If it is not empty
					if (inventory[x][y].getItem().getTypeId() == item.getTypeId()) { //if it is the same item
						//add to the current stack. Then if the remainder is greater than 0 then add it to the inventory
						remainder = inventory[x][y].addStack(count); //Merge. Will set the remainder to the current stack
						if (remainder != count) {
							if (remainder > 0) { //There was a remainder and less than the original so add it to the inventory
								return addItemStack(item, count); //recursively add it back
							} else { //There was no remainder so return
								wasChanged = true;
								return true;
							}
						}
					}
				} else { //It's an open spot
					if (openX == -1 && openY == -1){ //Didn't process yet
						openX = x;
						openY = y;
					}
				}
			}
		}
		if (openX != -1 && openY != -1) { //Does not contain the item so lets add it
			inventory[openX][openY] = ItemStack.getItemStack(item, count);
			wasChanged = true;
			return true;
		}
		return false;
	}

	public int getTotalCount(Item item) {
		int total = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (inventory[x][y] != null && inventory[x][y].getItem().getTypeId() == item.getTypeId()) {
					total += inventory[x][y].getStack();
				}
			}
		}
		return total;
	}

	public boolean containsItemStack(ItemStack itemStack) {
		int totalCount = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ItemStack is = inventory[x][y];
				if (is != null) {
					if (is.getItem() == itemStack.getItem()) {
						totalCount += is.getStack();
						if (totalCount >= itemStack.getStack()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public void resetChangedFlag() {
		wasChanged = false;
	}

	public boolean wasChanged() {
		return wasChanged;
	}

	public boolean isFull() {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (inventory[x][y] == null)
					return false;
		return true;
	}

	public boolean hasItem(Item item) {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (inventory[x][y] != null)
					if (inventory[x][y].getItem().getTypeId() == item.getTypeId())
						return true;
		return false;
	}

	public void clearInventory() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				destroyItemStack(x, y);
			}
		}
	}

	public ItemStack[][] getInventory() {
		return inventory;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
