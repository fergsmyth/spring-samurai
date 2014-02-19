package com.genericgames.samurai;

import com.genericgames.samurai.model.Icon;
import com.genericgames.samurai.utility.ImageCache;

public class IconFactory {

    public static Icon createConversationIcon(float x, float y){
        return new Icon(x, y, ImageCache.conversationIcon);
    }
}
