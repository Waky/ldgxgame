package com.yetea.lgdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image {

    private int speedY, velY;
    private boolean isFalling, isRising;

    public Player(Texture tex){
        super(tex);
        speedY = velY = 0;
        isFalling = true;
        isRising = false;
    }

    public void startRise(){
        isRising = true;
        isFalling = false;
    }

    public void startFall(){
        isFalling = true;
        isRising = false;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        if (isRising) if (velY<6) velY++;
        if (isFalling) if (velY>-10) velY--;
        this.setY(getY()+velY);
    }
}
