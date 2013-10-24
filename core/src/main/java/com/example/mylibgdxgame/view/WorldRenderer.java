package com.example.mylibgdxgame.view;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.example.mylibgdxgame.model.Castle;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.Wall;
import com.example.mylibgdxgame.model.movable.living.playable.PlayerCharacter;
import com.example.mylibgdxgame.physics.PhysicalWorldHelper;

public class WorldRenderer {

    private MyWorld myWorld;

    private OrthographicCamera cam;
    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;
    private static final float tileSize = 1f;

    private int width = 20;
    private int height = 20;
    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

	// For rendering:
    private SpriteBatch spriteBatch;

	//For physics and collision detection:
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public WorldRenderer(MyWorld myWorld) {
        this.myWorld = myWorld;
        cam = new OrthographicCamera(width, height);
		cam.position.set(myWorld.getPlayerCharacter().getPositionX(), myWorld.getPlayerCharacter().getPositionY(), 0);
		spriteBatch = new SpriteBatch();
        loadTextures();
    }

    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
    }

    private void loadTextures() {
		ImageCache.load();
    }

    public void render() {
		PhysicalWorldHelper.checkForCollisions(myWorld);

		cam.position.set(myWorld.getPlayerCharacter().getPositionX(), myWorld.getPlayerCharacter().getPositionY(), 0);
        cam.update();

        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();

        //Draw stuff here:
		drawGrass();

		drawPlayerCharacter();

		drawWalls();

		drawCastle();

        spriteBatch.end();


		//Draw bounding boxes:
		World physicalWorld = myWorld.getPhysicalWorld();
		debugRenderer.render(physicalWorld, cam.combined);
    }


	private void drawGrass() {
		ImageCache.grassTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		spriteBatch.draw(ImageCache.grassTexture, 0, 0,
			  myWorld.getLevelWidth()-(tileSize/2),
			  myWorld.getLevelHeight()-(tileSize/2),
			  0, myWorld.getLevelWidth(),
			  myWorld.getLevelHeight(), 0);
	}

	private void drawPlayerCharacter() {
		PlayerCharacter playerCharacter = myWorld.getPlayerCharacter();
		//                                                  xPos,                           yPos
		spriteBatch.draw(ImageCache.playerCharacterTexture, playerCharacter.getPositionX()-(tileSize/2), playerCharacter.getPositionY()-(tileSize/2),
			  tileSize, tileSize);
	}

	private void drawWalls() {
		for(Wall currentWall : myWorld.getWalls()){
			spriteBatch.draw(ImageCache.wallTexture, currentWall.getPositionX()-(tileSize/2), currentWall.getPositionY()-(tileSize/2), tileSize, tileSize);
		}
	}

	private void drawCastle() {
		Castle castle = myWorld.getCastle();
		spriteBatch.draw(ImageCache.castleTexture, castle.getPositionX()-(tileSize/2), castle.getPositionY()-(tileSize/2), tileSize, tileSize);
	}
}

