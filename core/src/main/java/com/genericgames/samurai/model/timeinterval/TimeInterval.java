package com.genericgames.samurai.model.timeinterval;

public abstract class TimeInterval {
    private int timeCounter = 0;

    public abstract boolean hasIntervalEnded();

    public int getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(int timeCounter) {
        this.timeCounter = timeCounter;
    }

    public void reinitialise() {
        resetTimeCounter();
    }

    public void resetTimeCounter() {
        timeCounter = 0;
    }
}
