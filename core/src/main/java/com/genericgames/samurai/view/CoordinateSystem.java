package com.genericgames.samurai.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CoordinateSystem {

    public static final int ROTATION_CORRECTION = 90;

    private static int getScreenCenterX() {
        return Gdx.graphics.getWidth() / 2;
    }

    private static int getScreenCenterY() {
        return Gdx.graphics.getHeight() / 2;
    }

    public static Vector2 translateMousePosToWorldPosition(Vector2 position){
        float xPositionFromCenter = position.x - getScreenCenterX();
        float yPositionFromCenter = position.y - getScreenCenterY();
        return new Vector2(xPositionFromCenter, yPositionFromCenter);
    }

    public static float getRotationAngleInDegrees(Vector2 rotationVector) {
        return (ROTATION_CORRECTION - rotationVector.angle()) % 360;
    }

    public static float getRotationAngleInRadians(Vector2 rotationVector){
        return getRotationAngleInDegrees(rotationVector) * MathUtils.degreesToRadians;
    }
}
