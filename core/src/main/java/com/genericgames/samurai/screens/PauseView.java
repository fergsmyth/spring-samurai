package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.genericgames.samurai.menu.Menu;

import java.util.HashMap;
import java.util.Map;

public class PauseView extends StageView {

    public PauseView() {
        super();
    }

    protected Stage getStage(){
        return Menu.createButtonMenu(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), getButtonInfo());
    }

    @Override
    protected void update(Object data) {

    }

    @Override
    protected void setData(Object data) {

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
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    setState(WorldRenderer.GameState.IN_GAME);
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
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    setState(WorldRenderer.GameState.SAVE);
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
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
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
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        };
    }
}
