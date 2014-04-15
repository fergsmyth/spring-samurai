package com.genericgames.samurai.ai.performers;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class BlockAIActionPerformer extends AIActionPerformer {

    private static int duration = 60;

    public BlockAIActionPerformer(AI performer) {
        super(performer);
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public ActionState getActionState() {
        return ActionState.BLOCK;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld) {
        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        movementVector.stop();
        PhysicalWorldHelper.moveBody(samuraiWorld.getPhysicalWorld(), performer,
                performer.getRotation(), movementVector);

        getPerformer().setState(State.BLOCKING);

        super.performAction(samuraiWorld);
    }
}
