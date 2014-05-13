package com.genericgames.samurai.model.movable.character;

import com.genericgames.samurai.model.movable.Movable;
import com.genericgames.samurai.model.state.State;

public abstract class WorldCharacter extends Movable {

    private float stateTime = 0;
    private State state = State.IDLE;

    public WorldCharacter(){
        super();
    }

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
