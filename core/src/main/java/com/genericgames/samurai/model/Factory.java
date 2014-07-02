package com.genericgames.samurai.model;

import com.badlogic.gdx.math.Vector2;

public interface Factory {

    public void create(SamuraiWorld samuraiWorld, float x, float y, Vector2 emitVelocity);
}
