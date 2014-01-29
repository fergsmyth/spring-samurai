package com.genericgames.samurai.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.genericgames.samurai.model.PlayerCharacter;
import com.genericgames.samurai.model.SamuraiWorld;
import com.genericgames.samurai.model.movable.living.ai.Enemy;
import com.genericgames.samurai.physics.PhysicalWorldHelper;
import com.genericgames.samurai.utility.CoordinateSystem;

public class AIHelper {

    /**
     * Checks if a player is within an enemy's field of vision.
     * If he is, then check if there's a clear line of sight between the two.
     */
    public static void detectAIAwareness(SamuraiWorld samuraiWorld){
        for(Contact contact : samuraiWorld.getPhysicalWorld().getContactList()){
            if(contact.isTouching() && PhysicalWorldHelper.isBetweenPlayerAndEnemyFOV(contact)){
                Enemy enemy = PhysicalWorldHelper.getEnemy(contact);
                //TODO if clear line of sight to player
				enemy.setPlayerAware(true);
            }
        }
    }

    /**
     * handles all movement & combat for AI.
     */
    public static void handleAIActions(SamuraiWorld samuraiWorld) {
        //TODO handle AI combat
        handleAIMovement(samuraiWorld);
    }

    private static void handleAIMovement(SamuraiWorld samuraiWorld) {
        for(Enemy enemy : samuraiWorld.getEnemies()){
            if(enemy.isPlayerAware()){
                Body body = PhysicalWorldHelper.getBodyFor(enemy, samuraiWorld.getPhysicalWorld());
                //Look in player's direction:
                PlayerCharacter playerCharacter = samuraiWorld.getPlayerCharacter();

                Vector2 directionVector =  new Vector2(playerCharacter.getX() - enemy.getX(),
                        enemy.getY() - playerCharacter.getY());
                body.setTransform(body.getPosition(), CoordinateSystem.getRotationAngleInRadians(directionVector));
            }
        }
        //Set direction of movement
    }
}
