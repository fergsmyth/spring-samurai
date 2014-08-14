package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class DialogueView extends StageView {


    public DialogueView(int width, int height) {
        super(width, height);
    }

    @Override
    public Stage getStage() {
        return new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void setData(Object data) {

    }

}
