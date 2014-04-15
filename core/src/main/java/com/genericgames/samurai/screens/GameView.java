package com.genericgames.samurai.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.genericgames.samurai.Dialogue;
import com.genericgames.samurai.DialogueManager;
import com.genericgames.samurai.model.Icon;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.Enemy;
import com.genericgames.samurai.model.movable.character.ai.NPC;
import com.genericgames.samurai.physics.Arrow;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.DebugMode;
import com.genericgames.samurai.utility.ImageCache;

import java.util.Map;

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
    private Dialogue dialogue;
    private Icon icon;

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
        //loadTextures();
    }

    @Override
    public void render(float delta){
        mapRenderer.setView((OrthographicCamera)stage.getCamera());
        mapRenderer.render();

        if(DebugMode.isDebugEnabled()){
            drawDebugBoundingBoxes();
        }
        samuraiWorld.deleteWorldObjects();
        PhysicalWorldHelper.checkForCollisions(samuraiWorld);

        stage.getCamera().position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
        stage.getCamera().update();
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        spriteBatch.begin();
        drawPlayerCharacter();
        drawEnemies();
        drawNPCs();
        drawArrows();
        drawIcons();
        spriteBatch.end();

        drawHUD();
        drawDialogue();

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
        if(icon != null){
            dialogueManager.initialiseDialogue(icon.getDialogue());
        }
    }

    public void setIcon(Icon icon){
        this.icon = icon;
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
        if(icon != null){
            spriteBatch.draw(ImageCache.conversationIcon, icon.getX() - tileSize, icon.getY(), 20*0.05f, 20*0.05f);
        }
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
        samuraiWorld.getPlayerCharacter().draw(spriteBatch);
        /*
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
        */

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

    private void drawNPCs(){
        for(NPC npc : samuraiWorld.getNPCs()){
            Map<State, Animation> animationMap = ImageCache.getAnimations().get(npc.getClass());
            TextureRegion texture = animationMap.get(npc.getState()).getKeyFrame(npc.getStateTime(),
                    npc.getState().isLoopingState());

            spriteBatch.draw(texture, npc.getX() - (tileSize / 2), npc.getY() - (tileSize / 2),
                    0.5f, 0.5f, tileSize, tileSize, 1, 1, npc.getRotationInDegrees());
        }
    }

    private void drawArrows(){
        for(Arrow arrow : samuraiWorld.getArrows()){
            arrow.draw(spriteBatch);
        }
    }

    private void loadTextures() {
        ImageCache.load();
    }
}
