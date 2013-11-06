package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ImageCache {

	public static TextureRegion playerCharacterTexture;
    public static Texture grassTexture;
    public static Texture wallTexture;
    public static Texture castleTexture;

	private static final float RUNNING_FRAME_DURATION = 6f;

	public static void load (WorldRenderer worldRenderer) {
        grassTexture = new  Texture(Gdx.files.internal("grass-01.png"));
        wallTexture = new  Texture(Gdx.files.internal("wall.png"));
        castleTexture = new  Texture(Gdx.files.internal("castle-02.png"));

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("textures/texture.pack"));

		playerCharacterTexture = atlas.findRegion("samurai");

		TextureRegion[] walkFrames = new TextureRegion[4];
		for (int i = 0; i < 4; i++) {
			walkFrames[i] = atlas.findRegion("samurai-walk-0" + (i+1));
		}
		worldRenderer.setWalkAnimation(new Animation(RUNNING_FRAME_DURATION, walkFrames));


	}
}
