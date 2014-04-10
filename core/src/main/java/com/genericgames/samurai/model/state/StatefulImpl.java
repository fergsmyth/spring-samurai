package com.genericgames.samurai.model.state;

public class StatefulImpl implements Stateful {

    private float stateTime = 0;
    private State state = State.IDLE;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void incrementStateTime() {
        stateTime = stateTime + 1;
    }
}
