package com.jmrapp.terralegion.game.views.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jmrapp.terralegion.engine.views.drawables.ResourceManager;
import com.jmrapp.terralegion.engine.views.drawables.ui.Button;
import com.jmrapp.terralegion.game.item.ItemStack;

/**
 * Created by Jon on 10/5/15.
 */
public class InventoryBox extends Button {

    public static final Texture bg = ResourceManager.getInstance().getTexture("inventoryBox");
    public static final Texture selectedBg = ResourceManager.getInstance().getTexture("selectedInventoryBox");
    private static final BitmapFont font = ResourceManager.getInstance().getFont("smallFont");

    private ItemStack itemStack;
    private boolean selected;

    public InventoryBox(ItemStack itemStack, float x, float y) {
        super(x, y, bg.getWidth(), bg.getHeight());
        this.itemStack = itemStack;
    }

    public void render(SpriteBatch sb) {
        if (selected)
            sb.draw(selectedBg, x, y);
        else
            sb.draw(bg, x, y);

        if (itemStack != null) {
            itemStack.getItem().getIcon().render(sb, x + (bg.getWidth() / 2) - (itemStack.getItem().getIcon().getWidth() / 2), y + (bg.getHeight() / 2) - (itemStack.getItem().getIcon().getHeight() / 2));
            font.draw(sb, String.valueOf(itemStack.getStack()), x + 5, y + bg.getHeight() - 10);
        }

    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean b) {
        selected = b;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}
