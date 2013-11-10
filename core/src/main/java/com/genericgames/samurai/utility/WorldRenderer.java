package com.genericgames.samurai.utility;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.genericgames.samurai.model.*;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.playable.PlayerCharacter;
import com.genericgames.samurai.physics.PhysicalWorld;

import java.util.Collection;

public class WorldRenderer {

    private MyWorld myWorld;

    private OrthographicCamera camera;
    private static final float CAMERA_WIDTH = 28f;
    private static final float CAMERA_HEIGHT = 20f;
    private static final float tileSize = 1f;

    private int screenWidthInPixels = 800;
    private int screenHeightInPixels = 480;
    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

	// For rendering:
    private SpriteBatch spriteBatch;

	//Animations:
	private Animation walkAnimation;

	//For physics and collision detection:
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public WorldRenderer(MyWorld myWorld) {
        this.myWorld = myWorld;
        camera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.position.set(myWorld.getPlayerCharacter().getPositionX(), myWorld.getPlayerCharacter().getPositionY(), 0);
		spriteBatch = new SpriteBatch();
        loadTextures();
    }

    public void setSize (int w, int h) {
        this.screenWidthInPixels = w;
        this.screenHeightInPixels = h;
        ppuX = (float)screenWidthInPixels/CAMERA_WIDTH;
        ppuY = (float)screenHeightInPixels/CAMERA_HEIGHT;
    }

    private void loadTextures() {
		ImageCache.load(this);
    }

    public void render() {
		PhysicalWorld.checkForCollisions(myWorld);

		camera.position.set(myWorld.getPlayerCharacter().getPositionX(), myWorld.getPlayerCharacter().getPositionY(), 0);
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        spriteBatch.begin();
		drawGrass();
		drawPlayerCharacter();
		drawWalls();
        drawDoors();
        drawRoofs();
        drawChests();
		drawCastles();
        spriteBatch.end();

        if(DebugMode.isDebugEnabled()){
            drawDebugBoundingBoxes();
        }
    }

    private void drawDebugBoundingBoxes() {
        debugRenderer.render(myWorld.getPhysicalWorld(), camera.combined);
    }

    private void drawGrass() {
		ImageCache.grassTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		spriteBatch.draw(ImageCache.grassTexture, 0-(tileSize/2), 0-(tileSize/2),
			  myWorld.getLevelWidth(),
			  myWorld.getLevelHeight(),
			  myWorld.getLevelWidth(), myWorld.getLevelHeight(),
			  0, 0);
	}

	private void drawPlayerCharacter() {
		PlayerCharacter playerCharacter = myWorld.getPlayerCharacter();
		TextureRegion texture;
		if(playerCharacter.getState().equals(State.WALKING)) {
			texture = walkAnimation.getKeyFrame(playerCharacter.getStateTime(), true);
		}
		else{
			texture = ImageCache.playerCharacterTexture;
		}
		spriteBatch.draw(texture, playerCharacter.getPositionX()-(tileSize/2), playerCharacter.getPositionY()-(tileSize/2),
			  0.5f,  0.5f, tileSize, tileSize, 1, 1, playerCharacter.getRotationInDegrees());
	}


	private void drawWalls() {
        drawWorldObject(myWorld.getWalls(), ImageCache.wallTexture);
	}

    private void drawDoors(){
        drawWorldObject(myWorld.getDoors(), ImageCache.doorTexture);
    }

	private void drawCastles() {
        drawWorldObject(myWorld.getCastles(), ImageCache.castleTexture);
	}

    private void drawRoofs(){
        drawWorldObject(myWorld.getRoofs(), ImageCache.roofTexture);
    }

    private void drawChests(){
        drawWorldObject(myWorld.getChests(), ImageCache.chestTexture);
    }

    private void drawWorldObject(Collection<? extends WorldObject> worldObjects, Texture texture) {
        for(WorldObject worldObject : worldObjects){
            spriteBatch.draw(texture, worldObject.getPositionX()-(tileSize/2), worldObject.getPositionY()-(tileSize/2), tileSize, tileSize);
        }
    }

    public Animation getWalkAnimation() {
		return walkAnimation;
	}

	public void setWalkAnimation(Animation walkAnimation) {
		this.walkAnimation = walkAnimation;
	}
}

