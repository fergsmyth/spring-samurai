package com.genericgames.samurai.maths;

import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.ai.routefinding.AStarNode;
import com.genericgames.samurai.ai.routefinding.MapNode;
import com.genericgames.samurai.model.WorldObject;

public class MyMathUtils {

    /**
     * Calculates the distance between 2 given points, a and b.
     */
    public static float getDistance(float aX, float aY, float bX, float bY) {
        return (float) Math.sqrt(Math.pow((aX - bX), 2) + Math.pow((aY - bY), 2));
    }

    public static float getDistanceBetween(WorldObject a, WorldObject b){
        return getDistance(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static float getDistance(AStarNode nodeA, AStarNode nodeB) {
        MapNode mapNodeA = nodeA.getMapNode();
        MapNode mapNodeB = nodeB.getMapNode();
        return getDistance(mapNodeA.getPositionX(), mapNodeA.getPositionY(),
                mapNodeB.getPositionX(), mapNodeB.getPositionY());
    }

    public static float getAngleBetweenTwoPoints(float sourceX, float sourceY,
                                                 float targetX, float targetY) {
        return (float) Math.atan((targetY-sourceY)/(targetX-sourceX))
                + (float) Math.toRadians(90);
    }

    public static Vector2 getVectorFromTwoPoints(float sourceX, float sourceY, float targetX, float targetY) {
        return new Vector2(targetX - sourceX,
                sourceY - targetY);
    }

    public static Vector2 getVectorFromPointAndAngle(float sourceX, float sourceY, float angleInRadians) {
        float targetX = sourceX + (float)Math.sin(angleInRadians);
        float targetY = sourceY - (float)Math.cos(angleInRadians);
        return getVectorFromTwoPoints(sourceX, sourceY, targetX, targetY);
    }
}
