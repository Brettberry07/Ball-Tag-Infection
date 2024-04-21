package com.libgdx.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;


public class Player {
    private final int SCREEN_HEIGHT = 720;
    private final int SCREEN_WIDTH = 1280;
    private float SPEED = 2.5f;
    private boolean isIt = false;
    private float JUMPFORCE = 5f;
    private final Vector2 position;
    private final Sprite sprite;
    private final String[] controls;


    //constructor
    public Player(Texture img, String[] controls ){
        this.sprite = new Sprite(img);
        this.position = new Vector2((float) Gdx.graphics.getWidth()/2,0);
        this.sprite.setScale(1);
        this.controls = controls;
    }

    //draw on screen
    public void Draw(SpriteBatch batch){
        this.sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void setPosition(int x, int y){
        this.position.x = x;
        this.position.y = y;
    }

    public void setSpeed(float speed){
        this.SPEED=speed;
    }

    public void movement() {

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[3]))) {
                //we need to subtract the player width because the
                //coordinates are based off the bottom left of the sprite

                if (position.x + 5 * SPEED > SCREEN_WIDTH - sprite.getWidth()) {
                    this.position.x = SCREEN_WIDTH - sprite.getWidth();
                } else {
                    this.position.x += 5 * SPEED;
                }
            }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[1]))) {
            if (position.x - 5 * SPEED <= 0) {
                this.position.x = 0;
            } else {
                this.position.x -= 5 * SPEED;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[0]))) {
            //We also repeat the same steps

            if (position.y + 5 * SPEED > SCREEN_HEIGHT - sprite.getHeight()) {
                this.position.y = SCREEN_HEIGHT - sprite.getHeight();
            } else {
                this.position.y += 5 * SPEED;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[2]))) {
            if (position.y - 5 * SPEED <= 0) {
                this.position.y = 0;
            } else {
                this.position.y -= 5 * SPEED;
            }
        }
    }
}

