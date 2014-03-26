package com.genericgames.samurai.ai.performers.dodge;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.model.movable.State;
import com.genericgames.samurai.model.movable.living.ai.AI;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public abstract class DodgeAIActionPerformer extends AIActionPerformer {

    private static int duration = 18;

    public DodgeAIActionPerformer(AI performer) {
        super(performer);
    }

    public int getDuration() {
        return duration;
    }

    protected void performDodge(AI performer, Vector2 movementVector, World physicalWorld) {
        PhysicalWorldHelper.moveBody(physicalWorld, performer,
                performer.getRotation(), movementVector);
        performer.setState(State.DODGE);
        performer.incrementStateTime();
    }
}
