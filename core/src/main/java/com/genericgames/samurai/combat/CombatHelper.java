package com.genericgames.samurai.combat;

import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.Living;

public class CombatHelper {

    public static void initiateAttack(State state, Living attacker){
        attacker.setState(state);
        attacker.setStateTime(0);
    }

    public static void continueAttack(State state, Living attacker){
        float stateTime = attacker.getStateTime() + 1;
        attacker.setStateTime(stateTime);
        try {
            Attack correspondingAttack = getMatchingAttack(state, attacker);
            if(stateTime > correspondingAttack.getDuration()){
                attacker.setState(State.IDLE);
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Attack getMatchingAttack(State state, Living attacker) throws AttackNotFoundException {
        for(Attack attack : attacker.getAttacks()){
            if(attack.getState().equals(state)){
                return attack;
            }
        }
        throw new AttackNotFoundException();
    }
}
