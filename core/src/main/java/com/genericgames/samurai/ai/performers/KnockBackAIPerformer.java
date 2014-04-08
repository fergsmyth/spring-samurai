package com.genericgames.samurai.ai.performers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
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

    protected void performKnockBack(AI performer, Vector2 movementVector, World physicalWorld) {
        PhysicalWorldHelper.moveBody(physicalWorld, performer,
                performer.getRotation(), movementVector);
        performer.setState(State.DODGE);
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld) {
        super.performAction(samuraiWorld);

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
    }
}
