package com.genericgames.samurai.ai.patrolpattern;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.AI;

public interface PatrolStep {

    public boolean isStepComplete(SamuraiWorld samuraiWorld, AI ai);

    public void processStep(SamuraiWorld samuraiWorld, AI ai);
}
