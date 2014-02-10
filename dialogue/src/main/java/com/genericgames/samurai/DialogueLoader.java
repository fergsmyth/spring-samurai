package com.genericgames.samurai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;

public class DialogueLoader {

    public Dialogue loadDialogue(){
        Dialogue dialogue = new Dialogue();
        XmlReader reader = new XmlReader();
        try {
            XmlReader.Element element = getRootElement(reader);
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

    private XmlReader.Element getRootElement(XmlReader reader) throws IOException {
        FileHandle handle = Gdx.files.internal("src/main/resources/npc/dialogue.xml");
        return reader.parse(handle);
    }

    public Array<XmlReader.Element> getStageElements(XmlReader.Element element) {
        return element.getChildrenByName("stage");
    }

    public Array<XmlReader.Element> getPhraseElements(XmlReader.Element element) {
        return element.getChildrenByName("phrase");
    }

}
