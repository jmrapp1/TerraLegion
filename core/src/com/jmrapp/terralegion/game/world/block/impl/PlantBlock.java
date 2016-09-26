package com.jmrapp.terralegion.game.world.block.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.engine.views.drawables.Drawable;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.world.block.Block;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;
import com.jmrapp.terralegion.game.world.chunk.ChunkManager;

/**
 * Plant blocks are automatically destroyed if the block under them no longer exists
 *
 * Created by jordanb84 on 8/5/16.
 */
public class PlantBlock extends Block{

    private ChunkManager chunkManager;

    public PlantBlock(BlockType type, Drawable drawable, float lightBlockingAmount, boolean collides, boolean transparent, float initHealth, float resistance){
        super(type, drawable, lightBlockingAmount, collides, transparent, initHealth, resistance);
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch sb, float x, float y, float lightValue){
        super.render(camera, sb, x, y, lightValue);

        Vector2 tilePosition = new Vector2(chunkManager.pixelToTilePosition(x), chunkManager.pixelToTilePosition(y));
        Vector2 belowTilePosition = new Vector2(tilePosition.x, tilePosition.y - 1);

        if(chunkManager.getBlockFromTilePos((int) belowTilePosition.x, (int) belowTilePosition.y) == BlockType.AIR){
            chunkManager.setBlock(BlockType.AIR, (int) tilePosition.x, (int) tilePosition.y, true);
        }
    }

    @Override
    public void onPlace(ChunkManager chunkManager, Chunk chunk, int chunkTileX, int chunkTileY) {
        super.onPlace(chunkManager, chunk, chunkTileX, chunkTileY);

        this.chunkManager = chunkManager;
    }

}