package com.genericgames.samurai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

public class DialogueLoader {

    private static DialogueLoader loader;

    private DialogueLoader(){}

    public static DialogueLoader loader(){
        if(loader == null){
            loader = new DialogueLoader();
        }
        return loader;
    }

    public Dialogue loadDialogue(String dialogueFile){
        Dialogue dialogue = new Dialogue();
        XmlReader reader = new XmlReader();
        try {
            XmlReader.Element element = getRootElement(reader, dialogueFile);
            for(XmlReader.Element stageElement : getStageElements(element)){
                for(XmlReader.Element phraseElement : getPhraseElements(stageElement)){
                    dialogue.addDialogue(phraseElement.getText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dialogue;
    }

    private XmlReader.Element getRootElement(XmlReader reader, String dialogueFile) throws IOException {
        System.out.println("Local storage path : " + Gdx.files.getLocalStoragePath());
        System.out.println("External storage path : " + Gdx.files.getExternalStoragePath());
        for(FileHandle fileHandle : Gdx.files.internal(".").list()){
            System.out.println(fileHandle.name());
        }
        FileHandle handle = Gdx.files.internal("../dialogue/src/main/resources/npc/" + dialogueFile);
        return reader.parse(handle);
    }

    public Array<XmlReader.Element> getStageElements(XmlReader.Element element) {
        return element.getChildrenByName("stage");
    }

    public Array<XmlReader.Element> getPhraseElements(XmlReader.Element element) {
        return element.getChildrenByName("phrase");
    }

}
