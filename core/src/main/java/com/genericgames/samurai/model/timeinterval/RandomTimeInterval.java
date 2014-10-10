package com.genericgames.samurai.model.timeinterval;

import java.util.Random;

public class RandomTimeInterval extends TimeInterval {

    private int minInterval = 0;
    private int maxInterval = 1000;
    private Random random = new Random();

    public RandomTimeInterval(int minInterval, int maxInterval){
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
    }

    @Override
    public boolean hasIntervalEnded() {
        boolean intervalHasEnded = getTimeCounter() > getTimeCounter();

        if(intervalHasEnded){
            resetTimeCounter();
            getNextTimeInterval();
        }
        return intervalHasEnded;
    }

    private void getNextTimeInterval() {
        setTimeCounter(minInterval + random.nextInt(maxInterval-minInterval));
    }
}
