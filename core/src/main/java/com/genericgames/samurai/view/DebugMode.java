package com.genericgames.samurai.view;

public class DebugMode {

    private static boolean DEBUG_ENABLED;

    public static void toggleDebugMode(){
        DEBUG_ENABLED = !DEBUG_ENABLED;
    }

    public static boolean isDebugEnabled(){
        return DEBUG_ENABLED;
    }

}
