package com.genericgames.samurai.ai;

import com.genericgames.samurai.model.movable.character.ai.ActionState;
import com.genericgames.samurai.model.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EnemyAIActionScript {

    public static ActionState getNewEnemyAIActionState(State playerState, float distanceToPlayer,
                                                       boolean incomingArrow) throws Exception {
        int probabilityRange = 0;

        //Initialise each action with a probability of 10/100
        Map<ActionState, Integer> actionProbabilities = new HashMap<ActionState, Integer>();
        for(ActionState actionState : ActionState.getVoluntaryActionStates()){
            actionProbabilities.put(actionState, 10);
            probabilityRange = probabilityRange + 10;
        }
        
        if(playerState.equals(State.LIGHT_ATTACKING) || playerState.equals(State.CHARGING) || incomingArrow){
            //Increase block probability
            actionProbabilities.put(ActionState.BLOCK, actionProbabilities.get(ActionState.BLOCK) + 100);
            probabilityRange = probabilityRange + 100;
        }
        else if(playerState.equals(State.HEAVY_ATTACKING) || playerState.equals(State.CHARGED) || incomingArrow){
            //Increase dodge probability
            actionProbabilities.put(ActionState.DODGE_LEFT, actionProbabilities.get(ActionState.DODGE_LEFT) + 50);
            actionProbabilities.put(ActionState.DODGE_RIGHT, actionProbabilities.get(ActionState.DODGE_RIGHT) + 50);
            probabilityRange = probabilityRange + 100;
        }

        if(distanceToPlayer<1.6f && distanceToPlayer>0.8f){
            //Increase heavy attack probability
            actionProbabilities.put(ActionState.HEAVY_ATTACK, actionProbabilities.get(ActionState.HEAVY_ATTACK) + 100);
            probabilityRange = probabilityRange + 100;
        }
        else {
            Integer currentProbability = actionProbabilities.get(ActionState.HEAVY_ATTACK);
            actionProbabilities.put(ActionState.HEAVY_ATTACK, 0);
            probabilityRange = probabilityRange - currentProbability;
        }

        if(distanceToPlayer<1f){
            //Increase light attack probability
            actionProbabilities.put(ActionState.LIGHT_ATTACK, actionProbabilities.get(ActionState.LIGHT_ATTACK) + 100);
            probabilityRange = probabilityRange + 100;
        }
        else {
            Integer currentProbability = actionProbabilities.get(ActionState.LIGHT_ATTACK);
            actionProbabilities.put(ActionState.LIGHT_ATTACK, 0);
            probabilityRange = probabilityRange - currentProbability;
        }

        if(distanceToPlayer>2.0f){
            //Increase walk forward probability
            actionProbabilities.put(ActionState.WALK_FORWARD, actionProbabilities.get(ActionState.WALK_FORWARD) + 100);
            probabilityRange = probabilityRange + 100;
        }
        else if(distanceToPlayer<0.3f){
            //Increase walk backward probability
            actionProbabilities.put(ActionState.WALK_BACKWARDS, actionProbabilities.get(ActionState.WALK_BACKWARDS) + 40);
            probabilityRange = probabilityRange + 40;
        }

        Random r = new Random();
        int randomInteger = r.nextInt(probabilityRange);
        if(randomInteger<actionProbabilities.get(ActionState.BLOCK)){
            return ActionState.BLOCK;
        }
        else {
            randomInteger = randomInteger - actionProbabilities.get(ActionState.BLOCK);
        }

        if(randomInteger<actionProbabilities.get(ActionState.WALK_FORWARD)){
            return ActionState.WALK_FORWARD;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.WALK_FORWARD);
        }

        if(randomInteger<actionProbabilities.get(ActionState.WALK_BACKWARDS)){
            return ActionState.WALK_BACKWARDS;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.WALK_BACKWARDS);
        }

        if(randomInteger<actionProbabilities.get(ActionState.WALK_LEFT)){
            return ActionState.WALK_LEFT;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.WALK_LEFT);
        }

        if(randomInteger<actionProbabilities.get(ActionState.WALK_RIGHT)){
            return ActionState.WALK_RIGHT;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.WALK_RIGHT);
        }

        if(randomInteger<actionProbabilities.get(ActionState.LIGHT_ATTACK)){
            return ActionState.LIGHT_ATTACK;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.LIGHT_ATTACK);
        }

        if(randomInteger<actionProbabilities.get(ActionState.HEAVY_ATTACK)){
            return ActionState.HEAVY_ATTACK;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.HEAVY_ATTACK);
        }

        if(randomInteger<actionProbabilities.get(ActionState.DODGE_RIGHT)){
            return ActionState.DODGE_RIGHT;
        }
        else {
            randomInteger= randomInteger - actionProbabilities.get(ActionState.DODGE_RIGHT);
        }

        if(randomInteger<actionProbabilities.get(ActionState.DODGE_LEFT)){
            return ActionState.DODGE_LEFT;
        }

        return ActionState.IDLE;
    }
}
