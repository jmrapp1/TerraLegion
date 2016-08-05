package com.jmrapp.terralegion.game.world.entity;

import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.game.world.block.BlockType;
import com.jmrapp.terralegion.game.item.ItemManager;
import com.jmrapp.terralegion.game.item.ItemType;

import java.util.HashMap;

/**
 * Created by Jon on 9/30/15.
 */
public class DropManager {

    private static final DropManager instance = new DropManager();
    private final HashMap<Integer, Drop> drops = new HashMap<Integer, Drop>();

    private DropManager() {
        drops.put(BlockType.DIRT.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.DIRT), ResourceManager.getInstance().getDrawable("dirtDrop")));
        drops.put(BlockType.GRASS.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.GRASS), ResourceManager.getInstance().getDrawable("grassDrop")));
        drops.put(BlockType.STONE.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.STONE), ResourceManager.getInstance().getDrawable("stoneDrop")));
        drops.put(BlockType.TORCH.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.TORCH), ResourceManager.getInstance().getDrawable("torchDrop")));
        drops.put(BlockType.DIAMOND.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.DIAMOND), ResourceManager.getInstance().getDrawable("diamondDrop")));
        drops.put(BlockType.COAL.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.COAL), ResourceManager.getInstance().getDrawable("coalDrop")));
        drops.put(BlockType.WOOD.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.STICK), ResourceManager.getInstance().getDrawable("stickDrop")));
        drops.put(BlockType.LEAVES.getId(), new Drop(ItemManager.getInstance().getItem(BlockType.LEAVES), ResourceManager.getInstance().getDrawable("leavesDrop")));
        drops.put(ItemType.WOODEN_PICKAXE.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.WOODEN_PICKAXE), ResourceManager.getInstance().getDrawable("woodenPickaxe")));
        drops.put(ItemType.SWORD.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.SWORD), ResourceManager.getInstance().getDrawable("sword")));
        drops.put(ItemType.STICK.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.STICK), ResourceManager.getInstance().getDrawable("stickDrop")));
        drops.put(BlockType.COVER_GRASS.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.SEED), ResourceManager.getInstance().getDrawable("seed")));
        drops.put(BlockType.CROP_BLOCK_0.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.SEED), ResourceManager.getInstance().getDrawable("seed")));
        drops.put(BlockType.CROP_BLOCK_1.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.SEED), ResourceManager.getInstance().getDrawable("seed")));
        drops.put(BlockType.CROP_BLOCK_2.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.SEED), ResourceManager.getInstance().getDrawable("seed")));
        drops.put(BlockType.CROP_BLOCK_3.getId(), new Drop(ItemManager.getInstance().getItem(ItemType.SEED), ResourceManager.getInstance().getDrawable("seed")));
    }

    public Drop getDrop(BlockType type, int stackCount, float x, float y) {
        return drops.get(type.getId()).getInstance(type.getId(), stackCount, x, y);
    }

    public Drop getDrop(ItemType type, int stackCount, float x, float y) {
        return drops.get(type.getId()).getInstance(type.getId(), stackCount, x, y);
    }

    public Drop getDrop(int id, int stackCount, float x, float y) {
        return drops.get(id).getInstance(id, stackCount, x, y);
    }

    public static DropManager getInstance() {
        return instance;
    }

}
