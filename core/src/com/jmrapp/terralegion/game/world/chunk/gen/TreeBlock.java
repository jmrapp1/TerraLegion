package com.jmrapp.terralegion.game.world.chunk.gen;


import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.game.world.block.BlockType;

public class TreeBlock {

    private BlockType type;
    private Vector2 position;

    public TreeBlock(BlockType type, Vector2 position) {
        this.type = type;
        this.position = position;
    }

    public BlockType getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }
}
