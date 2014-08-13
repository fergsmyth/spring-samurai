package com.genericgames.samurai;

import com.genericgames.samurai.model.DialogueIcon;
import com.genericgames.samurai.model.Icon;
import com.genericgames.samurai.utility.ImageCache;

public class IconFactory {

    public static DialogueIcon createConversationIcon(float x, float y, String dialogue){
        return new DialogueIcon(x, y, ImageCache.conversationIcon, dialogue, 1f);
    }

    public static Icon createHealthIcon(float x, float y, float scalingFactorX, float scalingFactorY){
        return new Icon(x, y, ImageCache.healthIcon, scalingFactorX, scalingFactorY);
    }

    public static Icon createSwordIcon(float x, float y, float scalingFactorX, float scalingFactorY){
        return new Icon(x, y, ImageCache.swordIcon, scalingFactorX, scalingFactorY);
    }

    public static Icon createBowIcon(float x, float y, float scalingFactor, float scalingFactorY){
        return new Icon(x, y, ImageCache.bowIcon, scalingFactor, scalingFactorY);
    }
}
