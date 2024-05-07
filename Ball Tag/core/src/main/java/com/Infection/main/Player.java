package com.Infection.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

import static com.Infection.main.utils.Box2DBodyCreator.createBody;

public class Player extends GameObject{

    private final String controls;
    int horizontalForce = 0;

    //constructor
    public Player(int x, int y, World world, String controls) {
        super(new Texture("Images/Player.png"), createBody(Gdx.graphics.getWidth()/4+x, Gdx.graphics.getHeight()/4+y, 32, 32, false, world));

        this.controls = controls;

    }

    @Override
    public void update(float delta){
        this.inputUpdate(delta);
    }

    public void inputUpdate(float delta) {
        this.horizontalForce = 0;

        // 0 = UP | 1 = LEFT | 2 = DOWN | 3 = RIGHT

        // p1
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(1))))) {
            this.horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(3))))) {
            this.horizontalForce += 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(0))))) {
            this.body.applyForceToCenter(0, 400, false);
        }

        this.body.setLinearVelocity(this.horizontalForce * 5, this.body.getLinearVelocity().y);
    }
}
