package com.genericgames.samurai.ai.performers.dodge;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class DodgeRightAIActionPerformer extends DodgeAIActionPerformer {
    public DodgeRightAIActionPerformer(AI performer) {
        super(performer);
    }

    @Override
    public ActionState getActionState() {
        return ActionState.DODGE_RIGHT;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld) {
        super.performAction(samuraiWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);

        movementVector.rightMovement(performer.getSpeed());
        performDodge(getPerformer(), movementVector.getDodgeVector(performer.getSpeed()), samuraiWorld.getPhysicalWorld());
    }
}
