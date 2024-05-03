package com.Infection.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;

public class GameObject implements Disposable {
    public final Texture img;
    public final Body body;

    public GameObject(Texture img, Body body) {
        this.img = img;
        this.body = body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void dispose()
    {
        img.dispose();
    }
}
