package com.jmrapp.terralegion.game.item;

public enum ItemType {

	WOODEN_PICKAXE(7), SWORD(12), STICK(13), SEED(20), MUSHROOM_BROWN(24), MUSHROOM_RED(25), MUSHROOM(26), FISH(27), FISH_COOKED(29), APPLE(38), COAL(39);

	private final int id;

	private ItemType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
