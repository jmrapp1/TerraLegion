package com.jmrapp.terralegion.game.item.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.world.World;

/**
 * Will be used for farming crops. May need a better, but still simple approach to tools for this
 * to be implemented in a proper manner.
 *
 * Created by root on 8/5/16.
 */
public class SeedItem extends UsableItem {

    public SeedItem(int typeId, ItemCategory category, String name, Drawable icon, int maxItemStack, float useDelay){
        super(typeId, category, name, icon, maxItemStack, useDelay);
    }

    @Override
    public void onUse(World world, float touchX, float touchY) {
        
    }
}