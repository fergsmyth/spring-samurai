package com.genericgames.samurai.hook;

import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.arena.ArenaLevelAttributes;
import com.genericgames.samurai.scoreboard.Score;
import com.genericgames.samurai.screens.ScreenManager;

public class OnArenaDeathHook implements OnDeathHook {
    private boolean hasFired;

    public void onDeath(SamuraiWorld world){
        if(!hasFired) {
            hasFired = true;
            ArenaLevelAttributes attributes = world.getCurrentLevel().getArenaLevelAttributes();
            if (attributes.isArenaLevel()) {
                if(attributes.getTotalNumEnemiesKilled() > 0) {
                    Score score = new Score("UNK", attributes.getRound().getRoundNum(), attributes.getTotalNumEnemiesKilled());
                    ScreenManager.manager.setEnterPlayerNameScreen(score);
                } else {
                    ScreenManager.manager.setScoreboardScreen();
                }
            }
        }
    }
}
