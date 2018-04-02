package com.yetea.lgdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Barrier {

    private int openingLoc;
    private Stage stage;
    private Image top, bot;
    private int speed;
    private int x;

    //private static final Texture blackPixel = new Texture(Gdx.files.internal("black_pixel.png"));

    public Barrier(Stage aStage, int opening){
        stage = aStage;
        openingLoc = opening;
        float topBar = 100 - (openingLoc + 20);
        float botBar = openingLoc - 20;
        x = Gdx.graphics.getWidth();
        speed = -8;

        Texture blackPixel = new Texture(Gdx.files.internal("black_pixel.png"));
        Texture redPixel = new Texture(Gdx.files.internal("red_pixel.png"));
        top = new Image(blackPixel);
        bot = new Image(blackPixel);
        top.setSize(30, Gdx.graphics.getHeight()*(topBar/100));
        top.setPosition(x, Gdx.graphics.getHeight()-top.getHeight());
        bot.setSize(30, Gdx.graphics.getHeight()*(botBar/100));
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

    public void setX(int newX){
        x = newX;
        top.setX(x);
        bot.setX(x);
    }

    public int getX(){
        return x;
    }

    public void update(){
        x += speed;
        top.setX(x);
        bot.setX(x);
    }

}
