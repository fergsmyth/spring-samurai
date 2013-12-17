package com.genericgames.samurai.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.genericgames.samurai.model.SamuraiWorld;

public class ScreenManager {

    public static ScreenManager manager;

    private Game game;
    private Screen currentScreen;

    public ScreenManager(Game game){
        this.game = game;
    }

    public static void initialiseScreenManager(Game game){
        manager = new ScreenManager(game);
        manager.setSplashScreen();
    }

    public void setMainMenu(){
        currentScreen = new MainMenu(this);
        game.setScreen(currentScreen);
    }

    public void setSplashScreen(){
        currentScreen = new SplashScreen();
        game.setScreen(currentScreen);
    }

    public void setGameScreen(SamuraiWorld samuraiWorld){
        currentScreen = new GameScreen(samuraiWorld);
        game.setScreen(currentScreen);
    }
}
