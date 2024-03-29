package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.genericgames.samurai.GameState;
import com.genericgames.samurai.menu.Menu;

import java.util.HashMap;
import java.util.Map;

public class PauseView extends StageView {

    public PauseView(int width, int height) {
        super(width, height);
    }

    public Stage getStage(){
        return Menu.createButtonMenu(width, height, getButtonInfo());
    }

    public void initialise(){

    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void setData(Object data) {

    }

    private Map<String, EventListener> getButtonInfo() {
        Map<String, EventListener> buttons = new HashMap<String, EventListener>();
        buttons.put("Resume", resumeAction());
        buttons.put("Save Game", saveAction());
        buttons.put("Main Menu", mainMenuAction());
        buttons.put("Exit Game", quitAction());
        return buttons;
    }

    private EventListener resumeAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    setState(GameState.IN_GAME);
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener saveAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    setState(GameState.SAVE);
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener mainMenuAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    ScreenManager.manager.setMainMenu();
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener quitAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        };
    }
}
