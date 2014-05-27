package com.genericgames.samurai.ai.patrolpattern;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.AI;

/**
 * Performing a PausePatrolStep causes AI to stand IDLE
 */
public class PausePatrolStep implements PatrolStep {

    private static int PAUSE_LENGTH = 60;

    private int pauseLength;
    private int pauseCounter = 0;

    public PausePatrolStep(){
        this.pauseLength = PAUSE_LENGTH;
    }

    public PausePatrolStep(int pauseLength){
        this.pauseLength = pauseLength;
    }

    @Override
    public boolean isStepComplete(SamuraiWorld samuraiWorld, AI ai) {
        boolean isComplete = pauseCounter >= pauseLength;
        if(isComplete){
            resetCounter();
        }
        return isComplete;
    }

    private void resetCounter() {
        pauseCounter = 0;
    }

    @Override
    public void processStep(SamuraiWorld samuraiWorld, AI ai) {
        pauseCounter++;
    }
}
