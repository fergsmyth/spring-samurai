package com.genericgames.samurai.ai.performers.walk;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class WalkForwardAIActionPerformer extends WalkAIActionPerformer {

    public WalkForwardAIActionPerformer(AI performer) {
        super(performer);
    }

    @Override
    public ActionState getActionState() {
        return ActionState.WALK_FORWARD;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld){
        super.performAction(samuraiWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);

        movementVector.forwardMovement(performer.getSpeed());

        performWalk(performer, movementVector, samuraiWorld.getPhysicalWorld());
    }
}