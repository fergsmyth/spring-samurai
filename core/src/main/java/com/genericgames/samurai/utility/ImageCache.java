package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.movable.living.ai.NPC;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static Map<Class, Map<State, Animation>> animations;

    public static Texture heartTexture;
    public static Texture conversationIcon;

    private static final float IDLE_FRAME_DURATION = 1f;
    private static final int NUM_IDLE_FRAMES = 1;
    private static final float RUNNING_FRAME_DURATION = 6f;
    private static final int NUM_RUNNING_FRAMES = 4;
    private static final float LIGHT_ATTACK_FRAME_DURATION = 2f;
    private static final int NUM_LIGHT_ATTACK_FRAMES = 4;
    private static final float DEAD_FRAME_DURATION = 1f;
    private static final int NUM_DEAD_FRAMES = 1;
    private static final float CHARGING_FRAME_DURATION = 6f;
    private static final int NUM_CHARGING_FRAMES = 3;
    private static final float CHARGED_FRAME_DURATION = 6f;
    private static final int NUM_CHARGED_FRAMES = 3;
    private static final float LIGHT_TELEGRAPH_FRAME_DURATION = 6f;
    private static final int NUM_LIGHT_TELEGRAPH_FRAMES = 3;
    private static final float HEAVY_TELEGRAPH_FRAME_DURATION = 6f;
    private static final int NUM_HEAVY_TELEGRAPH_FRAMES = 3;
    private static final float BLOCK_FRAME_DURATION = 1f;
    private static final int NUM_BLOCK_FRAMES = 1;
    private static final float DODGE_FRAME_DURATION = 6f;
    private static final int NUM_DODGE_FRAMES = 3;
    private static final float KNOCKBACK_FRAME_DURATION = 1f;
    private static final int NUM_KNOCKBACK_FRAMES = 1;

	public static void load () {
        animations = new HashMap<Class, Map<State, Animation>>();
        heartTexture = new Texture(Gdx.files.internal("resources/hud/heart.png"));
        conversationIcon = new Texture(Gdx.files.internal("resources/icon/speechBubble.png"));
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("animations/pack/animations.pack"));

        animations.put(PlayerCharacter.class, new HashMap<State, Animation>());
        loadIdleAnimation(atlas);
        loadBlockAnimation(atlas);
        loadRunningAnimation(atlas);
        loadLightAttackAnimation(atlas);
        loadHeavyAttackAnimation(atlas);
        loadChargingAnimation(atlas);
        loadChargedAnimation(atlas);
        loadDodgeAnimation(atlas);
        loadDeadAnimation(atlas);
        loadKnockBackAnimation(atlas);

        animations.put(Enemy.class, new HashMap<State, Animation>());
        loadEnemy1IdleAnimation(atlas);
        loadEnemy1RunningAnimation(atlas);
        loadEnemy1DeadAnimation(atlas);
        loadEnemy1BlockAnimation(atlas);
        loadEnemy1DodgeAnimation(atlas);
        loadEnemy1HeavyTelegraphAnimation(atlas);
        loadEnemy1HeavyAttackAnimation(atlas);
        loadEnemy1LightTelegraphAnimation(atlas);
        loadEnemy1LightAttackAnimation(atlas);
        loadEnemy1KnockBackAnimation(atlas);

        animations.put(NPC.class, new HashMap<State, Animation>());
        loadNPCIdleAnimation(atlas);
	}

    private static void loadChargedAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_CHARGED_FRAMES];
        for (int i = 0; i < NUM_CHARGED_FRAMES; i++) {
            frames[i] = atlas.findRegion("samurai-charged-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.CHARGED, new Animation(CHARGED_FRAME_DURATION, frames));
    }

    private static void loadChargingAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_CHARGING_FRAMES];
        for (int i = 0; i < NUM_CHARGING_FRAMES; i++) {
            frames[i] = atlas.findRegion("samurai-charging-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.CHARGING, new Animation(CHARGING_FRAME_DURATION, frames));
    }

    private static void loadDodgeAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_DODGE_FRAMES];
        for (int i = 0; i < NUM_DODGE_FRAMES; i++) {
            frames[i] = atlas.findRegion("samurai-dodge-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.DODGE, new Animation(DODGE_FRAME_DURATION, frames));
    }

    private static void loadIdleAnimation(TextureAtlas atlas) {
        TextureRegion[] idleFrames = new TextureRegion[NUM_IDLE_FRAMES];
        for (int i = 0; i < NUM_IDLE_FRAMES; i++) {
            idleFrames[i] = atlas.findRegion("samurai-idle-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.IDLE, new Animation(IDLE_FRAME_DURATION, idleFrames));
    }

    private static void loadBlockAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_BLOCK_FRAMES];
        for (int i = 0; i < NUM_BLOCK_FRAMES; i++) {
            frames[i] = atlas.findRegion("samurai-block-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.BLOCKING, new Animation(BLOCK_FRAME_DURATION, frames));
    }

    private static void loadLightAttackAnimation(TextureAtlas atlas) {
        TextureRegion[] lightAttackFrames = new TextureRegion[NUM_LIGHT_ATTACK_FRAMES];
        for (int i = 0; i < NUM_LIGHT_ATTACK_FRAMES; i++) {
            lightAttackFrames[i] = atlas.findRegion("samurai-lightAttack-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.LIGHT_ATTACKING, new Animation(LIGHT_ATTACK_FRAME_DURATION, lightAttackFrames));
    }

    private static void loadHeavyAttackAnimation(TextureAtlas atlas) {
        TextureRegion[] lightAttackFrames = new TextureRegion[NUM_LIGHT_ATTACK_FRAMES];
        for (int i = 0; i < NUM_LIGHT_ATTACK_FRAMES; i++) {
            lightAttackFrames[i] = atlas.findRegion("samurai-lightAttack-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.HEAVY_ATTACKING, new Animation(LIGHT_ATTACK_FRAME_DURATION, lightAttackFrames));
    }

    private static void loadRunningAnimation(TextureAtlas atlas) {
        TextureRegion[] walkFrames = new TextureRegion[NUM_RUNNING_FRAMES];
        for (int i = 0; i < NUM_RUNNING_FRAMES; i++) {
            walkFrames[i] = atlas.findRegion("samurai-walk-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.WALKING, new Animation(RUNNING_FRAME_DURATION, walkFrames));
    }

    private static void loadDeadAnimation(TextureAtlas atlas) {
        TextureRegion[] deadFrames = new TextureRegion[NUM_DEAD_FRAMES];
        for (int i = 0; i < NUM_DEAD_FRAMES; i++) {
            deadFrames[i] = atlas.findRegion("samurai-dead-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.DEAD, new Animation(DEAD_FRAME_DURATION, deadFrames));
    }

    private static void loadKnockBackAnimation(TextureAtlas atlas) {
        TextureRegion[] knockBackFrames = new TextureRegion[NUM_KNOCKBACK_FRAMES];
        for (int i = 0; i < NUM_KNOCKBACK_FRAMES; i++) {
            knockBackFrames[i] = atlas.findRegion("samurai-knockBack-0" + (i+1));
        }
        animations.get(PlayerCharacter.class).put(State.KNOCKED_BACK, new Animation(KNOCKBACK_FRAME_DURATION, knockBackFrames));
    }

    private static void loadEnemy1IdleAnimation(TextureAtlas atlas) {
        TextureRegion[] idleFrames = new TextureRegion[NUM_IDLE_FRAMES];
        for (int i = 0; i < NUM_IDLE_FRAMES; i++) {
            idleFrames[i] = atlas.findRegion("Enemy1-idle-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.IDLE, new Animation(IDLE_FRAME_DURATION, idleFrames));
    }

    private static void loadNPCIdleAnimation(TextureAtlas atlas) {
        TextureRegion[] idleFrames = new TextureRegion[NUM_IDLE_FRAMES];
        for (int i = 0; i < NUM_IDLE_FRAMES; i++) {
            idleFrames[i] = atlas.findRegion("samurai-idle-0" + (i+1));
        }
        animations.get(NPC.class).put(State.IDLE, new Animation(IDLE_FRAME_DURATION, idleFrames));
    }

    private static void loadEnemy1RunningAnimation(TextureAtlas atlas) {
        TextureRegion[] walkFrames = new TextureRegion[NUM_RUNNING_FRAMES];
        for (int i = 0; i < NUM_RUNNING_FRAMES; i++) {
            walkFrames[i] = atlas.findRegion("Enemy1-walk-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.WALKING, new Animation(RUNNING_FRAME_DURATION, walkFrames));
    }

    private static void loadEnemy1DeadAnimation(TextureAtlas atlas) {
        TextureRegion[] deadFrames = new TextureRegion[NUM_DEAD_FRAMES];
        for (int i = 0; i < NUM_DEAD_FRAMES; i++) {
            deadFrames[i] = atlas.findRegion("Enemy1-dead-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.DEAD, new Animation(DEAD_FRAME_DURATION, deadFrames));
    }

    private static void loadEnemy1BlockAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_BLOCK_FRAMES];
        for (int i = 0; i < NUM_BLOCK_FRAMES; i++) {
            frames[i] = atlas.findRegion("Enemy1-block-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.BLOCKING, new Animation(BLOCK_FRAME_DURATION, frames));
    }

    private static void loadEnemy1DodgeAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_DODGE_FRAMES];
        for (int i = 0; i < NUM_DODGE_FRAMES; i++) {
            frames[i] = atlas.findRegion("Enemy1-dodge-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.DODGE, new Animation(DODGE_FRAME_DURATION, frames));
    }

    private static void loadEnemy1LightTelegraphAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_LIGHT_TELEGRAPH_FRAMES];
        for (int i = 0; i < NUM_CHARGED_FRAMES; i++) {
            frames[i] = atlas.findRegion("Enemy1-lightTelegraph-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.TELEGRAPHING_LIGHT_ATTACK, new Animation(LIGHT_TELEGRAPH_FRAME_DURATION, frames));
    }

    private static void loadEnemy1HeavyTelegraphAnimation(TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_HEAVY_TELEGRAPH_FRAMES];
        for (int i = 0; i < NUM_CHARGING_FRAMES; i++) {
            frames[i] = atlas.findRegion("Enemy1-heavyTelegraph-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.TELEGRAPHING_HEAVY_ATTACK, new Animation(HEAVY_TELEGRAPH_FRAME_DURATION, frames));
    }

    private static void loadEnemy1LightAttackAnimation(TextureAtlas atlas) {
        TextureRegion[] lightAttackFrames = new TextureRegion[NUM_LIGHT_ATTACK_FRAMES];
        for (int i = 0; i < NUM_LIGHT_ATTACK_FRAMES; i++) {
            lightAttackFrames[i] = atlas.findRegion("Enemy1-lightAttack-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.LIGHT_ATTACKING, new Animation(LIGHT_ATTACK_FRAME_DURATION, lightAttackFrames));
    }

    private static void loadEnemy1HeavyAttackAnimation(TextureAtlas atlas) {
        TextureRegion[] lightAttackFrames = new TextureRegion[NUM_LIGHT_ATTACK_FRAMES];
        for (int i = 0; i < NUM_LIGHT_ATTACK_FRAMES; i++) {
            lightAttackFrames[i] = atlas.findRegion("Enemy1-lightAttack-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.HEAVY_ATTACKING, new Animation(LIGHT_ATTACK_FRAME_DURATION, lightAttackFrames));
    }

    private static void loadEnemy1KnockBackAnimation(TextureAtlas atlas) {
        TextureRegion[] deadFrames = new TextureRegion[NUM_KNOCKBACK_FRAMES];
        for (int i = 0; i < NUM_KNOCKBACK_FRAMES; i++) {
            deadFrames[i] = atlas.findRegion("Enemy1-knockBack-0" + (i+1));
        }
        animations.get(Enemy.class).put(State.KNOCKED_BACK, new Animation(KNOCKBACK_FRAME_DURATION, deadFrames));
    }

    public static Map<Class, Map<State, Animation>> getAnimations() {
        return animations;
    }
}
