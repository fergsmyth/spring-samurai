package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.genericgames.samurai.io.SaveGameHelper;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.screens.GameScreen;
import com.genericgames.samurai.screens.ScreenManager;

import java.util.Collection;
import java.util.Map;

public class WorldRenderer {


    private SamuraiWorld samuraiWorld;
    private OrthographicCamera camera;
    private GameScreen gameScreen;

    public static final int SCALE_FACTOR = 6;
    private static final float CAMERA_WIDTH = 20f;
    private static final float CAMERA_HEIGHT = 20f;
    private static final float tileSize = 1f;

    private int screenWidthInPixels = 800;
    private int screenHeightInPixels = 480;
    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

	// For rendering:
    private Stage stage;
    private SpriteBatch spriteBatch;
    private SpriteBatch hudBatch;
    private ShapeRenderer shapeRenderer;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer mapRenderer;
    private BitmapFont font;

    private GameState state;

    private enum GameState {
        CONVERSATION,
        IN_GAME,
        PAUSED
    }
	//For physics and collision detection:
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private static WorldRenderer renderer = new WorldRenderer();

    public void defaultState(){
        state = GameState.IN_GAME;
    }

    public static WorldRenderer getRenderer(){
        return renderer;
    }

    public void setGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public WorldRenderer(){
        hudBatch = new SpriteBatch();
        mapLoader = new TmxMapLoader();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = ResourceHelper.getFont();
        state = GameState.IN_GAME;
        loadTextures();
    }

