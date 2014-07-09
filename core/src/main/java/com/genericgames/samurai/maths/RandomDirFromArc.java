package com.genericgames.samurai.maths;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class RandomDirFromArc {

    Vector2 centralVector;
    int arcAngleInDegrees;
    Random random = new Random();

    public RandomDirFromArc(Vector2 centralVector, int arcAngleInDegrees){
        this.centralVector = centralVector;
        this.arcAngleInDegrees = arcAngleInDegrees;
    }

    public Vector2 getRandomDirection(){
        int randomAngle = random.nextInt(arcAngleInDegrees);
        Vector2 leftLimit = centralVector.cpy().rotate(-arcAngleInDegrees /2f);
        return leftLimit.rotate(randomAngle);
    }

    public Vector2 getCentralVector() {
        return centralVector;
    }

    public void setCentralVector(Vector2 centralVector) {
        this.centralVector = centralVector;
    }

    public int getArcAngleInDegrees() {
        return arcAngleInDegrees;
    }

    public void setArcAngleInDegrees(int arcAngleInDegrees) {
        this.arcAngleInDegrees = arcAngleInDegrees;
    }
}
