package com.jmrapp.terralegion.game.world.block.impl;

import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.world.block.Block;
import com.jmrapp.terralegion.game.world.block.BlockType;

/**
 * Generates around the map for a source of seeds
 *
 * Created by root on 8/5/16.
 */
public class CoverGrassBlock extends Block{

    public CoverGrassBlock(){
        super(BlockType.COVER_GRASS, ResourceManager.getInstance().getDrawable("covergrass"), .01f, false, false, .1f, .1f);

    }

}