package com.genericgames.samurai.model.state;

public interface Stateful {

    public State getState();

    public void setState(State state);

    public float getStateTime();

    public void setStateTime(float stateTime);

    public void incrementStateTime();
}
