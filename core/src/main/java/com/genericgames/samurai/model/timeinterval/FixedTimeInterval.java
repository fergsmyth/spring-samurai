package com.genericgames.samurai.model.timeinterval;

public class FixedTimeInterval extends TimeInterval {

    private int timeInterval = 1000;

    public FixedTimeInterval(int timeInterval){
        this.timeInterval = timeInterval;
    }

    @Override
    public boolean hasIntervalEnded() {
        boolean intervalHasEnded = getTimeCounter() % timeInterval == 0;
        return intervalHasEnded;
    }
}
