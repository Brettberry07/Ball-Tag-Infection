package com.Infection.main.screens;

import com.Infection.main.views.GameView;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

import java.security.Key;

import static com.Infection.main.utils.Settings.PixelsPerMeter;

public class GameScreen extends ScreenAdapter {
    private final float SCALE = 2.0f;
    private GameView gameView;
    private SpriteBatch batch;
    private Texture img;

    private OrthographicCamera cam;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body player;
    private Body player1;
    private Body platform;

    private Music theme;

    @Override
    public void show() {
        batch = new SpriteBatch();

        gameView = new GameView();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, (float) Gdx.graphics.getWidth() / SCALE, (float) Gdx.graphics.getHeight() / SCALE);

        world = new World(new Vector2(0, -9.8f), false);
        debugRenderer = new Box2DDebugRenderer();

        player = createBox(Gdx.graphics.getWidth()/4+8, Gdx.graphics.getHeight()/4+25, 32, 32, false); // This will be part of class later
        player1 = createBox(Gdx.graphics.getWidth()/4 -8, Gdx.graphics.getHeight()/4+25, 32, 32, false);
        platform = createBox(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4-50, 512, 32, true);

        img = new Texture("Player.png");

        theme = Gdx.audio.newMusic(Gdx.files.internal("GameOver.wav"));
        theme.setLooping(true);
        theme.play();
    }

    public void inputUpdate(float delta) { // again for loop each Player instance in class and update their horizontal forces and vertical forces
        int horizontalForce = 0;
        int horizontalForce1 = 0;

        // p1
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.applyForceToCenter(0, 400, false);
        }

        // p2
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            horizontalForce1 -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            horizontalForce1 += 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player1.applyForceToCenter(0, 400, false);
        }

        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
        player1.setLinearVelocity(horizontalForce1 * 5, player1.getLinearVelocity().y);
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
        gameView.render(delta, batch, img, player, player1);

        debugRenderer.render(world, cam.combined.scl(PixelsPerMeter));
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width / SCALE, height / SCALE);
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
        img.dispose();
    }

    private void update(float delta)
    {
        world.step(1 / 60f, 6, 2);

        cam.update();

        inputUpdate(delta);
        batch.setProjectionMatrix(cam.combined);
    }

    private Body createBox(int x, int y, int width, int height, boolean isStatic)
    {
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PixelsPerMeter, y / PixelsPerMeter);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f / PixelsPerMeter, height / 2f / PixelsPerMeter);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }
}
