package com.genericgames.samurai.model.timeinterval;

import java.util.Random;

public class RandomTimeInterval extends TimeInterval {

    private int currentTimeInterval = 0;
    private int minInterval = 0;
    private int maxInterval = 1000;
    private Random random = new Random();

    public RandomTimeInterval(int minInterval, int maxInterval){
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
    }

    @Override
    public boolean hasIntervalEnded() {
        boolean intervalHasEnded = getTimeCounter() > currentTimeInterval;

        if(intervalHasEnded){
            resetTimeCounter();
            getNextTimeInterval();
        }
        setTimeCounter(getTimeCounter()+1);
        return intervalHasEnded;
    }

    private void getNextTimeInterval() {
        currentTimeInterval = minInterval + random.nextInt(maxInterval-minInterval);
    }
}
