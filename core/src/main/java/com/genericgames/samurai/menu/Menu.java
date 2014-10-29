package com.genericgames.samurai.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.io.LoadListener;
import com.genericgames.samurai.io.Resource;
import com.genericgames.samurai.io.SaveListener;
import com.genericgames.samurai.scoreboard.Score;
import com.genericgames.samurai.scoreboard.UpperCaseTextField;
import com.genericgames.samurai.utility.ImageCache;

import java.util.*;

public class Menu {

    public static int BUTTON_HEIGHT = 50;
    public static int BUTTON_WIDTH = 200;
    public static final Skin SKIN = new Skin(Gdx.files.internal("uiskin.json"));

    private static Stage loadMenu;
    private static Stage saveMenu;

    public static Stage createButtonMenu(int width, int height, Map<String, EventListener> buttonInformation){
        return createButtonMenu(width, height, buttonInformation, new Color(1, 1, 1, 1), false);
    }

    public static Stage createButtonMenu(int width, int height, Map<String, EventListener> buttonInformation,
                                         Color buttonColor, boolean disabled){
        Stage stage = new Stage(new FillViewport(width, height));
        float x = getX(width);
        float i = 1;
        int numButtons = buttonInformation.size();
        for (Map.Entry<String, EventListener> entry : buttonInformation.entrySet()){
            stage.addActor(
                    createButton(entry.getKey(), x, i * (height / (numButtons + 1)),
                            entry.getValue(), buttonColor, disabled));
            i++;
        }
        return stage;
    }

    public static Stage createLoadMenu(int width, int height, EventListener backListener){
        loadMenu = new Stage(new ExtendViewport(width, height));
        List list = new List(SKIN);
        list.setItems(getSaveInformation());
        ScrollPane scrollPane = new ScrollPane(list);
        Table table = new Table(SKIN);
        table.setBounds(0, 0, width, height);
        table.debug();
        if (list.getItems().size == 0){
            table.add("No saves to load").row();
        } else {
            table.add("Select save").row();
            table.add(scrollPane).padBottom(BUTTON_HEIGHT).row();
            table.add(createButton("Load", BUTTON_WIDTH, BUTTON_HEIGHT, new LoadListener(list)))
                    .width(BUTTON_WIDTH).height(BUTTON_HEIGHT).padBottom(BUTTON_HEIGHT).row();
        }
        table.add(createButton("Back", BUTTON_WIDTH, BUTTON_HEIGHT, backListener))
                .width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        loadMenu.addActor(table);
        return loadMenu;
    }

    public static Stage createScoreboardView(int width, int height, EventListener backListener){
        Stage scoreboard = new Stage(new ExtendViewport(width, height));
        java.util.List<Score> scores = GameIO.getScoreboard().getScores();
        Table table = new Table(SKIN);
        table.setFillParent(false);
        float tableWidth = width / 2;
        float tableHeight = height / 2;
        float tableX = (width/2)-(tableWidth/2);
        float tableY = (height/2)-(tableHeight/2);
        table.setBounds(tableX, tableY, tableWidth, tableHeight);
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
        scoreboard.addActor(table);
        scoreboard.addActor(createButton("Exit", ((width/2) - (BUTTON_WIDTH/2)), tableY-BUTTON_HEIGHT, backListener));
        return scoreboard;

    }

    public static Stage createEnterPlayerNameView(int width, int height, EventListener confirm){
        Stage scoreboard = new Stage(new ExtendViewport(width, height));
        //List list = new List(new Skin(Gdx.files.internal("uiskin.json")));
        java.util.List<Score> scores = GameIO.getScoreboard().getScores();
        //ScrollPane scrollPane = new ScrollPane(list);
        Table table = new Table(SKIN);
        table.setFillParent(false);
        float tableWidth = width / 2;
        float tableHeight = height / 2;
        float tableX = (width/2)-(tableWidth/2);
        float tableY = (height/2)-(tableHeight/2);
        table.setBounds(tableX, tableY, tableWidth, tableHeight);
        table.add("Enter player name").center().row();
        TextField playerName = new UpperCaseTextField("ABC", SKIN);
        playerName.setMaxLength(3);
        //playerName.setTextFieldFilter(new UpperCaseTextField());
        table.add(playerName).center().width(40).padBottom(BUTTON_HEIGHT).row();
        scoreboard.addActor(table);
        scoreboard.addActor(createButton("Confirm", ((width/2) - (BUTTON_WIDTH/2)), tableY-BUTTON_HEIGHT, confirm));
        return scoreboard;
    }

    public static Stage createSaveMenu(int width, int height, EventListener previousScreenListener){
        if(saveMenu == null){
            saveMenu = new Stage(new ExtendViewport(width, height));
            TextField saveNameField = new TextField("", SKIN);
            Table table = new Table(SKIN);
            table.setBounds(0, 0, width, height);
            table.debug();
            table.add("Enter save name").row();
            table.add(saveNameField).row().pad(BUTTON_HEIGHT);
            table.add(createButton("Save", BUTTON_WIDTH, BUTTON_HEIGHT, new SaveListener(saveNameField, previousScreenListener)))
                    .width(BUTTON_WIDTH).height(BUTTON_HEIGHT).row();
            table.add(createButton("Cancel", BUTTON_WIDTH, BUTTON_HEIGHT, previousScreenListener))
                    .width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
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

    private static TextButton createButton(String buttonText, float x, float y,
                                           EventListener listener, Color color, boolean disabled) {
        TextButton textButton = createButton(buttonText, x, y, listener);
        textButton.setColor(color);
        textButton.setDisabled(disabled);
        return textButton;
    }

    private static TextButton createButton(String buttonText, float x, float y, EventListener listener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = Resource.getHeaderFont();

        //Create button skin:
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(ImageCache.buttonAtlas);
        style.up = buttonSkin.getDrawable("buttonUp2");
        style.down = buttonSkin.getDrawable("buttonDown2");

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

    private static float getX(int screenWidth){
        return (screenWidth / 2) - (BUTTON_WIDTH/2);
    }
}
