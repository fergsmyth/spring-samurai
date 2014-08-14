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
import com.genericgames.samurai.model.movable.character.ai.enemies.Enemy;
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
    private Icon healthIcon;
    private Icon swordIcon;
    private Icon bowIcon;
    private int weaponInventoryPositionX = 4;
    private int weaponInventoryPositionY = 5;
    private float weaponInventoryScaleX;
    private float weaponInventoryScaleY;
    private BitmapFont font;
    private DialogueIcon conversationIcon;
    private int[] backgroundLayers = {0};
    private int[] foregroundLayers = {1, 2, 3};

    public GameView(OrthographicCamera camera, String currentLevel){
        super(renderer.width, renderer.height);
        initialise();
        setCamera(camera);
        createMap(currentLevel);
        samuraiWorld = renderer.getWorld();
        healthIcon = IconFactory.createHealthIcon(width / 720, height / 2.15f,
                20 * width / 720, 20 * height / 402.5f);
        weaponInventoryScaleX = width /36;
        weaponInventoryScaleY = height /20.125f;
        swordIcon = IconFactory.createSwordIcon(weaponInventoryPositionX, weaponInventoryPositionY,
                weaponInventoryScaleX, weaponInventoryScaleY);
        bowIcon = IconFactory.createBowIcon(weaponInventoryPositionX, weaponInventoryPositionY,
                weaponInventoryScaleX, weaponInventoryScaleY);
        dialogueManager = new DialogueManager();
        informationView = new InformationView(hudBatch, font);
    }

    protected void initialise() {
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
        List<WorldCharacter> liveCharacters = drawDeadCharacters();
        drawQuivers();
        drawArrows();
        //draw remaining (live) characters:
        drawCharacters(liveCharacters);
        spriteBatch.end();

        drawEnemyHealthBar();

        mapRenderer.render(foregroundLayers);

        drawCherryBlossoms();
        drawArenaHUD();
        informationView.draw("Width : " + Gdx.graphics.getWidth(), width, 40, 1f, BitmapFont.HAlignment.RIGHT);
        informationView.draw("Height : " + Gdx.graphics.getHeight(), width, 20, 1f, BitmapFont.HAlignment.RIGHT);

        drawIcons();
        drawHUD();
        drawDialogue();

        informationView.draw("FPS : " + Gdx.graphics.getFramesPerSecond(), width, 100, 1f, BitmapFont.HAlignment.RIGHT);
        informationView.draw("Player X : " + samuraiWorld.getPlayerCharacter().getX(), width, 120, 1f, BitmapFont.HAlignment.RIGHT);
        informationView.draw("Player Y : " + samuraiWorld.getPlayerCharacter().getY(), width, 140, 1f, BitmapFont.HAlignment.RIGHT);
    }

    private void drawArenaHUD() {
        if(samuraiWorld.getCurrentLevel().getArenaLevelAttributes().isArenaLevel()) {
            drawArenaRoundCounter();
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
            informationView.draw(title,
                    width / 2, (3 * height) / 4,
                    5, BitmapFont.HAlignment.CENTER);
        }
    }

    private void drawArenaKillCounter() {
        informationView.draw("Kills : " + samuraiWorld.getCurrentLevel().getArenaLevelAttributes().getTotalNumEnemiesKilled(),
                width -60, height);
    }

    private void drawArenaRoundCounter() {
        informationView.draw(String.valueOf(samuraiWorld.getCurrentLevel().getArenaLevelAttributes().getRound().getRoundNum()),
                width -15, height -10,
                2, BitmapFont.HAlignment.RIGHT);
    }

    private void drawCherryBlossoms() {
        for(CherryBlossom cherryBlossom : samuraiWorld.getCherryBlossoms()){
            cherryBlossom.draw(spriteBatch, shapeRenderer);
        }
        for(Particle particle : samuraiWorld.getParticles()){
            particle.draw(spriteBatch, shapeRenderer);
        }
    }

    private List<WorldCharacter> drawDeadCharacters() {
        List<WorldCharacter> allCharacters = samuraiWorld.getAllCharacters();
        List<WorldCharacter> liveCharacters = new ArrayList<WorldCharacter>();
        //Draw Dead characters first:
        for(WorldCharacter character : allCharacters){
            if(character instanceof Living && !((Living)character).isAlive()){
                character.draw(spriteBatch, shapeRenderer);
            }
            else {
                liveCharacters.add(character);
            }
        }
        return liveCharacters;
    }

    private void drawCharacters(List<WorldCharacter> liveCharacters) {
        for(WorldCharacter character : liveCharacters){
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
        float indentationCounter = 0;
        for(Weapon weapon : weaponInventory.getWeapons()){
            weaponIcon = weapon.getIcon(this);
            weaponIcon.setPosition(weaponInventoryPositionX+indentationCounter, weaponInventoryPositionY);

            if(weaponInventory.getSelectedWeapon().equals(weapon)){
                //Draw selected weapon bigger:
                weaponIcon.setScalingFactors(weaponInventoryScaleX*1.5f, weaponInventoryScaleY*1.5f);
                indentationCounter = indentationCounter + weaponInventoryScaleX*1.5f;
            }
            else {
                weaponIcon.setScalingFactors(weaponInventoryScaleX, weaponInventoryScaleY);
                indentationCounter = indentationCounter + weaponInventoryScaleX;
            }

            weaponIcon.draw(hudBatch, shapeRenderer);

            if(weapon.equals(Weapon.BOW)){
                hudBatch.end();
                //draw numArrows counter too
                informationView.draw(Integer.toString(weaponInventory.getNumArrows()),
                        weaponInventoryPositionX + indentationCounter, weaponInventoryPositionY + 10);
                indentationCounter = indentationCounter + weaponInventoryScaleX/2f;
                hudBatch.begin();
            }
        }
        hudBatch.end();
    }

    private void drawHeart() {
        informationView.draw("Heart Width : " + healthIcon.getX(), width, 80, 1f, BitmapFont.HAlignment.RIGHT);
        informationView.draw("Heart Height : " + healthIcon.getY(), width, 60, 1f, BitmapFont.HAlignment.RIGHT);
        hudBatch.begin();
        healthIcon.draw(hudBatch, shapeRenderer);
        hudBatch.end();
    }

    private void drawEnemyHealthBar(){
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(spriteBatch.getTransformMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Enemy enemy : samuraiWorld.getEnemies()){
            enemy.drawHealthBar(shapeRenderer, spriteBatch);
        }
        shapeRenderer.end();
    }

    private void drawHealthBar() {
        shapeRenderer.setProjectionMatrix(hudBatch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(hudBatch.getTransformMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1);
        float healthBarToIconWidthRatio = 0.8f;
        float healthIconScalingFactorX = healthIcon.getScalingFactorX();
        float healthIconScalingFactorY = healthIcon.getScalingFactorY();
        shapeRenderer.rect(healthIcon.getX() + (((1 - healthBarToIconWidthRatio) * healthIconScalingFactorX) / 2), healthIcon.getY() + (healthIconScalingFactorY / 2),
                healthIconScalingFactorX * healthBarToIconWidthRatio, samuraiWorld.getPlayerCharacter().getHealth() * (height / 201.25f));
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(healthIcon.getX() + (((1 - healthBarToIconWidthRatio) * healthIconScalingFactorX) / 2), healthIcon.getY() + (healthIconScalingFactorY / 2),
                healthIconScalingFactorX * healthBarToIconWidthRatio, samuraiWorld.getPlayerCharacter().getMaxHealth() * (height / 201.25f));
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
