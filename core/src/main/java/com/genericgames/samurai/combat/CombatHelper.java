package com.genericgames.samurai.combat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.Living;
import com.genericgames.samurai.physics.PhysicalWorld;
import com.genericgames.samurai.utility.MovementVector;

import java.util.ArrayList;
import java.util.Collection;

public class CombatHelper {

    private static final int DODGE_DURATION = 18;
    private static Vector2 dodgeVector = new Vector2();

    public static void initiateAttack(State state, Living attacker){
        attacker.setState(state);
        attacker.setStateTime(0);
    }

    public static void continueAttack(Living attacker, World world){
        float stateTime = attacker.getStateTime() + 1;
        attacker.setStateTime(stateTime);
        try {
            Attack correspondingAttack = getMatchingAttack(attacker.getState(), attacker);
            if(stateTime == correspondingAttack.getDuration()){
                for(Living attacked : getAttackedObjects(attacker, world)){
                    attacked.damage(CombatHelper.getApplicableDamage(attacker, attacked));
                }
            }
            else if(stateTime > correspondingAttack.getDuration()){
                attacker.setState(State.IDLE);
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Collection<Living> getAttackedObjects(Living attacker, World world) {
        Fixture attackField = PhysicalWorld.getAttackFieldFor(attacker, world);
        Collection<Living> attacked = new ArrayList<Living>();
        for(Contact contact : world.getContactList()){
            if(contact.getFixtureA().equals(attackField)){
                if(!contact.getFixtureB().isSensor() &&
                        contact.getFixtureB().getBody().getUserData() instanceof Living){
                    attacked.add((Living)contact.getFixtureB().getBody().getUserData());
                }
            }
            else if(contact.getFixtureB().equals(attackField)){
                if(!contact.getFixtureA().isSensor() &&
                        contact.getFixtureA().getBody().getUserData() instanceof Living){
                    attacked.add((Living)contact.getFixtureA().getBody().getUserData());
                }
            }
        }
        return attacked;
    }

    private static Attack getMatchingAttack(State state, Living attacker) throws AttackNotFoundException {
        for(Attack attack : attacker.getAttacks()){
            if(attack.getState().equals(state)){
                return attack;
            }
        }
        throw new AttackNotFoundException();
    }

    public static int getApplicableDamage(Living attacker, Living attacked) {
        try {
            Attack correspondingAttack = getMatchingAttack(attacker.getState(), attacker);
            // Apply no damage if the attacked character blocks a light attack:
            if(!attacked.getState().equals(State.BLOCKING) || !correspondingAttack.getState().equals(State.LIGHT_ATTACKING)){
                if(attacker.getStateTime() == correspondingAttack.getDuration()){
                    return correspondingAttack.getStrength();
                }
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void initiateCharge(Living attacker){
        attacker.setState(State.CHARGING);
        attacker.setStateTime(0);
    }

    public static void initiateBlock(Living attacker){
        attacker.setState(State.BLOCKING);
        attacker.setStateTime(0);
    }

    public static void stopBlock(Living attacker){
        attacker.setState(State.IDLE);
        attacker.setStateTime(0);
    }

    public static void continueCharge(State state, Living attacker) {
        float stateTime = attacker.getStateTime() + 1;
        attacker.setStateTime(stateTime);
        try {
            Attack correspondingAttack = getMatchingAttack(state, attacker);
            if(correspondingAttack instanceof ChargeAttack){
                ChargeAttack chargeAttack = (ChargeAttack)correspondingAttack;
                if(stateTime < chargeAttack.getChargeDuration()){
                    attacker.setState(State.CHARGING);
                }
                else {
                    attacker.setState(State.CHARGED);
                }
            }
            else{
                throw new IllegalArgumentException("No ChargeAttack found for state: "+state+", and attacker: "+attacker);
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Vector2 getAttackMovementVector(Living attacker, MovementVector movementVector) {
        State attackerState = attacker.getState();
        if(attackerState.equals(State.HEAVY_ATTACKING)){
            return movementVector.getHeavyAttackVector();
        }
        else if(attackerState.equals(State.LIGHT_ATTACKING)){
            return movementVector.getLightAttackVector();
        }
        throw new IllegalArgumentException("No movement vector defined for this state: "+attackerState);
    }

    public static void initiateDodge(Living dodger){
        dodger.setState(State.DODGE);
        dodger.setStateTime(0);
    }

    public static void continueDodge(Living dodger) {
        float stateTime = dodger.getStateTime() + 1;
        dodger.setStateTime(stateTime);

        if(stateTime >= DODGE_DURATION){
            dodger.setState(State.IDLE);
        }
    }

    public static Vector2 getDodgeVector() {
        return dodgeVector;
    }

    public static void setDodgeVector(Vector2 dodgeVector) {
        CombatHelper.dodgeVector = dodgeVector;
    }
}
