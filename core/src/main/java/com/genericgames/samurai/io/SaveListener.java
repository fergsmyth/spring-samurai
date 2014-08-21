package com.genericgames.samurai.io;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.genericgames.samurai.screens.WorldRenderer;

public class SaveListener implements EventListener {

    private TextField field;
    private EventListener previousScreenListener;

    public SaveListener(TextField field, EventListener previousScreenListener){
        this.field = field;
        this.previousScreenListener = previousScreenListener;
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof ChangeListener.ChangeEvent){
            GameIO.saveGame(WorldRenderer.getRenderer().getWorld().getCurrentLevel(), field.getText());
            previousScreenListener.handle(event);
            return true;
        }
        return false;
    }
}
