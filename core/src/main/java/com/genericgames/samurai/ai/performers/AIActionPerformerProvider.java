package com.genericgames.samurai.ai.performers;

import com.genericgames.samurai.ai.performers.attack.HeavyAttackAIActionPerformer;
import com.genericgames.samurai.ai.performers.attack.LightAttackAIActionPerformer;
import com.genericgames.samurai.ai.performers.dodge.DodgeLeftAIActionPerformer;
import com.genericgames.samurai.ai.performers.dodge.DodgeRightAIActionPerformer;
import com.genericgames.samurai.ai.performers.walk.WalkBackwardsAIActionPerformer;
import com.genericgames.samurai.ai.performers.walk.WalkForwardAIActionPerformer;
import com.genericgames.samurai.ai.performers.walk.WalkLeftAIActionPerformer;
import com.genericgames.samurai.ai.performers.walk.WalkRightAIActionPerformer;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;

public class AIActionPerformerProvider {

    public static AIActionPerformer getActionPerformer(ActionState actionState, AI performer){
        AIActionPerformer correspondingAIActionPerformer;
        switch (actionState){
            case WALK_FORWARD:
                correspondingAIActionPerformer = new WalkForwardAIActionPerformer(performer);
                break;
            case WALK_BACKWARDS:
                correspondingAIActionPerformer = new WalkBackwardsAIActionPerformer(performer);
                break;
            case WALK_LEFT:
                correspondingAIActionPerformer = new WalkLeftAIActionPerformer(performer);
                break;
            case WALK_RIGHT:
                correspondingAIActionPerformer = new WalkRightAIActionPerformer(performer);
                break;
            case LIGHT_ATTACK:
                correspondingAIActionPerformer = new LightAttackAIActionPerformer(performer);
                break;
            case HEAVY_ATTACK:
                correspondingAIActionPerformer = new HeavyAttackAIActionPerformer(performer);
                break;
            case BLOCK:
                correspondingAIActionPerformer = new BlockAIActionPerformer(performer);
                break;
            case DODGE_LEFT:
                correspondingAIActionPerformer = new DodgeLeftAIActionPerformer(performer);
                break;
            case DODGE_RIGHT:
                correspondingAIActionPerformer = new DodgeRightAIActionPerformer(performer);
                break;
            case KNOCK_BACK:
                correspondingAIActionPerformer = new KnockBackAIPerformer(performer);
                break;
            default:
                correspondingAIActionPerformer = new IdleAIActionPerformer(performer);
                break;
        }
        return correspondingAIActionPerformer;
    }
}
