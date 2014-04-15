package com.genericgames.samurai.ai.performers;

import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class KnockBackAIPerformer extends AIActionPerformer {

    private static int duration = CombatHelper.KNOCKBACK_DURATION;

    public KnockBackAIPerformer(AI performer) {
        super(performer);
    }

    @Override
    public ActionState getActionState() {
        return ActionState.KNOCK_BACK;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld) {
        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);

        //TODO Add movement to knockback?
//        movementVector.leftMovement(performer.getSpeed());
//        performKnockBack(getPerformer(), movementVector.getDodgeVector(performer.getSpeed()), samuraiWorld.getPhysicalWorld());

        movementVector.stop();
        PhysicalWorldHelper.moveBody(samuraiWorld.getPhysicalWorld(), performer,
                performer.getRotation(), movementVector);

        getPerformer().setState(State.KNOCKED_BACK);

        super.performAction(samuraiWorld);
    }
}
