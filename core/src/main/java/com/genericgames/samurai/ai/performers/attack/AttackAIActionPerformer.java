package com.genericgames.samurai.ai.performers.attack;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public abstract class AttackAIActionPerformer extends AIActionPerformer {

    public AttackAIActionPerformer(AI performer) {
        super(performer);
    }

    protected void performAttack(AI performer, Vector2 movementVector, World physicalWorld) {
        PhysicalWorldHelper.moveBody(physicalWorld, performer,
                performer.getRotation(), movementVector);
    }
}
