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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.genericgames.samurai.DialogueManager;
import com.genericgames.samurai.IconFactory;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.arena.Round;
import com.genericgames.samurai.model.movable.character.WorldCharacter;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.model.weapon.Quiver;
import com.genericgames.samurai.model.weapon.Weapon;
import com.genericgames.samurai.model.weapon.WeaponInventory;
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
    InformationView informationView;
    private int infoViewPositionX = 1295;
    private Icon healthIcon;
    private Icon swordIcon;
    private Icon bowIcon;
    private int weaponInventoryPositionX = 4;
    private int weaponInventoryPositionY = 5;
    private float weaponInventoryScale = 2f;
    private BitmapFont font;
    private DialogueIcon conversationIcon;
    private int[] backgroundLayers = {0};
    private int[] foregroundLayers = {1, 2, 3};

    public GameView(OrthographicCamera camera, String currentLevel){
        initialise();
        setCamera(camera);
        createMap(currentLevel);
        samuraiWorld = renderer.getWorld();
        healthIcon = IconFactory.createHeartIcon(-2, 383, 2f);
        swordIcon = IconFactory.createSwordIcon(weaponInventoryPositionX, weaponInventoryPositionY, weaponInventoryScale);
        bowIcon = IconFactory.createBowIcon(weaponInventoryPositionX, weaponInventoryPositionY, weaponInventoryScale);
        dialogueManager = new DialogueManager();
        informationView = new InformationView(hudBatch, font);
    }

    private void initialise() {
        font = new BitmapFont();
        hudBatch = new SpriteBatch();
        mapLoader = new TmxMapLoader();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
    }

    private void setCamera(OrthographicCamera camera) {
        this.camera = camera;
        stage.getViewport().setCamera(camera);
    }

    private void createMap(String currentLevel) {
        TiledMap map = mapLoader.load(currentLevel);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/32f);
    }

    @Override
    public void render(float delta){
        //Game object handling (not rendering-related):
        samuraiWorld.step();

        //Update camera position BEFORE drawing anything!
        stage.getCamera().position.set(samuraiWorld.getPlayerCharacter().getX(), samuraiWorld.getPlayerCharacter().getY(), 0);
        stage.getCamera().update();

        mapRenderer.setView((OrthographicCamera)stage.getCamera());
        mapRenderer.render(backgroundLayers);

        if(DebugMode.isDebugEnabled()){
            drawDebugBoundingBoxes();
        }

        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        spriteBatch.begin();
        drawArrows();
        drawQuivers();
        drawAllCharacters();
        spriteBatch.end();


        mapRenderer.render(foregroundLayers);

        drawCherryBlossoms();
        drawArenaHUD();
        informationView.draw("Width : " + Gdx.graphics.getWidth(), infoViewPositionX, 40);
        informationView.draw("Height : " + Gdx.graphics.getHeight(), infoViewPositionX, 20);

        drawIcons();
        drawHUD();
        drawDialogue();

        informationView.draw("FPS : " + Gdx.graphics.getFramesPerSecond(), infoViewPositionX, 100);
        informationView.draw("Player X : " + samuraiWorld.getPlayerCharacter().getX(), infoViewPositionX, 120);
        informationView.draw("Player Y : " + samuraiWorld.getPlayerCharacter().getY(), infoViewPositionX, 140);
    }

    private void drawArenaHUD() {
        if(samuraiWorld.getCurrentLevel().getArenaLevelAttributes().isArenaLevel()) {
            drawArenaKillCounter();
            drawArenaTitles();
        }
    }

    private void drawArenaTitles() {
        Round arenaRound = samuraiWorld.getCurrentLevel().getArenaLevelAttributes().getRound();
        if(arenaRound.isCountdownInitiated()){
            int nextRoundNum = arenaRound.getRoundNum() + 1;
            String title = "Round " + nextRoundNum;

            if(arenaRound.getNumFramesUntilNextRound() < 20){
                title = title.concat("\nFIGHT!!!");
            }
            else if(arenaRound.getNumFramesUntilNextRound() < 80){
                title = title.concat("\n1");
            }
            else if(arenaRound.getNumFramesUntilNextRound() < 140){
                title = title.concat("\n2");
            }
            else if(arenaRound.getNumFramesUntilNextRound() < 200){
                title = title.concat("\n3");
            }
            informationView.draw(title, 1380/2, (3*805)/4, 5, BitmapFont.HAlignment.CENTER);
        }
    }

    private void drawArenaKillCounter() {
        informationView.draw("Kills : " + samuraiWorld.getCurrentLevel().getArenaLevelAttributes().getTotalNumEnemiesKilled(), 1380, 805);
    }

    private void drawCherryBlossoms() {
        for(CherryBlossom cherryBlossom : samuraiWorld.getCherryBlossoms()){
            cherryBlossom.draw(spriteBatch, shapeRenderer);
        }
        for(Particle particle : samuraiWorld.getParticles()){
            particle.draw(spriteBatch, shapeRenderer);
        }
    }

    private void drawAllCharacters() {
        List<WorldCharacter> allCharacters = samuraiWorld.getAllCharacters();
        List<WorldCharacter> remainingCharacters = new ArrayList<WorldCharacter>();
        //Draw Dead characters first:
        for(WorldCharacter character : allCharacters){
            if(character instanceof Living && !((Living)character).isAlive()){
                character.draw(spriteBatch, shapeRenderer);
            }
            else {
                remainingCharacters.add(character);
            }
        }
        //Draw the remaining characters:
        for(WorldCharacter character : remainingCharacters){
            character.draw(spriteBatch, shapeRenderer);
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
            dialogueManager.initialiseDialogue(hudBatch, shapeRenderer, conversationIcon.getDialogue(), font);
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
            conversationIcon.draw(spriteBatch, shapeRenderer);

        }
        spriteBatch.end();
    }

    private void drawHUD() {
        drawHealthBar();
        drawHeart();
        drawWeaponInventory();
    }

    private void drawWeaponInventory() {
        hudBatch.begin();
        WeaponInventory weaponInventory = samuraiWorld.getPlayerCharacter().getWeaponInventory();
        Icon weaponIcon;
        int indentationCounter = 0;
        for(Weapon weapon : weaponInventory.getWeapons()){
            weaponIcon = weapon.getIcon(this);
            weaponIcon.setPosition(weaponInventoryPositionX+indentationCounter, weaponInventoryPositionY);

            if(weaponInventory.getSelectedWeapon().equals(weapon)){
                //Draw selected weapon bigger:
                weaponIcon.setScalingFactor(weaponInventoryScale*1.5f);
                indentationCounter = indentationCounter + 63;
            }
            else {
                weaponIcon.setScalingFactor(weaponInventoryScale);
                indentationCounter = indentationCounter + 40;
            }

            weaponIcon.draw(hudBatch, shapeRenderer);

            if(weapon.equals(Weapon.BOW)){
                hudBatch.end();
                //draw numArrows counter too
                informationView.draw(Integer.toString(weaponInventory.getNumArrows()),
                        weaponInventoryPositionX + indentationCounter, weaponInventoryPositionY + 10);
                indentationCounter = indentationCounter + 6;
                hudBatch.begin();
            }
        }
        hudBatch.end();
    }

    private void drawHeart() {
        informationView.draw("Heart Width : " + healthIcon.getX(), infoViewPositionX, 80);
        informationView.draw("Heart Height : " + healthIcon.getY(), infoViewPositionX, 60);
        hudBatch.begin();
        healthIcon.draw(hudBatch, shapeRenderer);
        hudBatch.end();
    }

    private void drawHealthBar() {
        shapeRenderer.setProjectionMatrix(hudBatch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(hudBatch.getTransformMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(5, 400, 25, samuraiWorld.getPlayerCharacter().getHealth() * 4);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(5, 400, 25, samuraiWorld.getPlayerCharacter().getMaxHealth() * 4);
        shapeRenderer.end();

    }


    private void drawDebugBoundingBoxes() {
        debugRenderer.render(samuraiWorld.getPhysicalWorld(), camera.combined);
    }

    private void drawArrows(){
        for(Arrow arrow : samuraiWorld.getArrows()){
            arrow.draw(spriteBatch, shapeRenderer);
        }
    }

    private void drawQuivers(){
        for(Quiver quiver : samuraiWorld.getQuivers()){
            quiver.draw(spriteBatch, shapeRenderer);
        }
    }

    public Icon getSwordIcon() {
        return swordIcon;
    }

    public void setSwordIcon(Icon swordIcon) {
        this.swordIcon = swordIcon;
    }

    public Icon getBowIcon() {
        return bowIcon;
    }

    public void setBowIcon(Icon bowIcon) {
        this.bowIcon = bowIcon;
    }

    public void move(float deltaX, float deltaY) {
        //TODO For testing purposes:
//        infoViewPositionX = infoViewPositionX + deltaX;
//        swordIcon.setPosition(swordIcon.getX()+deltaX, swordIcon.getY()+deltaY);
//        for(Quiver q : samuraiWorld.getQuivers()){
//            q.deltaX = q.deltaX+deltaX;
//            q.deltaY = q.deltaY+deltaY;
//        }
    }
}
