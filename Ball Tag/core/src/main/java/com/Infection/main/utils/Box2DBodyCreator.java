package com.Infection.main.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/*
Util class for building a physics body in a certain transform with Box2D
 */

public class Box2DBodyCreator {

    public static Body createBody(int x, int y, int width, int height, boolean isStatic, World world)
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
}
