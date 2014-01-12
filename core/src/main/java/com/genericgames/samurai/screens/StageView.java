package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

abstract public class StageView {

    protected Stage stage;

    public StageView(){
        this.stage = getStage();
    }

    public StageView(Object data){
        setData(data);
        this.stage = getStage();
    }

    abstract protected Stage getStage();
    abstract protected void update(Object data);
    abstract protected void setData(Object data);

    protected void render(float delta){
        Gdx.input.setInputProcessor(stage);
        stage.act(delta);
        stage.draw();
    }

    protected void setState(WorldRenderer.GameState state){
        WorldRenderer.getRenderer().setState(state);
    }
}
