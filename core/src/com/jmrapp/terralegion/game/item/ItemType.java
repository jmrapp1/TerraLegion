package com.jmrapp.terralegion.game.item;

public enum ItemType {

	WOODEN_PICKAXE(7), SWORD(12), STICK(13);

	private final int id;

	private ItemType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
