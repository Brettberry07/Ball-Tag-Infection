package com.Infection.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;


public class Player implements Disposable{



    //    private boolean isIt = false;
    private boolean isGrounded = false;
    private boolean isJumping = false;
    private final Vector2 position;
    private final Vector2 velocity = new Vector2(0,0);
    private final Vector2 acceleration = new Vector2(1.5f,1f);
    private final Sprite sprite;
    private final String[] controls;


    //constructor
    public Player(Texture img, String[] controls) {
        this.sprite = new Sprite(img);
        this.position = new Vector2((float) Gdx.graphics.getWidth() / 2, 0);
        this.sprite.setScale(1);
        this.controls = controls;
    }

    //draw on screen
    public void draw(SpriteBatch batch) {
        this.sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    //sets the position of the sprite, cords are bottom left
    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void update(float delta){
        this.vectorMovement(delta);
        this.checkGrounded();
    }


    public void vectorMovement(float deltaTime){
        final int SCREEN_WIDTH = 1280;
        final int SCREEN_HEIGHT = 720;
        final float SPEED = 10f;
        final float JUMPFORCE = 750f;
        final float GRAVITY_FORCE = -0.5f;

        if(Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[0]))) {
            if(isGrounded){
                this.velocity.y = JUMPFORCE*deltaTime;
                this.isJumping = !this.isJumping;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[1]))){
            this.velocity.x = -SPEED;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[3]))){
            this.velocity.x = SPEED;
        }


        this.velocity.x *= this.acceleration.x;
        this.velocity.y *= this.acceleration.y;

        if(this.position.x + this.velocity.x > SCREEN_WIDTH - sprite.getWidth()){
            this.position.x = SCREEN_WIDTH - sprite.getWidth();
        }
        if(this.position.x + this.velocity.x < 0){
            this.position.x = 0;
        }
        if(this.position.y + this.velocity.y > SCREEN_HEIGHT){
            this.position.y = SCREEN_HEIGHT;
        }
        if(this.position.y + this.velocity.y < 0){
            this.position.y = 0;
        }

        if(!this.isGrounded){
            this.velocity.y += GRAVITY_FORCE;
        }

        this.position.add(this.velocity);
        this.velocity.x = 0;


    }

    private void checkGrounded(){
        if(this.position.y<=0){
            this.isGrounded = true;
            this.isJumping = false;
            this.position.y = 0;
        }
        else{
            this.isGrounded = false;
        }
    }

    @Override
    public void dispose() {}
}
