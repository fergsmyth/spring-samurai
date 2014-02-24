package com.genericgames.samurai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class Dialogue {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private List<Phrase> phrases = new LinkedList<Phrase>();
    BitmapFont font;
    private Phrase currentPhrase;
    int index = 0;
    private float time;

    public Dialogue(){

    }

    public boolean isFinished(){
        return time > 2 && endOfDialogue();
    }

    public void addPhrase(Phrase phrase){
        this.phrases.add(phrase);
    }

    public Collection<Phrase> getPhrases(){
        return phrases;
    }

    public Phrase getPhrase(float deltaTime){
        incrementTime(deltaTime);
        if(time > 1.5){
            System.out.println("Index : " + index + ", Phrase size : " + (phrases.size() - 1));
            if(!endOfDialogue()){
                currentPhrase = phrases.get(index);
                time = 0;
                index++;
            }
        }
        return currentPhrase;
    }

    private boolean endOfDialogue() {
        return index >= phrases.size();
    }

    private void incrementTime(float deltaTime) {
        this.time += deltaTime;
    }

    public void drawDialogue(float deltaTime, SpriteBatch batch){
        getPhrase(deltaTime);
        drawBackground();
        drawConversation();
    }

    private void drawConversation(){
        if(currentPhrase != null){
            font = new BitmapFont();
            Texture icon = new Texture(Gdx.files.internal("resources/icon/" + currentPhrase.getCharacter() + ".png"));
            float posX = Gdx.graphics.getWidth() / 4;
            float posY = Gdx.graphics.getHeight() / 3;
            SpriteBatch batch = new SpriteBatch();
            batch.begin();
            batch.draw(icon, posX + icon.getWidth() / 2, posY + icon.getHeight() / 7, icon.getWidth(), icon.getHeight());
            font.setScale(1f);
            font.draw(batch, currentPhrase.getPhrase(), posX + icon.getWidth() * 2, posY + icon.getHeight() / 3);
            batch.end();
        }
    }

    private void drawBackground(){
        float posX = Gdx.graphics.getWidth() / 4;
        float posY = Gdx.graphics.getHeight() / 3;
        float width = Gdx.graphics.getWidth() / 2;
        float height = Gdx.graphics.getHeight() / 3;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(posX, posY, width, height);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(posX, posY, width, height);
        shapeRenderer.end();
    }

}
