package com.genericgames.samurai.ai.performers;

import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.model.movable.living.ai.ActionState;

public abstract class AIActionPerformer {

    private AI performer;

    public int getActionFrame() {
        return actionFrame;
    }

    public void setActionFrame(int actionFrame) {
        this.actionFrame = actionFrame;
    }

    private int actionFrame = 0;

    public AIActionPerformer(AI performer){
        this.performer = performer;
        performer.setAIActionPerformer(this);
    }

    public AI getPerformer() {
        return performer;
    }

    public void setPerformer(AI performer) {
        this.performer = performer;
    }

    public abstract ActionState getActionState();

    public abstract int getDuration();

    public void performAction(World physicalWorld){
        iterateActionFrame();
    }

    public void iterateActionFrame(){
        if(actionFrame >= getDuration()){
            performer.setAIActionPerformer(new IdleAIActionPerformer(performer));
        }
        actionFrame++;
    }
}
