package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.genericgames.samurai.GameState;
import com.genericgames.samurai.view.View;

abstract public class StageView implements View {

    protected Stage stage;

    public StageView(){
        this.stage = getStage();
    }

    public StageView(Object data){
        setData(data);
        this.stage = getStage();
    }

    public void render(float delta){
        Gdx.input.setInputProcessor(stage);
        stage.act(delta);
        stage.draw();
    }

    public void setState(GameState state){
        WorldRenderer.getRenderer().setState(state);
    }
}
