package com.genericgames.samurai.model.timeinterval;

public class FixedTimeInterval implements TimeInterval {

    private int timeInterval = 1000;
    private int timeCounter = 0;

    public FixedTimeInterval(int timeInterval){
        this.timeInterval = timeInterval;
    }

    @Override
    public boolean hasIntervalEnded() {
        boolean intervalHasEnded = timeCounter % timeInterval == 0;
        timeCounter++;
        return intervalHasEnded;
    }
}
