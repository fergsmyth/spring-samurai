package com.genericgames.samurai.model.state.living;

import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.Stateful;
import com.genericgames.samurai.model.state.StatefulImpl;

/**
 * Any creature or character with health, i.e. it can die.
 */
public class LivingImpl implements Living {

    //Used when no max health is explicitly specified.
	private static final int DEFAULT_MAX_HEALTH = 100;

    private int health;
    private int maxHealth;

    private Stateful stateful;

    public LivingImpl() {
        super();
        stateful = new StatefulImpl();
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
        else {
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

    public State getState() {
        return stateful.getState();
    }

    public void setState(State state) {
        stateful.setState(state);
    }

    public float getStateTime() {
        return stateful.getStateTime();
    }

    public void setStateTime(float stateTime) {
        stateful.setStateTime(stateTime);
    }

    public void incrementStateTime() {
        stateful.incrementStateTime();
    }
}
