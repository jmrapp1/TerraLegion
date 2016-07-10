package com.jmrapp.terralegion.game.world.chunk.gen;

import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.world.chunk.Chunk;

import java.util.Random;

public class TreeGenerator {

    public static void generateTree(Chunk chunk, int x, int y) {
        int randomNum1 = getRandomNumber(1, 4);
        int leavesLength;
        int stemLength;
        switch(randomNum1) {
            case 2:
                stemLength = 4;
                leavesLength = getRandomNumber(1, 3);
                break;
            case 3:
                stemLength = 5;
                leavesLength = getRandomNumber(1, 4);
                break;
            case 4:
                stemLength = 6;
                leavesLength = getRandomNumber(1, 5);
                break;
            default: // (case 1)
                stemLength = 3;
                leavesLength = getRandomNumber(1, 2);
                break;
        }
        // add stem
        for(int i = 0; i < stemLength; i++) {
            chunk.setBlock(BlockType.WOOD, x, y+i, false);
        }
        // add leaves
        for(int i = 1; i <= leavesLength; i++) {
            chunk.setBlock(BlockType.LEAVES, x-1, y+stemLength-i, false);
            chunk.setBlock(BlockType.LEAVES, x+1, y+stemLength-i, false);
        }
        chunk.setBlock(BlockType.LEAVES, x, y+stemLength, false);
    }

    private static int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
