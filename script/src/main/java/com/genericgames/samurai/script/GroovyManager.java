package com.genericgames.samurai.script;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;

public class GroovyManager implements ScriptManager {

    private final String GROOVY_ROOT[] = {"src/main/scripts"};
    private GroovyScriptEngine engine;

    public GroovyManager() throws IOException {
        engine = new GroovyScriptEngine(GROOVY_ROOT);
    }

    @Override
    public void execute(Script scriptToRun) throws ResourceException, ScriptException {
        Object object = engine.run(scriptToRun.getPath(), scriptToRun.getBinding());
        scriptToRun.setResult(object);
    }
}
