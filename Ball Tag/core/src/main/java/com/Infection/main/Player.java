package com.Infection.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;


public class Player{
    private Body pBody;
    private Texture pImg;

    private final String controls;

    //constructor
    public Player(int x, int y, World world, String controls) {
        pImg = new Texture("Images/Player.png");
        pBody = createBox(Gdx.graphics.getWidth()/4+x, Gdx.graphics.getHeight()/4+y, 32, 32, false, world); // This will be part of class later
        //super(pImg, pBody);
        this.controls = controls;

    }

    public static Body createBox(int x, int y, int width, int height, boolean isStatic, World world)
    {
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / 32f, y / 32f);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f / 32f, height / 2f / 32f);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }

    //draw on screen
    public void draw(SpriteBatch batch) {
        batch.draw(pImg, pBody.getPosition().x * 32f - (pImg.getWidth() / 2f), pBody.getPosition().y * 32f - (pImg.getHeight() / 2f));
    }

    public void update(float delta){
        this.inputUpdate(delta);
    }

    public void inputUpdate(float delta) { // again for loop each Player instance in class and update their horizontal forces and vertical forces
        int horizontalForce = 0;

        // p1
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(1))))) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(3))))) {
            horizontalForce += 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(0))))) {
            pBody.applyForceToCenter(0, 400, false);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.valueOf(String.valueOf(controls.charAt(2)))))
        {

        }

        pBody.setLinearVelocity(horizontalForce * 5, pBody.getLinearVelocity().y);
    }

    //@Override
    public void dispose() {
        pImg.dispose();
    }
}
