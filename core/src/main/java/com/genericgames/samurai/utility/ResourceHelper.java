package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ResourceHelper {

    private static final String RESOURCES_LOGO = "resources/logo/";
    public static final String RESOURCES_SPLASH = "resources/splash/";

    public static Sprite getLogo(String logoName){
        return new Sprite(new Texture(Gdx.files.internal(RESOURCES_LOGO + logoName)));
    }

    public static Sprite getSplashImage(String imageName){
        return new Sprite(new Texture(Gdx.files.internal(RESOURCES_SPLASH + imageName)));
    }
}
