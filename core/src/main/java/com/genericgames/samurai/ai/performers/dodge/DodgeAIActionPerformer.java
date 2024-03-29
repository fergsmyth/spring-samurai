package com.genericgames.samurai.ai.performers.dodge;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.genericgames.samurai.ai.performers.AIActionPerformer;
import com.genericgames.samurai.audio.SoundEffectCache;
import com.genericgames.samurai.combat.CombatHelper;
import com.genericgames.samurai.model.state.State;
import com.genericgames.samurai.model.movable.character.ai.AI;
import com.genericgames.samurai.physics.PhysicalWorldHelper;

public abstract class DodgeAIActionPerformer extends AIActionPerformer {

    private static int duration = CombatHelper.DODGE_DURATION;

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

        //Play sfx on first frame:
        if(this.getActionFrame() == 0){
            SoundEffectCache.dodge.play(1.0f);
        }
    }
}
