package com.example.mylibgdxgame.view;

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
//        Vector2 checkVector = new Vector2(xPositionFromCenter, yPositionFromCenter).nor();
//        if (checkVector.y == 1.0 || checkVector.y == -1.0 || checkVector.x == 1 || checkVector.x == -1){
//            System.out.println(checkVector);
//            System.out.println("Rotate 45 : " + checkVector.rotate(45));
//            System.out.println("Rotate 90 : " + checkVector.rotate(90));
//            System.out.println("Rotate 135 : " + checkVector.rotate(135));
//            System.out.println("Rotate 180 : " + checkVector.rotate(180));
//            System.out.println("Rotate 225 : " + checkVector.rotate(225));
//            System.out.println("Rotate 270 : " + checkVector.rotate(270));
//            System.out.println("Rotate 315 : " + checkVector.rotate(315));
//        }
        return new Vector2(xPositionFromCenter, yPositionFromCenter);
    }

    public static float getRotationAngleInDegrees(Vector2 rotationVector) {
        return (ROTATION_CORRECTION - rotationVector.angle()) % 360;
    }

    public static float getRotationAngleInRadians(Vector2 rotationVector){
        return getRotationAngleInDegrees(rotationVector) * MathUtils.degreesToRadians;
    }
}
