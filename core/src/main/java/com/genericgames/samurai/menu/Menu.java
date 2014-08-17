package com.genericgames.samurai.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.io.LoadListener;
import com.genericgames.samurai.io.Resource;
import com.genericgames.samurai.io.SaveListener;
import com.genericgames.samurai.scoreboard.Score;
import com.genericgames.samurai.scoreboard.UpperCaseTextField;
import com.genericgames.samurai.utility.ImageCache;
import sun.font.TrueTypeFont;

import java.util.*;

public class Menu {

    public static int BUTTON_HEIGHT = 100;
    public static int BUTTON_WIDTH = 400;
    public static final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

    private static Stage loadMenu;
    private static Stage saveMenu;

    public static Stage createButtonMenu(int width, int height, Map<String, EventListener> buttonInformation){
        resetButtonSizes(width, height);
        Stage stage = new Stage(new ExtendViewport(width, height));
        float scaleFactor = buttonInformation.entrySet().size() * 2 + 1;
        float x = getX(scaleFactor, width);
        float i = 2;
        for (Map.Entry<String, EventListener> entry : buttonInformation.entrySet()){
            stage.addActor(createButton(entry.getKey(), x, getY(i, height), entry.getValue()));
            i++;
        }
        return stage;
    }

    private static void resetButtonSizes(int width, int height) {
        BUTTON_WIDTH = Math.round(width*0.278f);
        BUTTON_HEIGHT = Math.round(height*0.124f);
    }

    public static Stage createLoadMenu(int width, int height, EventListener backListener){
        resetButtonSizes(width, height);
        loadMenu = new Stage(new ExtendViewport(width, height));
        List list = new List(SKIN);
        list.setItems(getSaveInformation());
        ScrollPane scrollPane = new ScrollPane(list);
        Table table = new Table(SKIN);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.debug();
        if (list.getItems().size == 0){
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

    public static Stage createScoreboardView(int width, int height, EventListener backListener){
        resetButtonSizes(width, height);
        Stage scoreboard = new Stage(new ExtendViewport(width, height));
        java.util.List<Score> scores = GameIO.getScoreboard().getScores();
        Table table = new Table(SKIN);
        table.setFillParent(true);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.debug();
        table.add("Leader board").row();
        table.add("Player").padLeft(10).width(100);
        table.add("Round").padLeft(10).width(100);
        table.add("Score").padLeft(10).width(100);
        table.add("Time").padLeft(10).width(100).row();
        for (Score score : scores){
          table.add(score.getPlayerName()).padLeft(10).width(100);
          table.add(Integer.toString(score.getLevelNumber())).padLeft(10).width(100);
          table.add(Integer.toString(score.getScore())).padLeft(10).width(100);
          table.add(score.getTimeTakenStringFormat()).padLeft(10).width(100).row();
        }
        table.add(createButton("Exit", BUTTON_WIDTH, BUTTON_HEIGHT, backListener));
        scoreboard.addActor(table);
        return scoreboard;

    }

    public static Stage createEnterPlayerNameView(int width, int height, EventListener confirm){
        resetButtonSizes(width, height);
        Stage scoreboard = new Stage(new ExtendViewport(width, height));
        //List list = new List(new Skin(Gdx.files.internal("uiskin.json")));
        java.util.List<Score> scores = GameIO.getScoreboard().getScores();
        //ScrollPane scrollPane = new ScrollPane(list);
        Table table = new Table(SKIN);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add("Enter player name").padLeft(10).width(110);
        TextField playerName = new UpperCaseTextField("ABC", SKIN);
        playerName.setMaxLength(3);
        //playerName.setTextFieldFilter(new UpperCaseTextField());
        table.add(playerName).padLeft(10).width(40).row();
        table.add(createButton("Confirm", BUTTON_WIDTH, BUTTON_HEIGHT, confirm));
        //table.add(createButton("Cancel", BUTTON_WIDTH, BUTTON_HEIGHT, cancel));
        scoreboard.addActor(table);
        return scoreboard;
    }

    public static Stage createSaveMenu(int width, int height, EventListener previousScreenListener){
        if(saveMenu == null){
            resetButtonSizes(width, height);
            saveMenu = new Stage(new ExtendViewport(width, height));
            TextField saveNameField = new TextField("", SKIN);
            Table table = new Table(SKIN);
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

    private static Object[] getSaveInformation(){
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

        //Create button skin:
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(ImageCache.buttonAtlas);
        style.up = buttonSkin.getDrawable("buttonUp");
        style.down = buttonSkin.getDrawable("buttonDown");

        TextButton button = new TextButton(buttonText, style);
        button.setWidth(BUTTON_WIDTH);
        button.setHeight(BUTTON_HEIGHT);
        button.setPosition(x, y);
        button.addListener(listener);
        button.debug();
        return button;
    }

    private static float getY(float scaleFactor, int screenHeight){
        return BUTTON_HEIGHT * scaleFactor;
    }

    private static float getX(float scaleFactor, int screenWidth){
        return (screenWidth / 2) - (screenWidth / scaleFactor);
    }
}
