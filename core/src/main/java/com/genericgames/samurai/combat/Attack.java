package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.movable.State;

public class Attack {

    private float duration;
    private float chargeDuration;
    private int strength;
    private State state;

    public Attack(float duration, float chargeDuration, int strength, State state){
        this.duration = duration;
        this.chargeDuration = chargeDuration;
        this.strength = strength;
        this.state = state;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
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

    public float getChargeDuration() {
        return chargeDuration;
    }

    public void setChargeDuration(float chargeDuration) {
        this.chargeDuration = chargeDuration;
    }
}
