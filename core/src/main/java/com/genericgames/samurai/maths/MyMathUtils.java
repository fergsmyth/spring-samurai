package com.genericgames.samurai.maths;

import com.genericgames.samurai.ai.routefinding.AStarNode;
import com.genericgames.samurai.ai.routefinding.MapNode;

public class MyMathUtils {

    /**
     * Calculates the distance between 2 given points, a and b.
     */
    public static float getDistance(float aX, float aY, float bX, float bY) {
        return (float) Math.sqrt(Math.pow((aX - bX), 2) + Math.pow((aY - bY), 2));
    }

    public static float getDistance(AStarNode nodeA, AStarNode nodeB) {
        MapNode mapNodeA = nodeA.getMapNode();
        MapNode mapNodeB = nodeB.getMapNode();
        return getDistance(mapNodeA.getPositionX(), mapNodeA.getPositionY(),
                mapNodeB.getPositionX(), mapNodeB.getPositionY());
    }
}
