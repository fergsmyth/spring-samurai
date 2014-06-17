package com.genericgames.samurai.combat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.exception.AttackNotFoundException;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.state.living.Living;
import com.genericgames.samurai.model.state.living.combatable.Combatable;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

import java.util.ArrayList;
import java.util.Collection;

public class CombatHelper {

    public static final int DODGE_DURATION = 18;
    public static final int KNOCKBACK_DURATION = 30;
    private static Vector2 dodgeVector = new Vector2();

    public static void initiateAttack(State state, Living attacker){
        attacker.setState(state);
        attacker.setStateTime(0);
    }

    public static void continueAttack(Combatable attacker, SamuraiWorld samuraiWorld){
        float stateTime = attacker.getStateTime();
        try {
            State attackState = attacker.getState();
            Attack correspondingAttack = AttackHelper.getMatchingAttack(attackState, attacker);
            if(stateTime == correspondingAttack.getInflictionFrame()){
                for(Living attacked : getAttackedObjects(attacker, attackState, samuraiWorld.getPhysicalWorld())){
                    attacked.damage(getApplicableDamage(attacker, attacked), samuraiWorld);
                }
            }

            if(stateTime >= correspondingAttack.getTotalDuration()-1){
                attacker.setState(State.IDLE);
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Collection<Living> getAttackedObjects(Combatable attacker, State attackState, World world) {
        Fixture attackField = PhysicalWorldHelper.getAttackFieldFor(attacker, attackState, world);
        Collection<Living> attacked = new ArrayList<Living>();
        for(Contact contact : world.getContactList()){
            if(contact.isTouching()){
                if(contact.getFixtureA().equals(attackField)){
                    if(PhysicalWorldHelper.isLivingBody(contact.getFixtureB())){
                        attacked.add((Living)contact.getFixtureB().getBody().getUserData());
                    }
                }
                else if(contact.getFixtureB().equals(attackField)){
                    if(PhysicalWorldHelper.isLivingBody(contact.getFixtureA())){
                        attacked.add((Living)contact.getFixtureA().getBody().getUserData());
                    }
                }
            }
        }
        return attacked;
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

    public static void continueCharge(State state, Combatable attacker) {
        try {
            Attack correspondingAttack = AttackHelper.getMatchingAttack(state, attacker);
            if(correspondingAttack instanceof ChargeAttack){
                ChargeAttack chargeAttack = (ChargeAttack)correspondingAttack;
                if(attacker.getStateTime() < chargeAttack.getChargeDuration()){
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

    public static void initiateDodge(Living dodger){
        dodger.setState(State.DODGE);
        dodger.setStateTime(0);
    }

    public static void continueDodge(Living dodger) {
        if(dodger.getStateTime() >= DODGE_DURATION-1){
            dodger.setState(State.IDLE);
        }
    }

    public static void continueKnockBack(Living character) {
        if(character.getStateTime() >= KNOCKBACK_DURATION-1){
            character.setState(State.IDLE);
        }
    }

    public static Vector2 getDodgeVector() {
        return dodgeVector;
    }

    public static void setDodgeVector(Vector2 dodgeVector) {
        CombatHelper.dodgeVector = dodgeVector;
    }

    public static int getApplicableDamage(Combatable attacker, Living attacked) {
        try {
            Attack correspondingAttack = AttackHelper.getMatchingAttack(attacker.getState(), attacker);
            // Apply no damage if the attacked character blocks a light attack:
            if(!attacked.getState().equals(State.BLOCKING) || !correspondingAttack.getState().equals(State.LIGHT_ATTACKING)){
                return correspondingAttack.getStrength();
            }
        } catch (AttackNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
