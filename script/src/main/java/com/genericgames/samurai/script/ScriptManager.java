package com.genericgames.samurai.script;

import groovy.util.ResourceException;
import groovy.util.ScriptException;

public interface ScriptManager {
    void execute(Script scriptToRun) throws ResourceException, ScriptException;
}
