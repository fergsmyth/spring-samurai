package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.genericgames.samurai.model.DialogueIcon;
import com.genericgames.samurai.model.Icon;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.physics.Arrow;
import com.genericgames.samurai.utility.DebugMode;

import java.util.ArrayList;
import java.util.List;

public class GameView extends StageView {

    private static WorldRenderer renderer = WorldRenderer.getRenderer();
    private static final float CAMERA_WIDTH = 20f;
    private static final float CAMERA_HEIGHT = 20f;
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
    private Icon heartIcon;
    private DialogueIcon conversationIcon;
    private int[] backgroundLayers = {0};
    private int[] foregroundLayers = {1, 2, 3};

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
        heartIcon = IconFactory.createHeartIcon(0, (96f*CAMERA_HEIGHT)/100f);
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

        drawIcons();
        drawHUD();
        drawDialogue();

    }

    private void drawAllCharacters() {
        List<WorldCharacter> allCharacters = samuraiWorld.getAllCharacters();
        List<WorldCharacter> remainingCharacters = new ArrayList<WorldCharacter>();
        //Draw Dead characters first:
        for(WorldCharacter character : allCharacters){
            if(character instanceof Living && !((Living)character).isAlive()){
                character.draw(spriteBatch);
            }
            else {
                remainingCharacters.add(character);
            }
        }
        //Draw the remaining characters:
        for(WorldCharacter character : remainingCharacters){
            character.draw(spriteBatch);
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

    private void drawHUD() {
        //Setup camera matrices for using ShapeRenderer:
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(spriteBatch.getTransformMatrix());
        shapeRenderer.translate(samuraiWorld.getPlayerCharacter().getX()-(CAMERA_WIDTH/2), samuraiWorld.getPlayerCharacter().getY()-(CAMERA_HEIGHT/2), 0);

        //Setup camera matrices for using SpriteBatch:
        Matrix4 uiMatrix = stage.getCamera().combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        hudBatch.setProjectionMatrix(uiMatrix);

        drawHealthBar();
    }

    private void drawHealthBar() {
        float scalingFactor = 0.025f;
        float healthBarPosX = 0.2f;
        float healthBarPosY = (96f*CAMERA_HEIGHT)/100f;

        //Draw heart:
        hudBatch.begin();
        //System.out.println("X : " + healthBarPosX);
        //System.out.println("Y : " + healthBarPosY);
        //hudBatch.draw(ImageCache.heartIcon, healthBarPosX, healthBarPosY, 20*scalingFactor, 20*scalingFactor);
        heartIcon.draw(hudBatch);
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

    private void drawArrows(){
        for(Arrow arrow : samuraiWorld.getArrows()){
            arrow.draw(spriteBatch);
        }
    }
}
