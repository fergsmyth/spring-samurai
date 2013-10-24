package com.example.mylibgdxgame.view;

import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.example.mylibgdxgame.model.Castle;
import com.example.mylibgdxgame.model.Collidable;
import com.example.mylibgdxgame.model.MyWorld;
import com.example.mylibgdxgame.model.Wall;
import com.example.mylibgdxgame.model.movable.living.playable.King;

public class WorldRenderer {

    private MyWorld myWorld;

    private OrthographicCamera cam;
    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;
    private static final int tileSize = 100;

    private int width;
    private int height;
    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

	// For rendering:
    private SpriteBatch spriteBatch;
	ImageCache imageCache;

	//For physics and collision detection:
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    public WorldRenderer(MyWorld myWorld) {
        this.myWorld = myWorld;
//        cam = new OrthographicCamera(100, 100);
        // the extent of the camera's view (in pixels) - width and height
//        cam = new OrthographicCamera(1440, 900);
        cam = new OrthographicCamera(tileSize*myWorld.getLevelWidth(), tileSize*myWorld.getLevelHeight());
        cam.position.set(tileSize*myWorld.getLevelWidth()/2, tileSize*myWorld.getLevelHeight()/2, 0);
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
        imageCache = new ImageCache();
        imageCache.load();
    }

    public void render() {
		checkForCollisions();

        cam.update();

        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();

        //Draw stuff here:
		drawGrass();

		drawKing();

		drawWalls();

		drawCastle();

        spriteBatch.end();


//		//Draw bounding boxes:
//		World physicalWorld = myWorld.getPhysicalWorld();
//		debugRenderer.render(physicalWorld, cam.combined);
    }

	private void checkForCollisions() {
		World physicalWorld = myWorld.getPhysicalWorld();

		// Step in physical world:
		physicalWorld.step(1 / 20f, 8, 3);


		Array<Body> bodies = new Array<Body>();
		physicalWorld.getBodies(bodies);

		for (Body b : bodies){

			// Get the bodies user data - in this example, our user
			// data is an instance of the Collidable class
			 Collidable c = (Collidable) b.getUserData();

			if (c != null) {
				// Correct the entities/sprites position and angle based on body's (potentially) new position
				c.setPosition(b.getPosition().x, b.getPosition().y);
//				// We need to convert our angle from radians to degrees
//				c.setRotation(MathUtils.radiansToDegrees * b.getAngle());
			}
		}
	}

	private void drawGrass() {
		//                                                           startingX,startingY,endingX,endingY (pixel co-ords to take sample from)
		TextureRegion grassLand = new TextureRegion(ImageCache.grassTexture, 0, 0, tileSize*myWorld.getLevelWidth(),  tileSize*myWorld.getLevelWidth());
		grassLand.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		Sprite grassSprite = new Sprite(grassLand);
		grassSprite.setPosition(0, 0);
		grassSprite.draw(spriteBatch);
	}

	private void drawKing() {
		King king = myWorld.getKing();
		//                                       xPos,               yPos
		spriteBatch.draw(ImageCache.kingTexture, tileSize*king.getPositionX(), tileSize*king.getPositionY());
	}

	private void drawWalls() {
		for(Wall currentWall : myWorld.getWalls()){
			spriteBatch.draw(ImageCache.wallTexture, tileSize*currentWall.getPositionX(), tileSize*currentWall.getPositionY());
		}
	}

	private void drawCastle() {
		Castle castle = myWorld.getCastle();
		spriteBatch.draw(ImageCache.castleTexture, tileSize*castle.getPositionX(), tileSize*castle.getPositionY());
	}
}