    public void setSamuraiWorld(SamuraiWorld samuraiWorld){
        this.samuraiWorld = samuraiWorld;
        camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
        camera.setToOrtho(false, 20, 20);
        camera.update();

        TiledMap map = mapLoader.load(samuraiWorld.getCurrentLevelFile());
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/32f);
    }

    public void setSize (int w, int h) {
        this.screenWidthInPixels = w;
        this.screenHeightInPixels = h;
        ppuX = (float)screenWidthInPixels/CAMERA_WIDTH;
        ppuY = (float)screenHeightInPixels/CAMERA_HEIGHT;
    }

    private void loadTextures() {
		ImageCache.load();
    }

    public void setTiledMap(String file){
        mapRenderer.setMap(mapLoader.load(file));
    }

    public void render(float delta) {
        switch (state){
            case CONVERSATION :
                break;
            case IN_GAME :
                mapRenderer.setView(camera);
                mapRenderer.render();

                PhysicalWorldFactory.checkForCollisions(samuraiWorld);

                camera.position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
                camera.update();
                spriteBatch.setProjectionMatrix(camera.combined);
                spriteBatch.enableBlending();
                spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                spriteBatch.begin();
                drawPlayerCharacter();
                drawEnemies();
                spriteBatch.end();

                drawHUD();

                if(DebugMode.isDebugEnabled()){
                    drawDebugBoundingBoxes();
                }
                break;
            case PAUSED :
                stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                Gdx.input.setInputProcessor(stage);
                addButtons();
                stage.act(delta);
                stage.draw();
                break;
        }
    }

    public void pause(){
        state = GameState.PAUSED;
    }

    private void addButtons() {
        createButton("Resume", getTOP(), resumeAction());
        createButton("Save Game", getMIDDLE(), saveAction());
        createButton("Main Menu", getMIDDLE2(), mainMenuAction());
        createButton("Exit Game", getBOTTOM(), quitAction());

    }

    private EventListener resumeAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    state = GameState.IN_GAME;
                    gameScreen.setPlayerController();
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener quitAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener saveAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    SaveGameHelper.saveGame(samuraiWorld.getCurrentLevel());
                    return true;
                }
                return false;
            }
        };
    }

    private EventListener mainMenuAction(){
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event instanceof InputEvent && ((InputEvent)event).getType() == InputEvent.Type.touchDown){
                    ScreenManager.manager.setMainMenu();

                    return true;
                }
                return false;
            }
        };
    }

    private TextButton createButton(String buttonText, int position, EventListener listener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = ResourceHelper.getHeaderFont();
        TextButton button = new TextButton(buttonText, style);
        button.setWidth(400);
        button.setHeight(100);
        button.setPosition(getX(), position);
        button.addListener(listener);
        stage.addActor(button);
        return button;
    }

    private int getX(){
        return (Gdx.graphics.getWidth() / 2) - Gdx.graphics.getWidth() / SCALE_FACTOR;
    }

    private int getTOP(){
        return Gdx.graphics.getHeight() / 2 + (Gdx.graphics.getHeight() / SCALE_FACTOR);
    }

    private int getMIDDLE(){
        return Gdx.graphics.getHeight() / 2;
    }

    private int getMIDDLE2(){
        return Gdx.graphics.getHeight() / 2 - (Gdx.graphics.getHeight() / (SCALE_FACTOR + 1));
    }

    private int getBOTTOM(){
        return Gdx.graphics.getHeight() / 2 - (Gdx.graphics.getHeight() / SCALE_FACTOR);
    }

    private void drawHUD() {
        //Setup camera matrices for using ShapeRenderer:
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(spriteBatch.getTransformMatrix());
        shapeRenderer.translate(samuraiWorld.getPlayerCharacter().getX()-(CAMERA_WIDTH/2), samuraiWorld.getPlayerCharacter().getY()-(CAMERA_HEIGHT/2), 0);

        //Setup camera matrices for using SpriteBatch:
        Matrix4 uiMatrix = camera.combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        hudBatch.setProjectionMatrix(uiMatrix);

        drawHealthBar();
    }

    private void drawHealthBar() {
        float scalingFactor = 0.025f;
        float healthBarPosX = 0;
        float healthBarPosY = (96f*CAMERA_HEIGHT)/100f;

        //Draw heart:
        hudBatch.begin();
        hudBatch.draw(ImageCache.heartTexture, healthBarPosX, healthBarPosY, 20*scalingFactor, 20*scalingFactor);
        hudBatch.end();

        //Draw health Bar:
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        float heartIndent = (2 * CAMERA_WIDTH) / 100f;
        shapeRenderer.rect(healthBarPosX+heartIndent, healthBarPosY,
                samuraiWorld.getPlayerCharacter().getHealth()*scalingFactor, 20*scalingFactor);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.7f, 0, 0, 1);
        shapeRenderer.rect(healthBarPosX+heartIndent, healthBarPosY,
                samuraiWorld.getPlayerCharacter().getMaxHealth()*scalingFactor, 20*scalingFactor);
        shapeRenderer.end();
    }

    private void drawDebugBoundingBoxes() {
        debugRenderer.render(samuraiWorld.getPhysicalWorld(), camera.combined);
    }

	private void drawPlayerCharacter() {
		PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
        Map<State, Animation> animationMap = ImageCache.getAnimations().get(playerCharacter.getClass());
		TextureRegion texture = animationMap.get(playerCharacter.getState()).getKeyFrame(playerCharacter.getStateTime(),
                playerCharacter.getState().isLoopingState());
        float playerX = playerCharacter.getX()-(tileSize/2);
        float playerY = playerCharacter.getY()-(tileSize/2);
        if(DebugMode.isDebugEnabled()){
            Gdx.app.log("GameScreen", "X : " + playerX + "Y : " + playerY);
        }
		spriteBatch.draw(texture, playerX, playerY,
			  0.5f,  0.5f, tileSize, tileSize, 1, 1, playerCharacter.getRotationInDegrees());

	}

    private void drawEnemies(){
        for(Enemy enemy : samuraiWorld.getEnemies()){
            Map<State, Animation> animationMap = ImageCache.getAnimations().get(enemy.getClass());
            TextureRegion texture = animationMap.get(enemy.getState()).getKeyFrame(enemy.getStateTime(),
                    enemy.getState().isLoopingState());

            spriteBatch.draw(texture, enemy.getX()-(tileSize/2), enemy.getY()-(tileSize/2),
                    0.5f,  0.5f, tileSize, tileSize, 1, 1, enemy.getRotationInDegrees());
        }
    }

    private void drawWorldObject(Collection<? extends WorldObject> worldObjects, TextureRegion texture) {
        for(WorldObject worldObject : worldObjects){
            spriteBatch.draw(texture, worldObject.getX()-(tileSize/2), worldObject.getY()-(tileSize/2), tileSize, tileSize);
        }
    }

    private void drawWorldObject(Collection<? extends WorldObject> worldObjects, Texture texture) {
        for(WorldObject worldObject : worldObjects){
            spriteBatch.draw(texture, worldObject.getX()-(tileSize/2), worldObject.getY()-(tileSize/2), tileSize, tileSize);
        }
    }
}

