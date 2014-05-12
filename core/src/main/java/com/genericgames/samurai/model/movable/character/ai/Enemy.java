package com.genericgames.samurai.model.movable.character.ai;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformerProvider;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.TelegraphedAttack;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.model.state.living.combatable.CombatableImpl;
import com.genericgames.samurai.physics.PhysicalWorldFactory;
import com.genericgames.samurai.utility.ImageCache;

import java.util.Collection;
import java.util.Map;

public class Enemy extends Conversable implements Combatable {

    private static final float DEFAULT_SPEED = 1f;

    private Combatable combatable;

    public Enemy(World world, float x, float y){
        super();
        setPosition(x, y);
        body = PhysicalWorldFactory.createEnemy(this, world);
        combatable = new CombatableImpl();
        this.setSpeed(DEFAULT_SPEED);
        this.addAttack(new TelegraphedAttack(45, 8, 20, 30, State.LIGHT_ATTACKING));
        this.addAttack(new TelegraphedAttack(45, 8, 20, 50, State.HEAVY_ATTACKING));
    }

    @Override
    public void draw(SpriteBatch batch) {
        float tileSize = ImageCache.tileSize;
        Map<State, Animation> animationMap = ImageCache.getAnimations().get(getClass());
        TextureRegion texture = animationMap.get(getState()).getKeyFrame(getStateTime(),
                getState().isLoopingState());

        batch.draw(texture, getX()-(tileSize/2), getY()-(tileSize/2),
                0.5f,  0.5f, tileSize, tileSize, 1, 1, getRotationInDegrees());
    }

    @Override
    public String debugInfo() {
        return "Enemy\nPos x: "+ getX() +"\nPos y : " + getY();
    }

    public void damage(int damage){
        combatable.damage(damage);
        if(this.getHealth() > 0 && damage > 0){
            setAIActionPerformer(AIActionPerformerProvider.getActionPerformer(ActionState.KNOCK_BACK, this));
        }
        setPlayerAware(true);
    }

    @Override
    public void heal(int amount) {
        combatable.heal(amount);
    }

    @Override
    public void fullHeal() {
        combatable.fullHeal();
    }

    @Override
    public boolean isAlive() {
        return combatable.isAlive();
    }

    @Override
    public int getHealth() {
        return combatable.getHealth();
    }

    @Override
    public void setHealth(int health) {
        combatable.setHealth(health);
    }

    @Override
    public int getMaxHealth() {
        return combatable.getMaxHealth();
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        combatable.setMaxHealth(maxHealth);
    }

    @Override
    public Collection<Attack> getAttacks() {
        return combatable.getAttacks();
    }

    @Override
    public void setAttacks(Collection<Attack> attacks) {
        combatable.setAttacks(attacks);
    }

    @Override
    public void addAttack(Attack attack) {
        combatable.addAttack(attack);
    }

    public State getState() {
        return combatable.getState();
    }

    public void setState(State state) {
        combatable.setState(state);
    }

    public float getStateTime() {
        return combatable.getStateTime();
    }

    public void setStateTime(float stateTime) {
        combatable.setStateTime(stateTime);
    }

    public void incrementStateTime() {
        combatable.incrementStateTime();
    }
}
