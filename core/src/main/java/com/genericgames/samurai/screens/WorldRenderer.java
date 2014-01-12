package com.genericgames.samurai.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.genericgames.samurai.model.SamuraiWorld;

public class WorldRenderer {

    private static WorldRenderer renderer = new WorldRenderer();
    private static final float CAMERA_HEIGHT = 20f;
    private static final float CAMERA_WIDTH = 20f;

    private SamuraiWorld samuraiWorld;
    private GameScreen gameScreen;
    private GameState state;
    private StageView view;

    enum GameState {
        CONVERSATION,
        IN_GAME,
        INVENTORY,
        PAUSED,
        SAVE
    }

    public void render(float delta) {
        view.render(delta);
    }

    void setState(GameState nextState){
        state = nextState;
        switch (state){
            case CONVERSATION :
                break;
            case IN_GAME :
                gameScreen.setPlayerController();
                view = new GameView(initialiseCamera(), samuraiWorld.getCurrentLevelFile());
                break;
            case INVENTORY :
                view = new InventoryView(samuraiWorld.getPlayerCharacter().getInventory());
                break;
            case PAUSED :
                view = new PauseView();
                break;
            case SAVE :
                view = new SaveView();
                break;
        }
    }

    private OrthographicCamera initialiseCamera(){
        OrthographicCamera camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
        camera.setToOrtho(false, 20, 20);
        camera.update();
        return camera;
    }

    public void inGame(){
        setState(GameState.IN_GAME);
    }

    public void pause(){
        setState(GameState.PAUSED);
    }

    public void inventory(){
        setState(GameState.INVENTORY);
    }

    public static WorldRenderer getRenderer(){
        return renderer;
    }

    public SamuraiWorld getWorld(){
        return samuraiWorld;
    }
    public void setGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public void setSamuraiWorld(SamuraiWorld samuraiWorld){
        this.samuraiWorld = samuraiWorld;
    }

    public void setTiledMap(String file){
        view.update(file);
    }

}

