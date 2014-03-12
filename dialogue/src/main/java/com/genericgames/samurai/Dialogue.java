package com.genericgames.samurai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class Dialogue {

    private ShapeRenderer shapeRenderer;
    private List<Phrase> phrases = new LinkedList<Phrase>();
    private Phrase currentPhrase;
    private BitmapFont font;
    private int index = 0;
    private float time;

    public Dialogue(){
        shapeRenderer = new ShapeRenderer();
    }

    public boolean isFinished(){
        return timeoutReached() && dialogueFinished();
    }

    private boolean timeoutReached() {
        return time > 2;
    }

    public void addPhrase(Phrase phrase){
        this.phrases.add(phrase);
    }

    public List<Phrase> getPhrases(){
        return phrases;
    }

    public void drawDialogue(float deltaTime){
        updatePhrase(deltaTime);
        drawBackground();
        drawConversation();
    }

    private void updatePhrase(float deltaTime){
        incrementTime(deltaTime);
        if(time > 1.5){
            if(!dialogueFinished()){
                currentPhrase = phrases.get(index);
                time = 0;
                index++;
            }
        }
    }

    private boolean dialogueFinished() {
        return index >= phrases.size();
    }

    private void incrementTime(float deltaTime) {
        this.time += deltaTime;
    }

    private void drawConversation(){
        if(currentPhrase != null){
            font = new BitmapFont();
            Texture icon = new Texture(Gdx.files.internal("resources/icon/" + currentPhrase.getCharacter() + ".png"));
            float posX = getCornerX();
            float posY = getCornerY();
            SpriteBatch batch = new SpriteBatch();
            batch.begin();
            batch.draw(icon, posX + icon.getWidth() / 2, posY + icon.getHeight() / 7, icon.getWidth(), icon.getHeight());
            font.setScale(1f);
            font.draw(batch, currentPhrase.getPhrase(), posX + icon.getWidth() * 2, posY + icon.getHeight() / 3);
            batch.end();
        }
    }

    private void drawBackground(){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(getCornerX(), getCornerY(), getBoxWidth(), getBoxHeight());
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(getCornerX(), getCornerY(), getBoxWidth(), getBoxHeight());
        shapeRenderer.end();
    }

    private int getBoxHeight() {
        return Gdx.graphics.getHeight() / 3;
    }

    private int getBoxWidth() {
        return Gdx.graphics.getWidth() / 2;
    }

    private int getCornerY() {
        return Gdx.graphics.getHeight() / 3;
    }

    private int getCornerX() {
        return Gdx.graphics.getWidth() / 4;
    }

}
