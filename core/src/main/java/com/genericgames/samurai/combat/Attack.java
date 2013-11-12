package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.movable.State;

public class Attack {

    private float duration;
    private int strength;
    private State state;

    public Attack(float duration, int strength, State state){
        this.duration = duration;
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
}
