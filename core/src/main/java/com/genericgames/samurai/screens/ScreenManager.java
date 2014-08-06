package com.genericgames.samurai.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.WorldFactory;
import com.genericgames.samurai.scoreboard.ScoreboardScreen;

public class ScreenManager {

    public static ScreenManager manager;

    private Game game;
    private Screen currentScreen;

    public ScreenManager(Game game){
        this.game = game;
    }

    public static void initialiseScreenManager(Game game){
        manager = new ScreenManager(game);
        //manager.setGameScreen(WorldFactory.createSamuraiWorld("map/Level1.tmx"));
        manager.setSplashScreen();
    }

    public void setMainMenu(){
        currentScreen = new MainMenu(this);
        game.setScreen(currentScreen);
    }

    public void setLoadMenu(){
        currentScreen = new LoadMenu(this);
        game.setScreen(currentScreen);
    }

    public void setSplashScreen(){
        currentScreen = new SplashScreen();
        game.setScreen(currentScreen);
    }

    public void setArenaMode(){
        SamuraiWorld world = WorldFactory.createSamuraiWorld("map/ArenaLevel.tmx");
        currentScreen = new GameScreen(world);
        game.setScreen(currentScreen);
    }

    public void setGameScreen(SamuraiWorld samuraiWorld){
        currentScreen = new GameScreen(samuraiWorld);
        game.setScreen(currentScreen);
    }

    public void setScoreboardScreen(){
        currentScreen = new ScoreboardScreen(this);
        game.setScreen(currentScreen);
    }
}
