package com.genericgames.samurai.ai.performers;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class IdleAIActionPerformer extends AIActionPerformer {

    private static int duration = 45;

    public IdleAIActionPerformer(AI performer) {
        super(performer);
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public ActionState getActionState() {
        return ActionState.IDLE;
    }

    @Override
    public void performAction(World physicalWorld) {
        super.performAction(physicalWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        movementVector.stop();
        PhysicalWorldHelper.moveBody(physicalWorld, performer,
                performer.getRotation(), movementVector);

        getPerformer().setState(State.IDLE);
    }
}