package com.genericgames.samurai.model.timeinterval;

import java.util.Random;

public class RandomTimeInterval implements TimeInterval {

    private int currentTimeInterval = 0;
    private int timeCounter = 0;
    private int minInterval = 0;
    private int maxInterval = 1000;
    private Random random = new Random();

    public RandomTimeInterval(int minInterval, int maxInterval){
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
    }

    @Override
    public boolean hasIntervalEnded() {
        boolean intervalHasEnded = timeCounter > currentTimeInterval;

        if(intervalHasEnded){
            resetTimeCounter();
            getNextTimeInterval();
        }
        timeCounter++;
        return intervalHasEnded;
    }

    private void getNextTimeInterval() {
        currentTimeInterval = minInterval + random.nextInt(maxInterval-minInterval);
    }

    private void resetTimeCounter() {
        timeCounter = 0;
    }
}
