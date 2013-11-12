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
    public static Texture roofTexture;
    public static Texture doorTexture;
    public static Texture chestTexture;

    private static final float RUNNING_FRAME_DURATION = 6f;
    private static final int NUM_RUNNING_FRAMES = 4;
    private static final float LIGHT_ATTACK_FRAME_DURATION = 5f;
    private static final int NUM_LIGHT_ATTACK_FRAMES = 4;

	public static void load (WorldRenderer worldRenderer) {
        grassTexture = new  Texture(Gdx.files.internal("grass-01.png"));
        wallTexture = new  Texture(Gdx.files.internal("wall.png"));
        castleTexture = new  Texture(Gdx.files.internal("castle-02.png"));
        doorTexture = new Texture(Gdx.files.internal("Door.png"));
        roofTexture = new Texture(Gdx.files.internal("Roof.png"));
        chestTexture = new Texture(Gdx.files.internal("Chest.png"));

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("animations/pack/animations.pack"));

        playerCharacterTexture = atlas.findRegion("samurai");

        loadRunningAnimation(worldRenderer, atlas);

        loadLightAttackAnimation(worldRenderer, atlas);
	}

    private static void loadLightAttackAnimation(WorldRenderer worldRenderer, TextureAtlas atlas) {
        TextureRegion[] lightAttackFrames = new TextureRegion[NUM_LIGHT_ATTACK_FRAMES];
        for (int i = 0; i < NUM_LIGHT_ATTACK_FRAMES; i++) {
            lightAttackFrames[i] = atlas.findRegion("samurai-lightAttack-0" + (i+1));
        }
        worldRenderer.setLightAttackAnimation(new Animation(LIGHT_ATTACK_FRAME_DURATION, lightAttackFrames));
    }

    private static void loadRunningAnimation(WorldRenderer worldRenderer, TextureAtlas atlas) {
        TextureRegion[] walkFrames = new TextureRegion[NUM_RUNNING_FRAMES];
        for (int i = 0; i < NUM_RUNNING_FRAMES; i++) {
            walkFrames[i] = atlas.findRegion("samurai-walk-0" + (i+1));
        }
        worldRenderer.setWalkAnimation(new Animation(RUNNING_FRAME_DURATION, walkFrames));
    }
}
