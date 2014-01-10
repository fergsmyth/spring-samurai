package com.genericgames.samurai.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.io.LoadListener;
import com.genericgames.samurai.io.Resource;
import com.genericgames.samurai.io.SaveListener;

import java.util.ArrayList;
import java.util.Map;

public class Menu {

    public static final int BUTTON_HEIGHT = 100;
    public static final int BUTTON_WIDTH = 400;

    private static Stage loadMenu;
    private static Stage saveMenu;

    public static Stage createButtonMenu(int width, int height, Map<String, EventListener> buttonInformation){
        Stage stage = new Stage(width, height, true);
        float scaleFactor = buttonInformation.entrySet().size() * 2 + 1;
        float x = getX(scaleFactor);
        float i = 2;
        for (Map.Entry<String, EventListener> entry : buttonInformation.entrySet()){
            stage.addActor(createButton(entry.getKey(), x, getY(i), entry.getValue()));
            i++;
        }
        return stage;
    }

    public static Stage createLoadMenu(int width, int height, EventListener backListener){
        loadMenu = new Stage(width, height, true);
        List list = new List(getSaveInformation(), new Skin(Gdx.files.internal("uiskin.json")));
        ScrollPane scrollPane = new ScrollPane(list);
        Table table = new Table(new Skin(Gdx.files.internal("uiskin.json")));
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.debug();
        if (list.getItems().length == 0){
            table.add("No saves to load").row();
        } else {
            table.add("Select save").row();
            table.add(scrollPane).row();
            table.add(createButton("Load", BUTTON_WIDTH, BUTTON_HEIGHT, new LoadListener(list)));
        }
        table.add(createButton("Back", BUTTON_WIDTH, BUTTON_HEIGHT, backListener));
        loadMenu.addActor(table);
        return loadMenu;
    }

    public static Stage createSaveMenu(int width, int height, EventListener previousScreenListener){
        if(saveMenu == null){
            saveMenu = new Stage(width, height, true);
            TextField saveNameField = new TextField("", new Skin(Gdx.files.internal("uiskin.json")));
            Table table = new Table(new Skin(Gdx.files.internal("uiskin.json")));
            table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            table.debug();
            table.add("Enter save name").row();
            table.add(saveNameField).row();
            table.add(createButton("Save", BUTTON_WIDTH, BUTTON_HEIGHT, new SaveListener(saveNameField, previousScreenListener)));
            table.add(createButton("Back", BUTTON_WIDTH, BUTTON_HEIGHT, previousScreenListener));
            saveMenu.addActor(table);
        }
        return saveMenu;
    }

    public static Object[] getSaveInformation(){
        ArrayList<String> saveInformation = new ArrayList<String>();
        for (FileHandle file : GameIO.getSaves()){
            if(file.name().contains(Resource.SAVE_EXTENSION)){
                saveInformation.add(file.name());
            }
        }
        return saveInformation.toArray();
    }

    private static TextButton createButton(String buttonText, float x, float y, EventListener listener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = Resource.getHeaderFont();
        TextButton button = new TextButton(buttonText, style);
        button.setWidth(BUTTON_WIDTH);
        button.setHeight(BUTTON_HEIGHT);
        button.setPosition(x, y);
        button.addListener(listener);
        button.debug();
        return button;
    }

    private static float getY(float scaleFactor){
        return BUTTON_HEIGHT * scaleFactor;
    }

    private static float getX(float scaleFactor){
        return (Gdx.graphics.getWidth() / 2) - Gdx.graphics.getWidth() / scaleFactor;
    }
}
