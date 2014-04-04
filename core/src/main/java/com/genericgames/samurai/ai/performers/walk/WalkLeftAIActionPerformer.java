package com.genericgames.samurai.ai.performers.walk;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class WalkLeftAIActionPerformer extends WalkAIActionPerformer {
    public WalkLeftAIActionPerformer(AI performer) {
        super(performer);
    }

    @Override
    public ActionState getActionState() {
        return ActionState.WALK_LEFT;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld){
        super.performAction(samuraiWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);

        movementVector.leftMovement(performer.getSpeed());

        performWalk(performer, movementVector, samuraiWorld.getPhysicalWorld());
    }
}