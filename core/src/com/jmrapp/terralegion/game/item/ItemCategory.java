package com.jmrapp.terralegion.game.item;

public enum ItemCategory {

	BLOCK(1),
    TOOL(2),
    FURNITURE(3),
    MISC(4),
    FOOD(5);

	private final int id;

	private ItemCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
