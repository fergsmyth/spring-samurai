package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.File;

public class ResourceHelper {

    public static final String RESOURCE_LOGO = "resources/logo/";
    public static final String RESOURCE_SPLASH = "resources/splash/";
    private static String APP_NAME = ".springsamurai";
    private static String SAVE = "saves";
    private static String VERSION = "0_0_1";
    private static String HOME = File.separator + APP_NAME + File.separator + VERSION + File.separator + SAVE + File.separator;

    public static final BitmapFont whiteFont = getHeaderFont();

    public static BitmapFont getHeaderFont(){
        FileHandle handle = Gdx.files.internal("fonts/heading.fnt");
        return new BitmapFont(handle, false);
    }

    public static BitmapFont getFont(){
        FileHandle handle = Gdx.files.internal("fonts/Arial1.fnt");
        return new BitmapFont(handle, false);
    }

    public static Sprite getLogo(String logoName){
        return new Sprite(new Texture(Gdx.files.internal(RESOURCE_LOGO + logoName)));
    }

    public static Sprite getSplashImage(String imageName){
        return new Sprite(new Texture(Gdx.files.internal(RESOURCE_SPLASH + imageName)));
    }

    public static FileHandle[] getSaves(){
        FileHandle[] saves = Gdx.files.external(HOME).list();
        return saves;
    }
}
