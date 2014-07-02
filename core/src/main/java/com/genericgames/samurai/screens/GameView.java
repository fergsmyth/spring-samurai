package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.genericgames.samurai.DialogueManager;
import com.genericgames.samurai.IconFactory;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.physics.Arrow;
import com.genericgames.samurai.utility.DebugMode;

import java.util.ArrayList;
import java.util.List;

public class GameView extends StageView {

    private static WorldRenderer renderer = WorldRenderer.getRenderer();
    private static final float tileSize = 1f;

    // For rendering:
    private OrthogonalTiledMapRenderer mapRenderer;
    private DialogueManager dialogueManager;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private SamuraiWorld samuraiWorld;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private TmxMapLoader mapLoader;
    private SpriteBatch hudBatch;
    float scalingFactor = 2f;
    float right = (10f*WorldRenderer.getCameraHeight()) / 100f;
    float healthBarPosX = 1400;
    float healthBarPosY = (96f*WorldRenderer.getCameraHeight()) / 100f;
    float heartIndent = (3*WorldRenderer.getCameraWidth()) / 100f;
    CounterView counter;
    private Icon heartIcon;
    private DialogueIcon conversationIcon;
    private int[] backgroundLayers = {0};
    private int[] foregroundLayers = {1, 2, 3};
    private BitmapFont font = new BitmapFont();

    public GameView(OrthographicCamera camera, String currentLevel){
        dialogueManager = new DialogueManager();
        debugRenderer = new Box2DDebugRenderer();
        samuraiWorld = renderer.getWorld();
        hudBatch = new SpriteBatch();
        mapLoader = new TmxMapLoader();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        this.camera = camera;
        stage.getViewport().setCamera(camera);
        TiledMap map = mapLoader.load(currentLevel);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/32f);
        heartIcon = IconFactory.createHeartIcon(0, 760, 2f);
        counter = new CounterView();
    }

    @Override
    public void render(float delta){
        mapRenderer.setView((OrthographicCamera)stage.getCamera());
        mapRenderer.render(backgroundLayers);

        if(DebugMode.isDebugEnabled()){
            drawDebugBoundingBoxes();
        }

        //Game object handling (not rendering-related):
        samuraiWorld.step();

        stage.getCamera().position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
        stage.getCamera().update();
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        spriteBatch.begin();
        drawArrows();
        drawAllCharacters();
        spriteBatch.end();


        mapRenderer.render(foregroundLayers);

        counter.draw(Integer.toString(samuraiWorld.getCurrentLevel().getArenaLevelAttributes().getNumEnemiesKilled()), getUIMatrix(), 1430, 805);
        counter.draw("Width : " + Gdx.graphics.getWidth(), getUIMatrix(), 5, 40);
        counter.draw("Height : " + Gdx.graphics.getHeight(), getUIMatrix(), 5, 20);

        drawIcons();
        drawHUD();
        drawDialogue();

    }

    private void drawAllCharacters() {
        List<WorldCharacter> allCharacters = samuraiWorld.getAllCharacters();
        List<WorldCharacter> remainingCharacters = new ArrayList<WorldCharacter>();
        PlayerCharacter player = samuraiWorld.getPlayerCharacter();
        //Draw Dead characters first:
        for(WorldCharacter character : allCharacters){
            if(character instanceof Living && !((Living)character).isAlive()){
                if(character == player ||
                        MyMathUtils.getDistanceBetween(player, character) < WorldRenderer.getScreenSize()){
                    character.draw(spriteBatch);
                }
            }
            else {
                remainingCharacters.add(character);
            }
        }
        //Draw the remaining characters:
        for(WorldCharacter character : remainingCharacters){
            if(character != player ||
                    MyMathUtils.getDistanceBetween(player, character) < WorldRenderer.getScreenSize()){
                character.draw(spriteBatch);
            }
        }
    }

    private void drawDialogue() {
        if(dialogueManager.hasDialogue()){
            dialogueManager.renderPhrase();
        }
    }

    public void nextPhrase(){
        dialogueManager.nextPhrase();
    }

    public void setInConversation(){
        if(conversationIcon != null){
            dialogueManager.initialiseDialogue(conversationIcon.getDialogue());
        }
    }

    public void setConversationIcon(DialogueIcon conversationIcon){
        this.conversationIcon = conversationIcon;
    }

    @Override
    public Stage getStage() {
        return new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }

    @Override
    public void update(Object data) {
        mapRenderer.setMap(mapLoader.load((String) data));
    }

    @Override
    public void setData(Object data) {
    }

    private void drawIcons(){
        spriteBatch.begin();
        if(conversationIcon != null){
            conversationIcon.draw(spriteBatch);

        }
        spriteBatch.end();
    }


    private Matrix4 getUIMatrix() {
        Matrix4 uiMatrix = stage.getCamera().combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, WorldRenderer.getCameraWidth(), WorldRenderer.getCameraHeight());
        return uiMatrix;
    }

    private void drawHUD() {
        drawHeart();
        drawHealthBar();
    }

    private void drawHeart() {
        counter.draw("Heart Width : " + heartIcon.getX(), getUIMatrix(), 5, 80);
        counter.draw("Heart Height : " + heartIcon.getY(), getUIMatrix(), 5, 60);
        hudBatch.begin();
        heartIcon.draw(hudBatch);
        hudBatch.end();
    }

    private void drawHealthBar() {

        //shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        //shapeRenderer.setTransformMatrix(spriteBatch.getTransformMatrix());
        //shapeRenderer.translate(samuraiWorld.getPlayerCharacter().getX()-(WorldRenderer.getCameraWidth()/2),
        //        samuraiWorld.getPlayerCharacter().getY()-(WorldRenderer.getCameraHeight()/2), 0);

        //counter.draw("Health : " + samuraiWorld.getPlayerCharacter().getHealth(), getUIMatrix(), 5, 100);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(5, 350, 25, samuraiWorld.getPlayerCharacter().getHealth() * 4);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(5, 350, 25, samuraiWorld.getPlayerCharacter().getMaxHealth() * 4);
        shapeRenderer.end();

    }


    private void drawDebugBoundingBoxes() {
        debugRenderer.render(samuraiWorld.getPhysicalWorld(), camera.combined);
    }

    private void drawArrows(){
        for(Arrow arrow : samuraiWorld.getArrows()){
            arrow.draw(spriteBatch);
        }
    }
}
