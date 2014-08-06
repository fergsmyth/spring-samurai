package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.genericgames.samurai.model.movable.character.ai.enemies.DifficultEnemy;
import com.genericgames.samurai.model.movable.character.ai.enemies.EasyEnemy;
import com.genericgames.samurai.model.movable.character.ai.enemies.MediumEnemy;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.movable.character.ai.NPC;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {

    private static Map<Class, Map<State, Animation>> animations;
    public static final float tileSize = 1f;
    public static Texture healthIcon;
    public static Texture swordIcon;
    public static Texture bowIcon;
    public static Texture conversationIcon;
    public static Texture cherryBlossom;
    public static Texture quiver;

    private static final float IDLE_FRAME_DURATION = 1f;
    private static final int NUM_IDLE_FRAMES = 1;
    private static final float RUNNING_FRAME_DURATION = 6f;
    private static final int NUM_RUNNING_FRAMES = 4;
    private static final float LIGHT_ATTACK_FRAME_DURATION = 2f;
    private static final int NUM_LIGHT_ATTACK_FRAMES = 4;
    private static final float ENEMY_HEAVY_ATTACK_FRAME_DURATION = 2f;
    private static final int ENEMY_NUM_HEAVY_ATTACK_FRAMES = 4;
    private static final float HEAVY_ATTACK_FRAME_DURATION = 4f;
    private static final int NUM_HEAVY_ATTACK_FRAMES = 4;
    private static final float DEAD_FRAME_DURATION = 1f;
    private static final int NUM_DEAD_FRAMES = 1;
    private static final float CHARGING_FRAME_DURATION = 1f;
    private static final int NUM_CHARGING_FRAMES = 1;
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
        healthIcon = new Texture(Gdx.files.internal("resources/hud/health-new.png"));
        swordIcon = new Texture(Gdx.files.internal("resources/hud/sword.png"));
        bowIcon = new Texture(Gdx.files.internal("resources/hud/bow.png"));
        conversationIcon = new Texture(Gdx.files.internal("resources/icon/speechBubble.png"));
        cherryBlossom = new Texture(Gdx.files.internal("resources/image/cherryBlossom.png"));
        quiver = new Texture(Gdx.files.internal("resources/image/quiver.png"));
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("animations/pack/animations.pack"));

        loadPlayerAnimations(atlas);

        loadEnemy1Animations(atlas);
        loadEnemy2Animations(atlas);
        loadEnemy3Animations(atlas);

        loadNPCAnimations(atlas);
	}

    private static void loadNPCAnimations(TextureAtlas atlas) {
        Class<NPC> npcClass = NPC.class;
        String filePrefix = "samurai";

        animations.put(npcClass, new HashMap<State, Animation>());
        loadIdleAnimation(filePrefix, npcClass, atlas);
    }

    private static void loadPlayerAnimations(TextureAtlas atlas) {
        Class<PlayerCharacter> playerCharacterClass = PlayerCharacter.class;
        String filePrefix = "samurai";

        animations.put(playerCharacterClass, new HashMap<State, Animation>());
        loadIdleAnimation(filePrefix, playerCharacterClass, atlas);
        loadBlockAnimation(filePrefix, playerCharacterClass, atlas);
        loadRunningAnimation(filePrefix, playerCharacterClass, atlas);
        loadLightAttackAnimation(filePrefix, playerCharacterClass, atlas);
        loadHeavyAttackAnimation(filePrefix, playerCharacterClass, atlas);
        loadChargingAnimation(filePrefix, playerCharacterClass, atlas);
        loadChargedAnimation(filePrefix, playerCharacterClass, atlas);
        loadDodgeAnimation(filePrefix, playerCharacterClass, atlas);
        loadDeadAnimation(filePrefix, playerCharacterClass, atlas);
        loadKnockBackAnimation(filePrefix, playerCharacterClass, atlas);
    }

    private static void loadEnemy1Animations(TextureAtlas atlas) {
        Class<EasyEnemy> enemyClass = EasyEnemy.class;
        String filePrefix = "Enemy1";

        animations.put(enemyClass, new HashMap<State, Animation>());
        loadIdleAnimation(filePrefix, enemyClass, atlas);
        loadRunningAnimation(filePrefix, enemyClass, atlas);
        loadDeadAnimation(filePrefix, enemyClass, atlas);
        loadBlockAnimation(filePrefix, enemyClass, atlas);
        loadDodgeAnimation(filePrefix, enemyClass, atlas);
        loadHeavyTelegraphAnimation(filePrefix, enemyClass, atlas);
        loadHeavyAttackAnimation(filePrefix, enemyClass, atlas);
        loadLightTelegraphAnimation(filePrefix, enemyClass, atlas);
        loadLightAttackAnimation(filePrefix, enemyClass, atlas);
        loadKnockBackAnimation(filePrefix, enemyClass, atlas);
    }

    private static void loadEnemy2Animations(TextureAtlas atlas) {
        Class<MediumEnemy> enemyClass = MediumEnemy.class;
        String filePrefix = "Enemy2";

        animations.put(enemyClass, new HashMap<State, Animation>());
        loadIdleAnimation(filePrefix, enemyClass, atlas);
        loadRunningAnimation(filePrefix, enemyClass, atlas);
        loadDeadAnimation(filePrefix, enemyClass, atlas);
        loadBlockAnimation(filePrefix, enemyClass, atlas);
        loadDodgeAnimation(filePrefix, enemyClass, atlas);
        loadHeavyTelegraphAnimation(filePrefix, enemyClass, atlas);
        loadHeavyAttackAnimation(filePrefix, enemyClass, atlas);
        loadLightTelegraphAnimation(filePrefix, enemyClass, atlas);
        loadLightAttackAnimation(filePrefix, enemyClass, atlas);
        loadKnockBackAnimation(filePrefix, enemyClass, atlas);
    }

    private static void loadEnemy3Animations(TextureAtlas atlas) {
        Class<DifficultEnemy> enemyClass = DifficultEnemy.class;
        String filePrefix = "Enemy3";

        animations.put(enemyClass, new HashMap<State, Animation>());
        loadIdleAnimation(filePrefix, enemyClass, atlas);
        loadRunningAnimation(filePrefix, enemyClass, atlas);
        loadDeadAnimation(filePrefix, enemyClass, atlas);
        loadBlockAnimation(filePrefix, enemyClass, atlas);
        loadDodgeAnimation(filePrefix, enemyClass, atlas);
        loadHeavyTelegraphAnimation(filePrefix, enemyClass, atlas);
        loadHeavyAttackAnimation(filePrefix, enemyClass, atlas);
        loadLightTelegraphAnimation(filePrefix, enemyClass, atlas);
        loadLightAttackAnimation(filePrefix, enemyClass, atlas);
        loadKnockBackAnimation(filePrefix, enemyClass, atlas);
    }

    private static void loadChargedAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_CHARGED_FRAMES];
        for (int i = 0; i < NUM_CHARGED_FRAMES; i++) {
            frames[i] = atlas.findRegion(filePrefix+"-charged-0" + (i+1));
        }
        animations.get(clazz).put(State.CHARGED, new Animation(CHARGED_FRAME_DURATION, frames));
    }

    private static void loadChargingAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_CHARGING_FRAMES];
        for (int i = 0; i < NUM_CHARGING_FRAMES; i++) {
            frames[i] = atlas.findRegion(filePrefix+"-idle-0" + (i+1));
        }
        animations.get(clazz).put(State.CHARGING, new Animation(CHARGING_FRAME_DURATION, frames));
    }

    private static void loadDodgeAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_DODGE_FRAMES];
        for (int i = 0; i < NUM_DODGE_FRAMES; i++) {
            frames[i] = atlas.findRegion(filePrefix+"-dodge-0" + (i+1));
        }
        animations.get(clazz).put(State.DODGE, new Animation(DODGE_FRAME_DURATION, frames));
    }

    private static void loadIdleAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] idleFrames = new TextureRegion[NUM_IDLE_FRAMES];
        for (int i = 0; i < NUM_IDLE_FRAMES; i++) {
            idleFrames[i] = atlas.findRegion(filePrefix+"-idle-0" + (i+1));
        }
        animations.get(clazz).put(State.IDLE, new Animation(IDLE_FRAME_DURATION, idleFrames));
    }

    private static void loadBlockAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_BLOCK_FRAMES];
        for (int i = 0; i < NUM_BLOCK_FRAMES; i++) {
            frames[i] = atlas.findRegion(filePrefix+"-block-0" + (i+1));
        }
        animations.get(clazz).put(State.BLOCKING, new Animation(BLOCK_FRAME_DURATION, frames));
    }

    private static void loadLightAttackAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] lightAttackFrames = new TextureRegion[NUM_LIGHT_ATTACK_FRAMES];
        for (int i = 0; i < NUM_LIGHT_ATTACK_FRAMES; i++) {
            lightAttackFrames[i] = atlas.findRegion(filePrefix+"-lightAttack-0" + (i+1));
        }
        animations.get(clazz).put(State.LIGHT_ATTACKING, new Animation(LIGHT_ATTACK_FRAME_DURATION, lightAttackFrames));
    }

    private static void loadHeavyAttackAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] heavyAttackFrames = new TextureRegion[NUM_HEAVY_ATTACK_FRAMES];
        for (int i = 0; i < NUM_HEAVY_ATTACK_FRAMES; i++) {
            heavyAttackFrames[i] = atlas.findRegion(filePrefix+"-heavyAttack-0" + (i+1));
        }
        animations.get(clazz).put(State.HEAVY_ATTACKING, new Animation(HEAVY_ATTACK_FRAME_DURATION, heavyAttackFrames));
    }

    private static void loadRunningAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] walkFrames = new TextureRegion[NUM_RUNNING_FRAMES];
        for (int i = 0; i < NUM_RUNNING_FRAMES; i++) {
            walkFrames[i] = atlas.findRegion(filePrefix+"-walk-0" + (i+1));
        }
        animations.get(clazz).put(State.WALKING, new Animation(RUNNING_FRAME_DURATION, walkFrames));
    }

    private static void loadDeadAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] deadFrames = new TextureRegion[NUM_DEAD_FRAMES];
        for (int i = 0; i < NUM_DEAD_FRAMES; i++) {
            deadFrames[i] = atlas.findRegion(filePrefix+"-dead-0" + (i+1));
        }
        animations.get(clazz).put(State.DEAD, new Animation(DEAD_FRAME_DURATION, deadFrames));
    }

    private static void loadKnockBackAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] knockBackFrames = new TextureRegion[NUM_KNOCKBACK_FRAMES];
        for (int i = 0; i < NUM_KNOCKBACK_FRAMES; i++) {
            knockBackFrames[i] = atlas.findRegion(filePrefix+"-knockBack-0" + (i+1));
        }
        animations.get(clazz).put(State.KNOCKED_BACK, new Animation(KNOCKBACK_FRAME_DURATION, knockBackFrames));
    }

    private static void loadLightTelegraphAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_LIGHT_TELEGRAPH_FRAMES];
        for (int i = 0; i < NUM_CHARGED_FRAMES; i++) {
            frames[i] = atlas.findRegion(filePrefix+"-lightTelegraph-0" + (i+1));
        }
        animations.get(clazz).put(State.TELEGRAPHING_LIGHT_ATTACK, new Animation(LIGHT_TELEGRAPH_FRAME_DURATION, frames));
    }

    private static void loadHeavyTelegraphAnimation(String filePrefix, Class clazz, TextureAtlas atlas) {
        TextureRegion[] frames = new TextureRegion[NUM_HEAVY_TELEGRAPH_FRAMES];
        for (int i = 0; i < NUM_HEAVY_TELEGRAPH_FRAMES; i++) {
            frames[i] = atlas.findRegion(filePrefix+"-heavyTelegraph-0" + (i+1));
        }
        animations.get(clazz).put(State.TELEGRAPHING_HEAVY_ATTACK, new Animation(HEAVY_TELEGRAPH_FRAME_DURATION, frames));
    }

    public static Map<Class, Map<State, Animation>> getAnimations() {
        if(animations == null){
            load();
        }
        return animations;
    }
}
