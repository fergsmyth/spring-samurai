package com.genericgames.samurai.model.movable.character.ai.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.Factory;
import com.genericgames.samurai.model.SamuraiWorld;

public class DifficultEnemy extends Enemy {

    private static final float DEFAULT_SPEED = 2f;
    private static final int DEFAULT_MAX_HEALTH = 150;
    private static final float DEFAULT_SKILL_LEVEL = 1.0f;

    public DifficultEnemy(World world, float x, float y) {
        super(world, x, y);
        this.setSpeed(DEFAULT_SPEED);
        this.setMaxHealth(DEFAULT_MAX_HEALTH);
        this.setSkillLevel(DEFAULT_SKILL_LEVEL);
    }

    public static class DifficultEnemyFactory implements Factory {
        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            DifficultEnemy enemy = new DifficultEnemy(samuraiWorld.getPhysicalWorld(), x, y);
            enemy.setPlayerAware(true);
            samuraiWorld.getEnemies().add(enemy);
        }
    }
}
