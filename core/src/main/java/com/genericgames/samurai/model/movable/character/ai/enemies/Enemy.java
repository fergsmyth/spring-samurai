package com.genericgames.samurai.model.movable.character.ai.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformerProvider;
import com.genericgames.samurai.combat.TelegraphedAttack;
import com.genericgames.samurai.model.arena.ArenaLevelAttributes;
import com.genericgames.samurai.model.Factory;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.utility.ImageCache;

import java.util.Map;

public class Enemy extends Combatable {

    private static final float DEFAULT_SPEED = 1.5f;
    private float skillLevel = 1.0f;

    public Enemy(World world, float x, float y){
        super(x, y);
        body = PhysicalWorldFactory.createEnemy(this, world);
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new TelegraphedAttack(45, 8, 20, 30, State.LIGHT_ATTACKING));
        this.addAttack(new TelegraphedAttack(45, 8, 20, 50, State.HEAVY_ATTACKING));
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if(!isInvincible() || (getInvincibilityCounter()%10<5)){
            float tileSize = ImageCache.tileSize;
            Map<State, Animation> animationMap = ImageCache.getAnimations().get(getClass());
            TextureRegion texture = animationMap.get(getState()).getKeyFrame(getStateTime(),
                    getState().isLoopingState());

            batch.draw(texture, getX()-(tileSize/2), getY()-(tileSize/2),
                    0.5f,  0.5f, tileSize, tileSize, 1, 1, getRotationInDegrees());
        }
    }

    @Override
    public String debugInfo() {
        return "Enemy\nPos x: "+ getX() +"\nPos y : " + getY();
    }

    public void damage(int damage, SamuraiWorld samuraiWorld){
        super.damage(damage, samuraiWorld);
        if(this.getHealth() > 0 && damage > 0){
            setAIActionPerformer(AIActionPerformerProvider.getActionPerformer(ActionState.KNOCK_BACK, this));
        }
        setPlayerAware(true);
    }

    public static class EnemyFactory implements Factory {

        @Override
        public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity) {
            Enemy enemy = new Enemy(samuraiWorld.getPhysicalWorld(), x, y);
            enemy.setPlayerAware(true);
            samuraiWorld.getEnemies().add(enemy);
        }
    }

    @Override
    protected void kill(SamuraiWorld samuraiWorld) {
        super.kill(samuraiWorld);
        ArenaLevelAttributes arenaLevelAttributes = samuraiWorld.getCurrentLevel().getArenaLevelAttributes();
        if(arenaLevelAttributes.isArenaLevel()){
            arenaLevelAttributes.incrementEnemiesKilledCounter();
        }
    }

    public void drawHealthBar(ShapeRenderer renderer, SpriteBatch batch){
        float healthBarToIconWidthRatio = 0.05f;
        if (isAlive()) {
            renderer.setColor(0, 0, 0, 1);
            renderer.rect(getX() - (ImageCache.tileSize * 0.75f / 2), getY() + (ImageCache.tileSize * 0.75f), ImageCache.tileSize * 0.75f , healthBarToIconWidthRatio);
            renderer.setColor(1, 0, 0, 1);
            renderer.rect(getX() - (ImageCache.tileSize * 0.75f / 2), getY() + (ImageCache.tileSize * 0.75f), ((ImageCache.tileSize * 0.75f) * health) / maxHealth, healthBarToIconWidthRatio);
        }
    }
    public float getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(float skillLevel) {
        this.skillLevel = skillLevel;
    }
}
