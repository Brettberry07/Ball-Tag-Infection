package com.Infection.main.utils;

import com.Infection.main.GameObject;
import com.Infection.main.Player;
import com.Infection.main.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.Infection.main.Settings;

import static com.Infection.main.utils.Box2DBodyCreator.createBody;

/*
Populates the world in GameScreen with all objects, players, collidables, etc.
 */

public class Populator {
    public static void populate(World world, Array<GameObject> gameObjects){
        Body platform = createBody(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4 - 50, 1280, 32, true, world);

        for (int i = 0; i < Settings.num_players; i++)
        {
            gameObjects.add(new Player(4 * (i+1),25, world, Settings.controlls[i]));
        }
    }
}
