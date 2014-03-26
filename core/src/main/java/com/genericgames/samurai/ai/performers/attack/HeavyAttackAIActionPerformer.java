package com.genericgames.samurai.ai.performers.attack;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class HeavyAttackAIActionPerformer extends AttackAIActionPerformer {

    private int duration;
    private static int telegraphDuration = 45;
    private static int recoveryDuration = 45;

    public HeavyAttackAIActionPerformer(AI performer) {
        super(performer);
        try {
            Attack attack = CombatHelper.getMatchingAttack(State.HEAVY_ATTACKING, performer);
            setDuration(attack.getDuration()+telegraphDuration+recoveryDuration);
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public ActionState getActionState() {
        return ActionState.HEAVY_ATTACK;
    }

    @Override
    public void performAction(World physicalWorld) {
        super.performAction(physicalWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        if(getActionFrame()<telegraphDuration){
            movementVector.stop();
            performer.setState(State.CHARGED);
        }
        else if(getActionFrame()<duration-recoveryDuration){
            movementVector.getHeavyAttackVector(performer.getSpeed());
            performer.setState(State.HEAVY_ATTACKING);
        }
        else {
            movementVector.stop();
            performer.setState(State.HEAVY_ATTACKING);
        }
        performAttack(performer, movementVector, physicalWorld);
    }
}
