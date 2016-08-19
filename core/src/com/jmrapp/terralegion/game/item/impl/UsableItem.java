package com.jmrapp.terralegion.game.item.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.Item;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.world.World;

/**
 * An item which performs an activity when used via tapping with the item in hand, such as a consumable.
 *
 * Created by jordanb84 on 8/18/16.
 */
public abstract class UsableItem extends Item {

    protected float useDelay;

    public UsableItem(int typeId, ItemCategory category, String name, Drawable icon, int maxItemStack, float useDelay){
        super(typeId, category, name, icon, maxItemStack);
        this.useDelay = useDelay;
    }

    /**
     * Called when the item is used
     *
     * @param world The world in which the item was used
     * @param touchX The x position at which the item was used
     * @param touchY The y position at which the item was used
     */
    public abstract void onUse(World world, float touchX, float touchY);

    public float getUseDelay() {
        return useDelay;
    }
}