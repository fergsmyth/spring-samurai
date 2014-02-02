package com.genericgames.samurai.script;

import groovy.lang.Binding;

public class Script<T, E> {

    private Binding binding;
    private String scriptLocation;
    private E result;

    public Script(String scriptLocation){
        this.scriptLocation = scriptLocation;
    }

    public String getPath() {
        return scriptLocation;
    }

    public void setResult(E result){
        this.result = result;
    }

    public E getResult(){
        return result;
    }

    public void setBinding(String variableToBind, Object variableValue){
        binding = new Binding();
        binding.setVariable(variableToBind, variableValue);
    }

    public Binding getBinding(){
        return binding;
    }
}
