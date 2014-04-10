package com.genericgames.samurai.model.state.living.combatable;

import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.model.state.living.LivingImpl;
import com.genericgames.samurai.model.state.State;

import java.util.ArrayList;
import java.util.Collection;

public class CombatableImpl implements Combatable {

    private Collection<Attack> attacks = new ArrayList<Attack>();

    private Living living;

    public CombatableImpl(){
        living = new LivingImpl();
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

    @Override
    public void damage(int damage) {
        living.damage(damage);
    }

    @Override
    public void heal(int amount) {
        living.heal(amount);
    }

    @Override
    public void fullHeal() {
        living.fullHeal();
    }

    @Override
    public boolean isAlive() {
        return living.isAlive();
    }

    @Override
    public int getHealth() {
        return living.getHealth();
    }

    @Override
    public void setHealth(int health) {
        living.setHealth(health);
    }

    @Override
    public int getMaxHealth() {
        return living.getMaxHealth();
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        living.setMaxHealth(maxHealth);
    }

    public State getState() {
        return living.getState();
    }

    public void setState(State state) {
        living.setState(state);
    }

    public float getStateTime() {
        return living.getStateTime();
    }

    public void setStateTime(float stateTime) {
        living.setStateTime(stateTime);
    }

    public void incrementStateTime() {
        living.incrementStateTime();
    }
}
