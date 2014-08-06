package com.genericgames.samurai.hook;

import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.arena.ArenaLevelAttributes;
import com.genericgames.samurai.scoreboard.Score;
import com.genericgames.samurai.scoreboard.Scoreboard;
import com.genericgames.samurai.screens.ScreenManager;

public class OnArenaDeathHook implements OnDeathHook {
    private boolean hasFired;
    private Scoreboard scoreboard;

    public void onDeath(SamuraiWorld world){
        if(!hasFired) {
            hasFired = true;
            ArenaLevelAttributes attributes = world.getCurrentLevel().getArenaLevelAttributes();
            scoreboard = GameIO.getScoreboard();
            if (attributes.isArenaLevel()) {
                Score score = new Score("UNK", attributes.getRound().getRoundNum(), attributes.getTotalNumEnemiesKilled());
                ScreenManager.manager.setEnterPlayerNameScreen(score);
            }
        }
    }
}
