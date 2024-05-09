package com.Infection.main.screens;

import com.Infection.main.GameObject;
import com.Infection.main.Player;
import com.Infection.main.Settings;
import com.Infection.main.utils.Populator;
import com.Infection.main.views.GameView;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.Infection.main.utils.Box2DBodyCreator.createBody;

/*
This class is the Main part of the game in a sense
Compared to Main.java, GameScreen manages all the physics,
 music, and objects to be rendered for the main Game Screen
 */

public class GameScreen extends ScreenAdapter{
    private GameView gameView;
    private SpriteBatch batch;

    private OrthographicCamera cam;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Music theme;

    public Array<GameObject> gameObjects;

    @Override
    public void show() {
        batch = new SpriteBatch();

        gameObjects = new Array<>();

        gameView = new GameView();

        cam = gameView.createCam();

        world = new World(new Vector2(0, -9.8f), false);
        debugRenderer = new Box2DDebugRenderer();

        Populator.populate(world, gameObjects);

        theme = Gdx.audio.newMusic(Gdx.files.internal("Audio/Tag.wav"));
        theme.setLooping(true);
        theme.setVolume(0.05f);
        theme.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            Gdx.app.exit();
        }

        update(delta);

        gameView.render(delta, batch, gameObjects, world, cam);
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width / 2.0f, height / 2.0f);
        gameView.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();

        for (GameObject player: gameObjects)
        {
            player.dispose();
        }

        gameView.dispose();
        theme.dispose();
    }

    private void update(float delta)
    {
        world.step(1 / 60f, 6, 2);

        camUpdate();

        for (GameObject player : gameObjects)
        {
            player.update(delta);
        }
        batch.setProjectionMatrix(cam.combined);
    }

    private void camUpdate() // Camera controller
    {
        float pValx = Gdx.graphics.getWidth()/2f;
        float pValy = Gdx.graphics.getHeight()/2f;

        for (GameObject player : gameObjects)
        {
            pValx += player.getPosition().x;
            pValy += player.getPosition().y;
        }

        pValx *= 0.25f;
        pValy *= 0.25f;
        cam.position.set(new Vector2((pValx) * Settings.SCALE,(pValy) * Settings.SCALE ), Settings.SCALE);
        cam.update();
    }
}
