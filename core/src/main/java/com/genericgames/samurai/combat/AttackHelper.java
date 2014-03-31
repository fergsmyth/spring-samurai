package com.genericgames.samurai.combat;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.utility.MovementVector;

public class AttackHelper {

    public static boolean isTelegraphing(Living attacker, Attack attack) {
        return attack instanceof TelegraphedAttack &&
                attacker.getStateTime()<((TelegraphedAttack)attack).getTelegraphDuration();
    }

    public static boolean isAttacking(Living attacker, Attack attack) {
        return !isTelegraphing(attacker, attack) &&
                attacker.getStateTime()<=attack.getInflictionFrame();
    }

    public static boolean isRecovering(Living attacker, Attack attack) {
        return !isAttacking(attacker, attack) &&
                attacker.getStateTime()<attack.getTotalDuration();
    }

    public static Attack getMatchingAttack(State state, Living attacker) throws AttackNotFoundException {
        for(Attack attack : attacker.getAttacks()){
            if(attack.getState().equals(state)){
                return attack;
            }
        }
        throw new AttackNotFoundException();
    }

    public static Vector2 getAttackMovementVector(Living attacker, MovementVector movementVector) {
        State attackerState = attacker.getState();
        try {
            Attack attack = getMatchingAttack(attackerState, attacker);
            if(isRecovering(attacker, attack)){
                movementVector.stop();
                return movementVector;
            }
            else {
                if(attackerState.equals(State.HEAVY_ATTACKING)){
                    return movementVector.getHeavyAttackVector(attacker.getSpeed());
                }
                else if(attackerState.equals(State.LIGHT_ATTACKING)){
                    return movementVector.getLightAttackVector(attacker.getSpeed());
                }
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("No movement vector defined for this state: "+attackerState);
    }
}
