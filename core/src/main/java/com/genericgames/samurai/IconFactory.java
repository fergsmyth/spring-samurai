package com.genericgames.samurai;

import com.genericgames.samurai.model.DialogueIcon;
import com.genericgames.samurai.model.Icon;
import com.genericgames.samurai.utility.ImageCache;

public class IconFactory {

    public static DialogueIcon createConversationIcon(float x, float y, String dialogue){
        return new DialogueIcon(x, y, ImageCache.conversationIcon, dialogue, 0.05f);
    }

    public static Icon createHeartIcon(float x, float y, float scalingFactor){
        return new Icon(x, y, ImageCache.heartIcon, scalingFactor);
    }
}
