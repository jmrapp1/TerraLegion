package com.jmrapp.terralegion.game.world.chunk.gen;


import com.badlogic.gdx.math.Vector2;
import com.jmrapp.terralegion.game.world.block.BlockType;

import java.util.ArrayList;
import java.util.Random;

public class TreeGenerator {

    public static ArrayList<TreeBlock> generateTree(int x, int y) {
        ArrayList<TreeBlock> blocks = new ArrayList<TreeBlock>();
        int randomNum1 = getRandomNumber(1, 3);
        int leavesLength;
        int stemLength;
        switch(randomNum1) {
            case 1:
                stemLength = 3;
                leavesLength = getRandomNumber(1, 2);
                break;
            case 2:
                stemLength = 4;
                leavesLength = getRandomNumber(1, 3);
                break;
            case 3:
                stemLength = 5;
                leavesLength = getRandomNumber(1, 4);
                break;
            default:
                stemLength = 3;
                leavesLength = getRandomNumber(1, 2);
                break;
        }
        // add stem
        for(int i = 0; i < stemLength; i++) {
            blocks.add(new TreeBlock(BlockType.WOOD, new Vector2(x, y+i)));
        }
        // add leaves
        for(int i = 1; i <= leavesLength; i++) {
            blocks.add(new TreeBlock(BlockType.LEAVES, new Vector2(x-1, y+stemLength-i)));
            blocks.add(new TreeBlock(BlockType.LEAVES, new Vector2(x+1, y+stemLength-i)));
        }
        blocks.add(new TreeBlock(BlockType.LEAVES, new Vector2(x, y+stemLength)));
        return blocks;
    }

    private static int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
