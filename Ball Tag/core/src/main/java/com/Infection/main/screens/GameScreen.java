package com.Infection.main.screens;

import com.Infection.main.GameObject;
import com.Infection.main.Player;
import com.Infection.main.views.GameView;
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

public class GameScreen extends ScreenAdapter{
    private GameView gameView;
    private SpriteBatch batch;
//    private Texture img;

    private OrthographicCamera cam;
    private Player player;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Music theme;

    private Array<GameObject> gameObjects;

    @Override
    public void show() {
        batch = new SpriteBatch();

        gameView = new GameView();

        cam = gameView.createCam();

        world = new World(new Vector2(0, -9.8f), false);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(8, 25, world, "WASD");
        Body platform = createBox(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4 - 50, 512, 32, true, world); // gonna be part of tilemap later

        theme = Gdx.audio.newMusic(Gdx.files.internal("Audio/Tag.wav"));
        theme.setLooping(true);
        theme.play();
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            Gdx.app.exit();
        }

        update(delta);

        // This should become a for loop of each iamge we genreate
        gameView.render(delta, batch, player);

        debugRenderer.render(world, cam.combined.scl(32f));
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width / 2.0f, height / 2.0f);
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
        player.dispose();
        gameView.dispose();
        theme.dispose();
    }

    private void update(float delta)
    {
        world.step(1 / 60f, 6, 2);

        cam.update();

        player.update(delta);
        batch.setProjectionMatrix(cam.combined);
    }
}
