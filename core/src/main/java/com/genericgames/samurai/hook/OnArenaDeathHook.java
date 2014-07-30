package com.genericgames.samurai.hook;

import com.genericgames.samurai.io.GameIO;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.arena.ArenaLevelAttributes;
import com.genericgames.samurai.scoreboard.Scoreboard;

public class OnArenaDeathHook implements OnDeathHook {
    private boolean hasFired;
    private Scoreboard scoreboard;

    public void onDeath(SamuraiWorld world){
        if(!hasFired) {
            hasFired = true;
            ArenaLevelAttributes attributes = world.getCurrentLevel().getArenaLevelAttributes();
            scoreboard = GameIO.getScoreboard();
            if (attributes.isArenaLevel()) {
                scoreboard.addToScoreBoard("TestName", attributes.getRound().getRoundNum(), attributes.getTotalNumEnemiesKilled());
                GameIO.saveScoreboard(scoreboard);
            }
        }
    }

    private void loadScoreBoard(){

    }
}
