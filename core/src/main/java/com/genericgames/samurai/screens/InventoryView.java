package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.genericgames.samurai.GameState;
import com.genericgames.samurai.inventory.Inventory;
import com.genericgames.samurai.inventory.Item;
import com.genericgames.samurai.io.Resource;
import com.genericgames.samurai.menu.Menu;

public class InventoryView extends StageView {

    private Inventory inventory;

    public InventoryView(Inventory inventory){
        super(inventory);
    }

    @Override
    public Stage getStage() {
        return showInventory();
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void setData(Object data) {
        inventory = (Inventory) data;
    }

    public Stage showInventory(){
        Stage inventoryView = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
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
        table.row();
        table.add(Menu.createButton("Back", Menu.BUTTON_WIDTH, Menu.BUTTON_HEIGHT, resumeAction()));
        inventoryView.addActor(table);
        return inventoryView;
    }

    private static ImageButton createItemButton(Item item){
        SpriteDrawable drawable = new SpriteDrawable(Resource.getImage(item.getImageName()));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle(drawable, drawable, null, null, null, null);
        ImageButton imageButton = new ImageButton(style);
        return imageButton;
    }

    private EventListener resumeAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    setState(GameState.IN_GAME);
                    return true;
                }
                return false;
            }
        };
    }
}
