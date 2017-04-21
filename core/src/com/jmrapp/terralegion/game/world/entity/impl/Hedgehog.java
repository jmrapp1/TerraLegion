package com.jmrapp.terralegion.game.world.entity.impl;

import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.TexturedDrawable;
import com.jmrapp.terralegion.engine.world.entity.BodyType;
import com.jmrapp.terralegion.game.world.entity.FriendlyEntity;

/**
 * Created by root on 11/19/16.
 */
public class Hedgehog extends FriendlyEntity {

    public Hedgehog(float x, float y){
        super(new TexturedDrawable(ResourceManager.getInstance().getTexture("hedgehog")), x, y, BodyType.DYNAMIC, 7f, 10, 7, 3.5f);
     }
}
