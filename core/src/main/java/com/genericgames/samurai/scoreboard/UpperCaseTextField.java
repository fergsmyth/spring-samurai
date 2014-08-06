package com.genericgames.samurai.scoreboard;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class UpperCaseTextField extends TextField{

    public UpperCaseTextField (String text, Skin skin) {
        super(text, skin.get(TextFieldStyle.class));
    }

    @Override
    protected InputListener createInputListener() {
        return new UpperCaseTextFieldInputListener();
    }

    class UpperCaseTextFieldInputListener extends TextFieldClickListener {
        @Override
        public boolean keyTyped (InputEvent event, char character) {
            if (Character.isLetter(character) && Character.isLowerCase(character)){
                character = Character.toUpperCase(character);
            }
            return super.keyTyped(event, character);
        }
    }
}
