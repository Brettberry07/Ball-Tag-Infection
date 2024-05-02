package com.Infection.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class GameObject {

    public final Texture img;
    public final Body body;
    public final Vector2 position;
    public boolean visible;

    public GameObject(Texture img, Body body) {
        this.img = img;
        this.body = body;
        visible = true;
        position = new Vector2();
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
