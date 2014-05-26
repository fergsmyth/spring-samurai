package com.genericgames.samurai.model.state.living;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.character.ai.Conversable;
import com.genericgames.samurai.model.state.State;

/**
 * Any creature or character with health, i.e. it can die.
 */
public abstract class Living extends Conversable {

    //Used when no max health is explicitly specified.
	private static final int DEFAULT_MAX_HEALTH = 100;

    private float health;
    private int maxHealth;
    private Invincibility invincibility;

    public Living() {
        super();
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.health = maxHealth;
        this.invincibility = new Invincibility();
    }

    public void damage(int damage, World world){
        if(!isInvincible()){
            if(damage > 0){
                setStateTime(0);
                health = health - damage;
                if(health <= 0){
                    health = 0;
                    kill(world);
                }
                else {
                    setState(State.KNOCKED_BACK);
                    invincibility.startInvincibilityPeriod();
                }
            }
        }
    }

    private void kill(World world) {
        setState(State.DEAD);
        deleteBody(world);
    }

    public void heal(float amount){
        health = health + amount;
        if(health>maxHealth){
            health = maxHealth;
        }
    }

    public void fullHeal(){
        health = maxHealth;
    }

    public boolean isAlive(){
        return health > 0;
    }

    public int getHealth() {
        return (int)health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public boolean isInvincible(){
        return invincibility.isInvincible();
    }

    public void incrementInvisibility(){
        invincibility.increment();
    }

    public int getInvincibilityCounter(){
        return invincibility.getCounter();
    }
}
