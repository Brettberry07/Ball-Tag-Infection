package com.libgdx.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;


public class Player {

    private final float GRAVITY_FORCE = 0.5f;

    //    private boolean isIt = false;
    private boolean isGrounded = false;
    private boolean canJump = false;
    private boolean isJumping = false;
    private final Vector2 position;
    private final Vector2 velocity = new Vector2(0,0);
    private final Vector2 acceleration = new Vector2(2.5f,0f);
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

    public void update(){
        this.vectorMovement();
        this.checkGrounded();
        this.gravity();
    }

    //gets the movement inputs and applies them
    private void vectorMovement(){

        final int SCREEN_WIDTH = 1280;
        final int SCREEN_HEIGHT = 720;
        final float SPEED = 10f;
        final float MAX_SPEED = 35.0f;
        final float FRICTION = 0.1f;
        final float JUMPFORCE = 32f;

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[0]))) {
            if(this.canJump) {
                this.isJumping = true;
                this.velocity.y += 1.5f * JUMPFORCE;
                this.canJump = false;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[1]))) {
            //checks if it will collide on the side of the screen
            if (this.position.x - (1* SPEED)*this.acceleration.x <= 0) {
                this.position.x = 0;
                this.velocity.x = 0;
            } else {
                this.velocity.x -= 1 * SPEED;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[2]))) {
            if (this.position.y - (1 * SPEED)*this.acceleration.x <= 0) {
                this.position.y = 0;
                this.velocity.y = 0;
            } else {
                this.velocity.y -= 1 * SPEED;
            }
        }


        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[3]))) {
            //we need to subtract the player width because the
            //coordinates are based off the bottom left of the sprite

            if (this.position.x + (1 * SPEED) * this.acceleration.x > SCREEN_WIDTH - sprite.getWidth()) {
                this.position.x = SCREEN_WIDTH - sprite.getWidth();
                this.velocity.x = 0;
            } else {
                this.velocity.x += 1 * SPEED;
            }

            this.acceleration.x = 0.2f;
        }


        if (!Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[0])) && !Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[1])) && !Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[2])) && !Gdx.input.isKeyPressed(Input.Keys.valueOf(controls[3]))) {
            this.acceleration.x = this.velocity.x * FRICTION;
        }

        this.velocity.x += this.acceleration.x;


        if(this.position.x + this.velocity.x <= 0){
            this.position.x = 0;
            this.velocity.x = 0;
        }
        else if(this.position.x + this.velocity.x > SCREEN_WIDTH - sprite.getWidth()){
            this.position.x = SCREEN_WIDTH - sprite.getWidth();
            this.velocity.x = 0;
        }
        else if(this.velocity.x > MAX_SPEED || this.velocity.x < MAX_SPEED *-1){
            if(this.velocity.x<0){
                this.velocity.x = MAX_SPEED *-1;
            }
            else{
                this.velocity.x = MAX_SPEED;
            }
            this.position.x += this.velocity.x;
            this.velocity.x *= FRICTION;
        }
        else{
            this.position.x += this.velocity.x;
            this.velocity.x *= FRICTION;
        }

//###################################Seperating X and Y velocity###########################################################################

        if(this.position.y + this.velocity.y <=0){
            this.position.y = 0;
            this.velocity.y = 0;
        }
        else if(this.position.y + this.velocity.y > SCREEN_HEIGHT - sprite.getHeight()){
            this.position.y = SCREEN_HEIGHT - sprite.getHeight();
            this.velocity.y = 0;
        }
        else if(this.velocity.y > JUMPFORCE || this.velocity.y < MAX_SPEED *-1){
            if(this.velocity.y<0){
                this.velocity.y = MAX_SPEED *-1;
            }
            else{
                this.position.y += this.velocity.y;
            }
        }
        else{
            this.position.y += this.velocity.y;
            this.velocity.y *= GRAVITY_FORCE;
        }

    }

    private void checkGrounded(){
        if(this.position.y<=0){
            this.isGrounded = true;
            this.canJump = true;
            this.position.y = 0;
        }
        else{
            this.isGrounded = false;
            this.isJumping = false;
        }
    }

    private void gravity(){
        if(!this.isGrounded && !this.isJumping) {
            this.velocity.y += -10*GRAVITY_FORCE;
        }
    }
}
