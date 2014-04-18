package com.genericgames.samurai.model;

import com.badlogic.gdx.physics.box2d.BodyDef;

public interface PhysicalObject {
    BodyDef createBody();
}
