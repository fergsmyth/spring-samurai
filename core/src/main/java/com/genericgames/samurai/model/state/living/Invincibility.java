package com.genericgames.samurai.model.state.living;

public class Invincibility {

    private int counter;
    private static int PERIOD_LENGTH = 180;

    Invincibility(){
        counter = PERIOD_LENGTH;
    }

    public boolean isInvincible(){
        return counter < PERIOD_LENGTH;
    }

    public void startInvincibilityPeriod(){
        counter = 0;
    }

    public void increment(){
        counter++;
    }

    public int getCounter(){
        return counter;
    }
}
