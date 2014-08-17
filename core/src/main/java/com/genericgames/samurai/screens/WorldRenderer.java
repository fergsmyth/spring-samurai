package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.genericgames.samurai.GameState;
import com.genericgames.samurai.model.SamuraiWorld;

public class WorldRenderer {

    private static WorldRenderer renderer = new WorldRenderer();


    private static final float CAMERA_HEIGHT = 20f;
    private static final float CAMERA_WIDTH = 26f;
    private static int FRAME = 0;

    public int height = Gdx.graphics.getHeight();
    public int width = Gdx.graphics.getHeight();

    private SamuraiWorld samuraiWorld;
    private GameScreen gameScreen;
    private GameState state;
    private StageView view;


    public void render(float delta) {
        view.render(delta);
        FRAME++;
    }

    public void resetSize(){
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
    }

    void setState(GameState nextState){
        resetSize();
//        height = view.getStage().getHeight();
//        width = view.getStage().getWidth();
        state = nextState;
        switch (state){
            case IN_GAME :
                gameScreen.setPlayerController();
                view = new GameView(initialiseCamera(), samuraiWorld.getCurrentLevelFile());
                break;
            case PAUSED :
                view = new PauseView(width, height);
                break;
            case SAVE :
                view = new SaveView(view.getWidth(), view.getHeight());
                break;
        }
    }

    public GameState getState() {
        return state;
    }

    public StageView getView(){
        return view;
    }

    public void setView(StageView view) {
        this.view = view;
    }

    private OrthographicCamera initialiseCamera(){
        OrthographicCamera camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
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

    public void dialogue(){
        samuraiWorld.getCurrentLevel().getPhysicsWorld();
        if(view instanceof GameView){
            ((GameView) view).setInConversation();
        }
    }

    public void nextPhrase(){
        samuraiWorld.getCurrentLevel().getPhysicsWorld();
        if(view instanceof GameView){
            ((GameView) view).nextPhrase();
        }
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

    public static float getCameraHeight() {
        return CAMERA_HEIGHT;
    }

    public static float getCameraWidth() {
        return CAMERA_WIDTH;
    }

    /**
     * @return the distance from the centre to the corner of the screen.
     */
    public static float getScreenSize(){
        return (float) Math.sqrt(WorldRenderer.getCameraWidth()*WorldRenderer.getCameraWidth() +
                WorldRenderer.getCameraHeight()*WorldRenderer.getCameraHeight());
    }

    public static int getFrame() {
        return FRAME;
    }
}

