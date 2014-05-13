package com.genericgames.samurai.model.state.living;

import com.genericgames.samurai.model.movable.character.ai.Conversable;
import com.genericgames.samurai.model.state.State;

/**
 * Any creature or character with health, i.e. it can die.
 */
public abstract class Living extends Conversable {

    //Used when no max health is explicitly specified.
	private static final int DEFAULT_MAX_HEALTH = 100;

    private int health;
    private int maxHealth;


    public Living() {
        super();
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.health = maxHealth;
    }

    public void damage(int damage){
        setStateTime(0);
        health = health - damage;
        if(health <= 0){
            health = 0;
            setState(State.DEAD);
        }
        else if(damage > 0){
            setState(State.KNOCKED_BACK);
        }
    }

    public void heal(int amount){
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
        return health;
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
}
