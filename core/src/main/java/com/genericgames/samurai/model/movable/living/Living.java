package com.genericgames.samurai.model.movable.living;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.movable.Movable;
import com.genericgames.samurai.model.movable.State;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Any creature or character with health, i.e. it can die.
 */
public abstract class Living extends Movable {

    //Used when no max health is explicitly specified.
	private static final int DEFAULT_MAX_HEALTH = 100;

    private int health;
    private int maxHealth;

    private Collection<Attack> attacks = new ArrayList<Attack>();

    public Living() {
        super();
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.health = maxHealth;
    }

    public void damage(int damage){
        health = health - damage;
        if(health <= 0){
            health = 0;
            setState(State.DEAD);
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

	@Override
	public void setPositionX(float positionX) {
		super.setPositionX(positionX);
	}

	@Override
	public void setPositionY(float positionY) {
		super.setPositionY(positionY);
	}

    public Collection<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(Collection<Attack> attacks) {
        this.attacks = attacks;
    }

    public void addAttack(Attack attack){
        this.attacks.add(attack);
    }
}
