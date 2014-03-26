package com.genericgames.samurai.ai.performers.dodge;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
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
    public void performAction(World physicalWorld) {
        super.performAction(physicalWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);

        movementVector.rightMovement(performer.getSpeed());
        performDodge(getPerformer(), movementVector.getDodgeVector(performer.getSpeed()), physicalWorld);
    }
}
