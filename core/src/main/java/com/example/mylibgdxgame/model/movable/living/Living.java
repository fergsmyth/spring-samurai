package com.example.mylibgdxgame.model.movable.living;

import com.example.mylibgdxgame.model.movable.Movable;

/**
 * Any creature or character with health, i.e. it can die.
 */
public abstract class Living extends Movable {

    //Used when no max health is explicitly specified.
    private static final int DEFAULT_MAX_HEALTH = 100;

    private int health;
    private int maxHealth;

    public Living() {
        super();
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.health = maxHealth;
    }

    public Living(int maxHealth) {
        super();
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public Living(int positonX, int positonY, int direction) {
        super(positonX, positonY, direction);
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.health = maxHealth;
    }

    public Living(int maxHealth, int positonX, int positonY, int direction) {
        super(positonX, positonY, direction);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public Living(int positonX, int positonY, int direction, int velocityX, int velocityY) {
        super(positonX, positonY, direction, velocityX, velocityY);
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.health = maxHealth;
    }

    public Living(int maxHealth, int positonX, int positonY, int direction, int velocityX, int velocityY) {
        super(positonX, positonY, direction, velocityX, velocityY);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void damage(int damage){
        health = health - damage;
        if(health < 0){
            health = 0;
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
}
