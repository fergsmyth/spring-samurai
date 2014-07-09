package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.math.Rectangle;
import com.genericgames.samurai.maths.RandomPointFromRect;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.model.RandomSpaceEmitter;
import com.genericgames.samurai.model.timeinterval.FixedTimeInterval;
import com.genericgames.samurai.screens.WorldRenderer;

public class WeatherProvider {

    public static RandomSpaceEmitter getWeatherEmitter(Weather weather, Level level){
        RandomSpaceEmitter correspondingWeatherEmitter;
        float positionX = level.getPlayerCharacter().getX() - WorldRenderer.getCameraWidth();
        float positionY = level.getPlayerCharacter().getY() - WorldRenderer.getCameraHeight();
        switch (weather){
            case RAIN:
                correspondingWeatherEmitter = new RandomSpaceEmitter(
                        new SnowFlake.SnowFlakeFactory(),
                        positionX, positionY,
                        level.getWind().getDirection().cpy().scl(level.getWind().getSpeed()), 45, true,
                        new FixedTimeInterval(1),
                        new RandomPointFromRect(new Rectangle(positionX, positionY,
                                WorldRenderer.getCameraWidth()*2, WorldRenderer.getCameraHeight()*2)));
                break;
            case SNOW:
                correspondingWeatherEmitter = new RandomSpaceEmitter(
                        new SnowFlake.SnowFlakeFactory(),
                        positionX, positionY,
                        level.getWind().getDirection().cpy().scl(level.getWind().getSpeed()), 45, true,
                        new FixedTimeInterval(1),
                        new RandomPointFromRect(new Rectangle(positionX, positionY,
                                WorldRenderer.getCameraWidth()*2, WorldRenderer.getCameraHeight()*2)));
                break;
            default:
                correspondingWeatherEmitter = null;
                break;
        }
        return correspondingWeatherEmitter;
    }
}
