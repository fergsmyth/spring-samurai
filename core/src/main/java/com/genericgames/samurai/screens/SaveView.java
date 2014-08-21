package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.genericgames.samurai.GameState;
import com.genericgames.samurai.menu.Menu;

public class SaveView extends StageView {

    public SaveView(int width, int height) {
        super(width, height);
    }

    @Override
    public Stage getStage() {
        return Menu.createSaveMenu(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), backAction());
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void setData(Object data) {

    }

    private EventListener backAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof ChangeListener.ChangeEvent){
                    setState(GameState.PAUSED);
                    return true;
                }
                return false;
            }
        };
    }
}
