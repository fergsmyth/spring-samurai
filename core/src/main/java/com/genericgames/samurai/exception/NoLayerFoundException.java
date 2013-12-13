package com.genericgames.samurai.exception;

public class NoLayerFoundException extends RuntimeException {

    public NoLayerFoundException(String layerName){
        super("The layer " + layerName + " does not exist");
    }
}
