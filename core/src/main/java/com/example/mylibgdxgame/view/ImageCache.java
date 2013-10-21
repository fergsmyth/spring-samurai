package com.example.mylibgdxgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageCache {

    public static Texture kingTexture;
    public static Texture grassTexture;
    public static Texture wallTexture;
    public static Texture castleTexture;

    public static void load () {
        kingTexture = new  Texture(Gdx.files.internal("king-01.png"));
        grassTexture = new  Texture(Gdx.files.internal("grass-01.png"));
        wallTexture = new  Texture(Gdx.files.internal("wall.png"));
        castleTexture = new  Texture(Gdx.files.internal("castle-02.png"));
    }
}
