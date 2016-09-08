package com.jmrapp.terralegion.game.item.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.game.item.ItemCategory;
import com.jmrapp.terralegion.game.item.ItemType;
import com.jmrapp.terralegion.game.world.World;
import com.jmrapp.terralegion.game.world.entity.LivingEntity;
import com.jmrapp.terralegion.game.world.entity.impl.Bunny;

/**
 * Created by jordanb84 on 9/7/16.
 *
 * Fish items are cookable items which attract bunny entities within a radius when clicked
 */
public class FishItem extends CookableItem {

    public FishItem(int typeId, int returnItem, ItemCategory category, String name, Drawable icon, int maxItemStack, float useDelay, float reach){
        super(typeId, returnItem, category, name, icon, maxItemStack, useDelay, reach);
    }

    @Override
    public boolean onUse(World world, float touchX, float touchY) {
        super.onUse(world, touchX, touchY);

        for(LivingEntity entity : world.getChunkManager().getChunkFromPos(touchX, touchY).findLivingEntitiesInRange(touchX, touchY, getReach())){
            if (entity instanceof Bunny) {
                float distanceX = (world.getPlayer().getX() - entity.getX());
                float distanceY = (world.getPlayer().getY() - entity.getY());

                entity.addVelocity(distanceX / 2, distanceY / 2);
            }
        }

        return false;
    }
}
