package com.genericgames.samurai.ai.performers.attack;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.AttackHelper;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.combat.TelegraphedAttack;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class LightAttackAIActionPerformer extends AttackAIActionPerformer {

    private int duration;

    public LightAttackAIActionPerformer(AI performer) {
        super(performer);
        try {
            Attack attack = AttackHelper.getMatchingAttack(State.LIGHT_ATTACKING, performer);
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
        return ActionState.LIGHT_ATTACK;
    }

    @Override
    public void performAction(SamuraiWorld samuraiWorld) {
        super.performAction(samuraiWorld);

        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        try {
            Attack attack = AttackHelper.getMatchingAttack(State.HEAVY_ATTACKING, performer);
            World physicalWorld = samuraiWorld.getPhysicalWorld();
            if(attack instanceof TelegraphedAttack &&
                    getActionFrame()<((TelegraphedAttack)attack).getTelegraphDuration()){
                movementVector.stop();
                performer.setState(State.TELEGRAPHING_LIGHT_ATTACK);
            }
            else if(getActionFrame()<=attack.getInflictionFrame()){
                movementVector.getLightAttackVector(performer.getSpeed());
                performer.setState(State.LIGHT_ATTACKING);
                if(getActionFrame()==attack.getInflictionFrame()){
                    PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
                    if(CombatHelper.getAttackedObjects(performer, physicalWorld).contains(playerCharacter)){
                        playerCharacter.damage(CombatHelper.getApplicableDamage(performer, playerCharacter));
                    }
                }
            }
            else{
                movementVector.stop();
                performer.setState(State.LIGHT_ATTACKING);
            }
            performAttack(performer, movementVector, physicalWorld);
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }

    }
}