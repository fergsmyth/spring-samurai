package com.genericgames.samurai.ai.patrolpattern;

import java.util.ArrayList;

public class PatrolPatternGroup {

    private ArrayList<PatrolPattern> patrolPatterns = new ArrayList<PatrolPattern>();
    private PatrolStep currentStep;

    public PatrolPatternGroup(){
    }

    public PatrolPatternGroup(ArrayList<PatrolPattern> patrolPatterns){
        addPatrolPatterns(patrolPatterns);
    }

    public void addPatrolPattern(PatrolPattern patrolPattern) {
        patrolPatterns.add(patrolPattern);
    }

    public void addPatrolPatterns(ArrayList<PatrolPattern> patrolPatterns) {
        this.patrolPatterns.addAll(patrolPatterns);
    }

    public ArrayList<PatrolPattern> getPatrolPatterns() {
        return patrolPatterns;
    }

    public void removePatrolPattern(PatrolPattern patrolPattern) {
        this.patrolPatterns.remove(patrolPattern);
    }

    public PatrolStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(PatrolStep currentStep) {
        this.currentStep = currentStep;
    }

    public PatrolStep getNextStep(PatrolStep currentPatrolStep){
        PatrolPattern currentPatrolPattern = getCorrespondingPatrolPattern(currentPatrolStep);
        int indexOfNewStep = (currentPatrolPattern.getPatrolSteps().indexOf(currentPatrolStep)+1)
                % currentPatrolPattern.getPatrolSteps().size();

        //If the new step is on the same pattern
        if(indexOfNewStep > 0){
            return currentPatrolPattern.getPatrolSteps().get(indexOfNewStep);
        }
        else {
            //Get first step of next patrolPattern
            return getNextPatrolPattern(currentPatrolPattern).getPatrolSteps().get(indexOfNewStep);
        }
    }

    private PatrolPattern getNextPatrolPattern(PatrolPattern currentPatrolPattern) {
        int indexOfNewPatrolPattern = (getPatrolPatterns().indexOf(currentPatrolPattern)+1)
                % getPatrolPatterns().size();
        return getPatrolPatterns().get(indexOfNewPatrolPattern);
    }

    private PatrolPattern getCorrespondingPatrolPattern(PatrolStep patrolStep) {
        for(PatrolPattern patrolPattern : getPatrolPatterns()){
            for(PatrolStep step : patrolPattern.getPatrolSteps()){
                if(step.equals(patrolStep)){
                    return patrolPattern;
                }
            }
        }
        throw new IllegalArgumentException("No matching patrol pattern found");
    }
}
