package com.genericgames.samurai.script;

import groovy.lang.GString;

import static org.junit.Assert.assertEquals;

public class GroovyManagerTest {

    GroovyManager manager;
    @org.junit.Before
    public void setUp() throws Exception {
        manager = new GroovyManager();
    }

    @org.junit.Test
    public void testHelloWorldScript() throws Exception {
        Script<String, GString> script = new Script<String, GString>("HelloWorld.groovy");
        script.setBinding("input", "world");
        manager.execute(script);
        assertEquals("Hello, world!", script.getResult().toString());
    }
}
