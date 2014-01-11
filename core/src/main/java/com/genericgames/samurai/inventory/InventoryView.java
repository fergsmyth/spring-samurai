package com.genericgames.samurai.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.genericgames.samurai.io.Resource;
import com.genericgames.samurai.menu.Menu;

public class InventoryView {

    public static Stage showInventory(Inventory inventory, EventListener backListener){
        Stage inventoryView = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Table table = new Table(new Skin(Gdx.files.internal("uiskin.json")));
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.debug();
        table.add("Inventory").row();
        if(inventory.getItems().isEmpty()){
            table.add("No items in inventory");
        } else {
            for(Item item : inventory.getItems()){
                table.add(createItemButton(item));
            }
        }
        table.add(Menu.createButton("Back", Menu.BUTTON_WIDTH, Menu.BUTTON_HEIGHT, backListener));
        inventoryView.addActor(table);
        return inventoryView;
    }

    private static ImageButton createItemButton(Item item){
        SpriteDrawable drawable = new SpriteDrawable(Resource.getImage(item.getImageName()));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(drawable, drawable, null, null, null, null);
        ImageButton imageButton = new ImageButton(style);
        return imageButton;
    }

}
