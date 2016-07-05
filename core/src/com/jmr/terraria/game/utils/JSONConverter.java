package com.jmr.terraria.game.utils;


import com.jmr.terraria.game.item.Item;
import com.jmr.terraria.game.item.ItemManager;
import com.jmr.terraria.game.item.ItemStack;
import com.jmr.terraria.game.item.inventory.Inventory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONConverter {

    public static JSONObject getJSONFromInventory(Inventory inventory) {
        JSONObject jsonInventory = new JSONObject();

        JSONObject inventorySize = new JSONObject();
        inventorySize.put("width", inventory.getWidth());
        inventorySize.put("height", inventory.getHeight());

        jsonInventory.put("inventorySize", inventorySize);

        JSONArray itemList = new JSONArray();
        for(int x = 0; x < inventory.getWidth(); x++) {
            for(int y = 0; y < inventory.getHeight(); y++) {
                ItemStack itemStack = inventory.getItemStack(x, y);
                if(itemStack != null) {
                    JSONObject itemStackInfo = new JSONObject();
                    itemStackInfo.put("x", x);
                    itemStackInfo.put("y", y);
                    itemStackInfo.put("id", itemStack.getItem().getTypeId());
                    itemStackInfo.put("stack", itemStack.getStack());
                    itemList.add(itemStackInfo);
                }
            }
        }

        jsonInventory.put("content", itemList);
        return jsonInventory;
    }

    public static Inventory getInventoryFromJSON(JSONObject jsonInventory) {
        JSONObject inventorySize = (JSONObject) jsonInventory.get("inventorySize");
        int inventoryWidth = Integer.parseInt(inventorySize.get("width").toString());
        int inventoryHeight = Integer.parseInt(inventorySize.get("height").toString());

        Inventory inventory = new Inventory(inventoryWidth, inventoryHeight);

        JSONArray itemList = ((JSONArray) jsonInventory.get("content"));
        for (int i = 0; i < itemList.size(); i++) {
            JSONObject jsonItemStack = (JSONObject) itemList.get(i);
            Item item = ItemManager.getInstance().getItem(Integer.parseInt(jsonItemStack.get("id").toString()));
            int stack = Integer.parseInt(jsonItemStack.get("stack").toString());
            ItemStack itemStack = ItemStack.getItemStack(item, stack);
            int x = Integer.parseInt(jsonItemStack.get("x").toString());
            int y = Integer.parseInt(jsonItemStack.get("y").toString());
            System.out.println(item.getTypeId() + " / " + stack);
            inventory.setItemStack(itemStack, x, y);
        }
        return inventory;
    }
}