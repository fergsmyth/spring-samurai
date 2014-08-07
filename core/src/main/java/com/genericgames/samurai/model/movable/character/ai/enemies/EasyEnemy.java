package com.genericgames.samurai.model.movable.character.ai.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.Factory;
import com.genericgames.samurai.model.SamuraiWorld;

public class EasyEnemy extends Enemy {

    private static final float DEFAULT_SPEED = 1.0f;
    private static final int DEFAULT_MAX_HEALTH = 70;
    private static final float DEFAULT_SKILL_LEVEL = 0.25f;

    public EasyEnemy(World world, float x, float y) {
        super(world, x, y);
        this.setSpeed(DEFAULT_SPEED);
        this.setMaxHealth(DEFAULT_MAX_HEALTH);
        this.setSkillLevel(DEFAULT_SKILL_LEVEL);
    }

    public static class EasyEnemyFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            EasyEnemy enemy = new EasyEnemy(samuraiWorld.getPhysicalWorld(), x, y);
            enemy.setPlayerAware(true);
            samuraiWorld.getEnemies().add(enemy);
        }
    }
}
