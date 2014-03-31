package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.movable.State;

public class Attack {

    private int attackDuration;
    private int recoveryDuration;
    private int strength;
    private State state;

    public Attack(int attackDuration, int recoveryDuration, int strength, State state){
        this.attackDuration = attackDuration;
        this.recoveryDuration = recoveryDuration;
        this.strength = strength;
        this.state = state;
    }

    public int getAttackDuration() {
        return attackDuration;
    }

    public void setAttackDuration(int attackDuration) {
        this.attackDuration = attackDuration;
    }

    public int getRecoveryDuration() {
        return recoveryDuration;
    }

    public void setRecoveryDuration(int recoveryDuration) {
        this.recoveryDuration = recoveryDuration;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getInflictionFrame(){
        return getAttackDuration();
    }

    public int getTotalDuration(){
        return getAttackDuration()+getRecoveryDuration();
    }
}
