package com.genericgames.samurai.ai.patrolpattern;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.genericgames.samurai.ai.AIHelper;
import com.genericgames.samurai.maths.MyMathUtils;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

import java.util.ArrayList;

/**
 * Performing a WalkPatrolStep causes AI to perform route finding to a given point
 */
public class WalkPatrolStep implements PatrolStep {

    private Vector2 targetPoint;

    public WalkPatrolStep(Vector2 targetPoint){
        this.targetPoint = targetPoint;
    }

    @Override
    public boolean isStepComplete(SamuraiWorld samuraiWorld, AI ai) {
        boolean isComplete = MyMathUtils.getDistance(targetPoint.x, targetPoint.y,
                ai.getX(), ai.getY()) < 0.03f;
        if(isComplete){
            //Stop movement:
            PhysicalWorldHelper.moveBody(samuraiWorld.getPhysicalWorld(), ai, ai.getRotation(),
                    new Vector2());
        }
        return isComplete;
    }

    @Override
    public void processStep(SamuraiWorld samuraiWorld, AI ai) {
        AIHelper.performRouteFindingToPoint(samuraiWorld, ai,
                targetPoint.x, targetPoint.y, new ArrayList<Fixture>());
    }
}
