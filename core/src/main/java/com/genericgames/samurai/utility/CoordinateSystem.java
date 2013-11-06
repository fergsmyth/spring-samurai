package com.genericgames.samurai.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CoordinateSystem {

    public static final int ROTATION_CORRECTION = 90;

    private static int getPlayerPositionX() {
        return Gdx.graphics.getWidth() / 2;
    }

    private static int getPlayerPositionY() {
        return Gdx.graphics.getHeight() / 2;
    }

    public static Vector2 translateMouseToLocalPosition(Vector2 mousePosition){
        float xPositionFromCenter = mousePosition.x - getPlayerPositionX();
        float yPositionFromCenter = mousePosition.y - getPlayerPositionY();
        return new Vector2(xPositionFromCenter, yPositionFromCenter);
    }

    public static float getRotationAngleInDegrees(Vector2 rotationVector) {
        return (ROTATION_CORRECTION - rotationVector.angle()) % 360;
    }

    public static float getRotationAngleInRadians(Vector2 rotationVector){
        return getRotationAngleInDegrees(rotationVector) * MathUtils.degreesToRadians;
    }
}
