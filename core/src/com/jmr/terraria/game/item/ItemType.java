package com.jmr.terraria.game.item;

public enum ItemType {

	WOODEN_PICKAXE(7), SWORD(12);

	private final int id;

	private ItemType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
