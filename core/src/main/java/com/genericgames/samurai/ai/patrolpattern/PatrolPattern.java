package com.genericgames.samurai.ai.patrolpattern;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PatrolPattern {

    private ArrayList<PatrolStep> patrolSteps = new ArrayList<PatrolStep>();
    private PatrolStep currentStep;

    public PatrolPattern(){
    }

    public PatrolPattern(ArrayList<Vector2> patrolPoints){
        for(Vector2 patrolPoint : patrolPoints){
            addPatrolPoint(patrolPoint);
        }
    }

    public PatrolPattern(ArrayList<Vector2> patrolPoints, int pauseLength){
        for(Vector2 patrolPoint : patrolPoints){
            addPatrolPoint(patrolPoint, pauseLength);
        }
    }

    public void addPatrolPoint(Vector2 patrolPoint) {
        patrolSteps.add(new WalkPatrolStep(patrolPoint));
        patrolSteps.add(new PausePatrolStep());
    }

    public void addPatrolPoint(Vector2 patrolPoint, int pauseLength) {
        patrolSteps.add(new WalkPatrolStep(patrolPoint));
        patrolSteps.add(new PausePatrolStep(pauseLength));
    }

    public ArrayList<PatrolStep> getPatrolSteps() {
        return patrolSteps;
    }

    public void addPatrolStep(PatrolStep patrolStep) {
        this.patrolSteps.add(patrolStep);
    }

    public void addPatrolSteps(ArrayList<PatrolStep> patrolSteps) {
        this.patrolSteps.addAll(patrolSteps);
    }

    public void removePatrolStep(PatrolStep patrolStep) {
        this.patrolSteps.remove(patrolStep);
    }

    public PatrolStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(PatrolStep currentStep) {
        this.currentStep = currentStep;
    }
}
