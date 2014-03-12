package com.genericgames.samurai;

public interface IDialogueManager {

    void initialiseDialogue(String dialogue);
    void renderPhrase();
    void previousPhrase();
    void nextPhrase();
    boolean isFinished();
}
