package com.example.mylibgdxgame.view;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.example.mylibgdxgame.model.Castle;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.Wall;
import com.example.mylibgdxgame.model.movable.living.playable.PlayerCharacter;
import com.example.mylibgdxgame.physics.PhysicalWorldHelper;

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
		ImageCache.load();
    }

    public void render() {
		PhysicalWorldHelper.checkForCollisions(myWorld);

		camera.position.set(myWorld.getPlayerCharacter().getPositionX(), myWorld.getPlayerCharacter().getPositionY(), 0);
        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();

        //Draw stuff here:
		drawGrass();

		drawPlayerCharacter();

		drawWalls();

		drawCastles();

        spriteBatch.end();


		//Draw bounding boxes:
		World physicalWorld = myWorld.getPhysicalWorld();
		debugRenderer.render(physicalWorld, camera.combined);
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
        Sprite playerSprite = new Sprite(ImageCache.playerCharacterTexture);
        playerSprite.setPosition(playerCharacter.getPositionX()-(tileSize/2), playerCharacter.getPositionY()-(tileSize/2));
        playerSprite.setOrigin(playerCharacter.getPositionX()-(tileSize/2),  playerCharacter.getPositionY()-(tileSize/2));
        System.out.println("Origin X : " + playerSprite.getOriginX());
        System.out.println("Origin Y : " + playerSprite.getOriginY());
        playerSprite.setSize(tileSize, tileSize);
        playerSprite.setRotation(playerCharacter.getRotationInDegrees());
//        playerSprite.set

//        playerSprite.draw(spriteBatch);

        TextureRegion region = new TextureRegion(ImageCache.playerCharacterTexture);
		spriteBatch.draw(region, playerCharacter.getPositionX()-(tileSize/2), playerCharacter.getPositionY()-(tileSize/2),
              0.5f,  0.5f,
			  tileSize, tileSize, 1, 1, playerCharacter.getRotationInDegrees());

//        spriteBatch.draw(ImageCache.playerCharacterTexture, playerCharacter.getPositionX()-(tileSize/2), playerCharacter.getPositionY()-(tileSize/2),
//              tileSize, tileSize);
	}

	private void drawWalls() {
		for(Wall currentWall : myWorld.getWalls()){
			spriteBatch.draw(ImageCache.wallTexture, currentWall.getPositionX()-(tileSize/2), currentWall.getPositionY()-(tileSize/2), tileSize, tileSize);
		}
	}

	private void drawCastles() {
		for(Castle currentCastle : myWorld.getCastles()){
			spriteBatch.draw(ImageCache.castleTexture, currentCastle.getPositionX()-(tileSize/2), currentCastle.getPositionY()-(tileSize/2), tileSize, tileSize);
		}
	}
}

