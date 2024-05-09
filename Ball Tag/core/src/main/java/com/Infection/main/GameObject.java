package com.Infection.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

/*
This class is an abstract class for all GameObjects
Each GameObject contains an image and a Box2D collision body

Examples would be the Player, platforms
(tho this will be done in populator and tile map),
and possibly a ball that we can shoot
 */

public abstract class GameObject implements Disposable {
    public final Texture img;
    public final Body body;

    public GameObject(Texture img, Body body) {
        this.img = img;
        this.body = body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.img, this.body.getPosition().x * 32f - (this.img.getWidth() / 2f), this.body.getPosition().y * 32f - (this.img.getHeight() / 2f));
    }

    public void update(float delta) {} // To be overriden

    public void dispose()
    {
        img.dispose();
    }
}