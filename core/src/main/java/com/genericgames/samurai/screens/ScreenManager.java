package com.genericgames.samurai.screens;

import com.badlogic.gdx.Game;

public class ScreenManager {

    private Game game;
    public static ScreenManager manager;

    public ScreenManager(Game game){
        this.game = game;
    }

    public static void initialiseScreenManager(Game game){
        manager = new ScreenManager(game);
        manager.setSplashScreen();
    }

    public void setMainMenu(){
        game.setScreen(new MainMenu(this));
    }

    public void setSplashScreen(){
        game.setScreen(new SplashScreen(this));
    }

    public void setGameScreen(){
        game.setScreen(new GameScreen(this));
    }
}
