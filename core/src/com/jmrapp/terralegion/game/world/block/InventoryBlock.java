package com.jmrapp.terralegion.game.world.block;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.inventory.Inventory;

/**
 * Provides a block which contains an inventory
 *
 * Created by jordanb84 on 8/2/16.
 */
public abstract class InventoryBlock extends Block{

    /** For management of the block's inventory **/
    private Inventory inventory;

    public InventoryBlock(int inventoryWidth, int inventoryHeight ,BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, boolean transparent, float initHealth, float resistance){
        super(type, drawable, lightBlockingAmount, collides, transparent, initHealth, resistance);

        this.inventory = new Inventory(inventoryWidth, inventoryHeight);
    }

    /**
     * @return {@link InventoryBlock#inventory}
     */
    public Inventory getInventory(){
        return this.inventory;
    }

    /**
     * @param inventory {@link InventoryBlock#inventory}
     */
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }

}
