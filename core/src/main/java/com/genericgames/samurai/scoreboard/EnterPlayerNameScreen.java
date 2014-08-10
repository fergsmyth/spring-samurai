package com.genericgames.samurai.scoreboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.genericgames.samurai.exception.WTFException;
import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.menu.Menu;
import com.genericgames.samurai.screens.ScreenManager;

public class EnterPlayerNameScreen implements Screen {

    private Score score;
    private Stage enterPlayerNameStage;

    public EnterPlayerNameScreen(Score score){
        this.score = score;
    }

    private EventListener confirmAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    getPlayerName();
                    Scoreboard scoreboard = GameIO.getScoreboard();
                    score.setPlayerName(getPlayerName());
                    scoreboard.addToScoreBoard(score);
                    GameIO.saveScoreboard(scoreboard);
                    ScreenManager.manager.setScoreboardScreen();
                    return true;
                }
                return false;
            }
        };
    }

    private String getPlayerName() {
        for (Actor parent : enterPlayerNameStage.getActors()) {
            if (parent instanceof Table) {
                Table table = (Table) parent;
                for (Actor child : table.getChildren()) {
                    if (child instanceof UpperCaseTextField) {
                        UpperCaseTextField nameField = (UpperCaseTextField) child;
                        return nameField.getText();
                    }
                }
            }
        }
        throw new WTFException();
    }

    @Override
    public void render(float delta) {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        enterPlayerNameStage.act(delta);
        enterPlayerNameStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        enterPlayerNameStage = Menu.createEnterPlayerNameView(width, height, confirmAction());
        Gdx.input.setInputProcessor(enterPlayerNameStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
