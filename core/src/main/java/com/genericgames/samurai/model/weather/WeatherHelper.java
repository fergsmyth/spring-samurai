package com.genericgames.samurai.model.weather;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.genericgames.samurai.model.Level;
import com.genericgames.samurai.screens.WorldRenderer;

public class WeatherHelper {

    public static final float RAIN_SPEED_SCALAR = 2.0f;
    public static final float SNOW_SPEED_SCALAR = 2.0f;

    public static Vector2 getWeatherEmitterPosition(Level level){
        return new Vector2(level.getPlayerCharacter().getX() - WorldRenderer.getCameraWidth(),
                level.getPlayerCharacter().getY() - WorldRenderer.getCameraHeight());
    }

    public static Rectangle getWeatherEmitterSpace(Level level){
        Vector2 emitterPosition = getWeatherEmitterPosition(level);
        return new Rectangle(emitterPosition.x, emitterPosition.y,
                WorldRenderer.getCameraWidth()*2, WorldRenderer.getCameraHeight()*2);
    }
}
