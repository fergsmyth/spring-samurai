package com.genericgames.samurai.ai.performers;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
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
    public void performAction(World physicalWorld) {
        super.performAction(physicalWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        movementVector.stop();
        PhysicalWorldHelper.moveBody(physicalWorld, performer,
                performer.getRotation(), movementVector);

        getPerformer().setState(State.BLOCKING);
    }
}
