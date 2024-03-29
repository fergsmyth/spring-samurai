package com.genericgames.samurai.io;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.WorldFactory;
import com.genericgames.samurai.screens.ScreenManager;

public class LoadListener implements EventListener {

    private List<String> list;

    public LoadListener(List list){
        this.list = list;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof ChangeListener.ChangeEvent){
            String levelName = list.getSelection().first();
            Level level = GameIO.loadGame(levelName);
            if(level != null){
                ScreenManager.manager.setGameScreen(WorldFactory.loadSamuraiWorld(level.getLevelFile(), level.getPlayerCharacter().getX(), level.getPlayerCharacter().getY()));
            }
            return true;
        }
        return false;
    }
}
