package com.genericgames.samurai.ai.performers.attack;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.AttackHelper;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class HeavyAttackAIActionPerformer extends AttackAIActionPerformer {

    private int duration;

    public HeavyAttackAIActionPerformer(AI performer) {
        super(performer);
        try {
            Attack attack = AttackHelper.getMatchingAttack(State.HEAVY_ATTACKING, (Combatable)performer);
            setDuration(attack.getTotalDuration());
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
    public void performAction(SamuraiWorld samuraiWorld) {

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        try {
            Attack attack = AttackHelper.getMatchingAttack(State.HEAVY_ATTACKING, (Combatable)performer);
            World physicalWorld = samuraiWorld.getPhysicalWorld();
            if(AttackHelper.isTelegraphing((Combatable)performer, attack)){
                movementVector.stop();
                performer.setState(State.TELEGRAPHING_HEAVY_ATTACK);
            }
            else if(AttackHelper.isAttacking((Combatable)performer, attack)){
                movementVector.getHeavyAttackVector(performer.getSpeed());
                performer.setState(State.HEAVY_ATTACKING);
                if(getActionFrame()==attack.getInflictionFrame()){
                    PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
                    if(CombatHelper.getAttackedObjects((Combatable)performer, physicalWorld).contains(playerCharacter)){
                        playerCharacter.damage(CombatHelper.getApplicableDamage((Combatable)performer, playerCharacter),
                                physicalWorld);
                    }
                }
            }
            else if(AttackHelper.isRecovering((Combatable)performer, attack)){
                movementVector.stop();
                performer.setState(State.HEAVY_ATTACKING);
            }
            performAttack(performer, movementVector, physicalWorld);
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }

        super.performAction(samuraiWorld);
    }
}
