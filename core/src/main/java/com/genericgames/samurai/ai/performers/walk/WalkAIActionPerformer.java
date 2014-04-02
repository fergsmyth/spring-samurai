package com.genericgames.samurai.ai.performers.walk;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public abstract class WalkAIActionPerformer extends AIActionPerformer {

    private static int duration = 40;

    public WalkAIActionPerformer(AI performer) {
        super(performer);
    }

    public int getDuration() {
        return duration;
    }

    protected void performWalk(AI performer, MovementVector movementVector, World physicalWorld) {
        PhysicalWorldHelper.moveBody(physicalWorld, performer,
                performer.getRotation(), movementVector);
        performer.setState(State.WALKING);
    }
}
