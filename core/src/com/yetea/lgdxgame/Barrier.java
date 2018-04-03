package com.yetea.lgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import org.w3c.dom.css.Rect;


public class Barrier {

    private int openingLoc;
    private Stage stage;
    private Image top, bot;
    private int speed;
    private int x, topY, botY, topHeight, botHeight;
    public final int WIDTH = 50;
    private static final int OPENING_BUFFER = 17;
    private boolean scored;

    //private static final Texture blackPixel = new Texture(Gdx.files.internal("black_pixel.png"));

    public Barrier(Stage aStage, int opening){
        stage = aStage;
        openingLoc = opening;
        float topBar = 100 - (openingLoc + OPENING_BUFFER);
        float botBar = openingLoc - OPENING_BUFFER;
        x = Gdx.graphics.getWidth();
        speed = 10;
        scored = false;

        Texture blackPixel = new Texture(Gdx.files.internal("black_pixel.png"));
        Texture redPixel = new Texture(Gdx.files.internal("red_pixel.png"));
        top = new Image(blackPixel);
        bot = new Image(blackPixel);
        topHeight = (int)(Gdx.graphics.getHeight()*(topBar/100));
        top.setSize(WIDTH, topHeight);
        topY = (int)(Gdx.graphics.getHeight() - top.getHeight());
        top.setPosition(x, topY);
        botHeight = (int)(Gdx.graphics.getHeight()*(botBar/100));
        bot.setSize(WIDTH, botHeight);
        bot.setPosition(x, 0);

        stage.addActor(top);
        stage.addActor(bot);
    }

    public void setZIndex(int newZ){
        top.setZIndex(newZ);
        bot.setZIndex(newZ);
    }

    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }

    public void incSpeed(){
        speed++;
    }

    public void decSpeed(){
        speed--;
    }

    public int getSpeed(){
        return speed;
    }

    public void setX(int newX){
        x = newX;
        top.setX(x);
        bot.setX(x);
    }

    public int getX(){
        return x;
    }

    public void update(){
        x -= speed;
        top.setX(x);
        bot.setX(x);
    }

    public void yesScore(){
        scored = true;
    }

    public void noScore(){
        scored = false;
    }

    public boolean hasScored(){
        return scored;
    }

    public boolean collidesWith(Player player){
        float playerY = player.getY();
        float playerX = player.getX();
        float playerHeight = player.getHeight();
        float playerWidth = player.getWidth();
        //Check bottom barrier
        if ((x < playerX + playerWidth && 0 < playerY + playerHeight && x + WIDTH > playerX && 0 + botHeight > playerY) ||
                //Check top barrier
                (x < playerX + playerWidth && topY < playerY + playerHeight && x + WIDTH > playerX && topY + topHeight > playerY)){
                scored = true; // This is in place to prevent the barrier from giving a score to the player
                return true;
        }
        return false;
    }

}
