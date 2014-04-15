package com.genericgames.samurai.ai.performers;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.model.movable.character.ai.ActionState;

public abstract class AIActionPerformer {

    private AI performer;

    private int actionFrame = 0;

    public AIActionPerformer(AI performer){
        this.performer = performer;
        performer.setAIActionPerformer(this);
        performer.setStateTime(0);
    }

    public AI getPerformer() {
        return performer;
    }

    public void setPerformer(AI performer) {
        this.performer = performer;
    }

    public int getActionFrame() {
        return actionFrame;
    }

    public void setActionFrame(int actionFrame) {
        this.actionFrame = actionFrame;
    }

    public abstract ActionState getActionState();

    public abstract int getDuration();

    public void performAction(SamuraiWorld samuraiWorld){
        performer.incrementStateTime();
        iterateActionFrame();
    }

    public void iterateActionFrame(){
        if(actionFrame >= getDuration()){
            performer.setAIActionPerformer(new IdleAIActionPerformer(performer));
        }
        actionFrame++;
    }
}
