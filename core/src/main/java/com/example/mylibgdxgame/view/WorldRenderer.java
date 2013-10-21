package com.example.mylibgdxgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.example.mylibgdxgame.model.Castle;
import com.example.mylibgdxgame.model.Wall;
import com.example.mylibgdxgame.model.World;
import com.example.mylibgdxgame.model.movable.living.playable.King;

/**
 * Created with IntelliJ IDEA.
 * User: John
 * Date: 01/10/13
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
public class WorldRenderer {

    private World world;
    ImageCache imageCache;

    private OrthographicCamera cam;
    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;
    private static final int tileSize = 100;

    private int width;
    private int height;
    private float ppuX; // pixels per unit on the X axis
    private float ppuY; // pixels per unit on the Y axis

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();
    private SpriteBatch spriteBatch;
    private boolean debug = false;

    public WorldRenderer(World world, boolean debug) {
        this.world = world;
        this.debug = debug;
//        cam = new OrthographicCamera(100, 100);
        // the extent of the camera's view (in pixels) - width and height
//        cam = new OrthographicCamera(1440, 900);
        cam = new OrthographicCamera(tileSize*world.getLevelWidth(), tileSize*world.getLevelHeight());
        cam.position.set(tileSize*world.getLevelWidth()/2, tileSize*world.getLevelHeight()/2, 0);
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
        cam.update();

        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.enableBlending();
        spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        spriteBatch.begin();

        //Draw stuff here:
        //                                                           startingX,startingY,endingX,endingY (pixel co-ords to take sample from)
        TextureRegion grassLand = new TextureRegion(ImageCache.grassTexture, 0, 0, tileSize*world.getLevelWidth(), tileSize*world.getLevelWidth());
        grassLand.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Sprite grassSprite = new Sprite(grassLand);
        grassSprite.setPosition(0, 0);
        grassSprite.draw(spriteBatch);

        King king = world.getKing();
        //                                       xPos,               yPos
        spriteBatch.draw(ImageCache.kingTexture, tileSize *king.getPositionX(), tileSize *king.getPositionY());

        for(Wall currentWall : world.getWalls()){
            spriteBatch.draw(ImageCache.wallTexture, tileSize *currentWall.getPositionX(), tileSize *currentWall.getPositionY());
        }

        Castle castle = world.getCastle();
        spriteBatch.draw(ImageCache.castleTexture, tileSize *castle.getPositionX(), tileSize *castle.getPositionY());

        spriteBatch.end();
        if (debug){
            drawDebug();
        }
    }

    private void drawDebug() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        // render stuff that WILL be invisible in final game here:

        debugRenderer.end();
    }
}

