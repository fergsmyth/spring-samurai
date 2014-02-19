package com.genericgames.samurai.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.genericgames.samurai.GameState;

public interface View {

    Stage getStage();
    void update(Object data);
    void setData(Object data);
    void render(float delta);
    void setState(GameState state);
}
