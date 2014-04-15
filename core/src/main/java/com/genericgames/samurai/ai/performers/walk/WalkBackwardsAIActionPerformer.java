package com.genericgames.samurai.ai.performers.walk;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class WalkBackwardsAIActionPerformer extends WalkAIActionPerformer {
    public WalkBackwardsAIActionPerformer(AI performer) {
        super(performer);
    }

    @Override
    public ActionState getActionState() {
        return ActionState.WALK_BACKWARDS;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld){
        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);

        movementVector.backwardMovement(performer.getSpeed());

        performWalk(performer, movementVector, samuraiWorld.getPhysicalWorld());

        super.performAction(samuraiWorld);
    }
}
