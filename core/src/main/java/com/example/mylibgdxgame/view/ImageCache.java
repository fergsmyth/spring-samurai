package com.example.mylibgdxgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ImageCache {

    public static Texture playerCharacterTexture;
    public static Texture grassTexture;
    public static Texture wallTexture;
    public static Texture castleTexture;

    public static void load () {
        playerCharacterTexture = new  Texture(Gdx.files.internal("samurai.png"));
        grassTexture = new  Texture(Gdx.files.internal("grass-01.png"));
        wallTexture = new  Texture(Gdx.files.internal("wall.png"));
        castleTexture = new  Texture(Gdx.files.internal("castle-02.png"));
    }
}
