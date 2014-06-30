package com.genericgames.samurai.ai.performers.attack;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.combat.Attack;
import com.genericgames.samurai.combat.AttackHelper;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.combat.TelegraphedAttack;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.MovementVector;

public class LightAttackAIActionPerformer extends AttackAIActionPerformer {

    private int duration;

    public LightAttackAIActionPerformer(AI performer) {
        super(performer);
        try {
            Attack attack = AttackHelper.getMatchingAttack(State.LIGHT_ATTACKING, (Combatable)performer);
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
        AI performer = getPerformer();
        MovementVector movementVector =
                PhysicalWorldHelper.getMovementVectorFor(performer);
        try {
            Attack attack = AttackHelper.getMatchingAttack(State.LIGHT_ATTACKING, (Combatable)performer);
            World physicalWorld = samuraiWorld.getPhysicalWorld();
            if(attack instanceof TelegraphedAttack &&
                    getActionFrame()<attack.getTelegraphDuration()){
                movementVector.stop();
                performer.setState(State.TELEGRAPHING_LIGHT_ATTACK);
            }
            else if(getActionFrame()<=attack.getInflictionFrame()){
                movementVector.getStaticAttackVector(performer.getSpeed());
                performer.setState(State.LIGHT_ATTACKING);
                if(getActionFrame()==attack.getTelegraphDuration()){
                    //reset State Timer:
                    performer.setStateTime(0);
                }
                else if(getActionFrame()==attack.getInflictionFrame()){
                    PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();
                    if(CombatHelper.getAttackedObjects((Combatable)performer, State.LIGHT_ATTACKING, physicalWorld).contains(playerCharacter)){
                        playerCharacter.damage(CombatHelper.getApplicableDamage((Combatable)performer, playerCharacter),
                                samuraiWorld);
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

        super.performAction(samuraiWorld);
    }
}
