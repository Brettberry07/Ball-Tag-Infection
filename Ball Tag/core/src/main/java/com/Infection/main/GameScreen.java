package com.Infection.main.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
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
    private SpriteBatch batch;
    private Texture img;

    private OrthographicCamera cam;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body player;
    private Body platform;

    @Override
    public void show() {
        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, (float) Gdx.graphics.getWidth() / SCALE, (float) Gdx.graphics.getHeight() / SCALE);

        world = new World(new Vector2(0, -9.8f), false);
        debugRenderer = new Box2DDebugRenderer();

        player = createBox(8, 65, 32, 32, false); // This will be part of class later
        platform = createBox(0, 0, 64, 32, true);

        img = new Texture("Player.png");
    }

    public void inputUpdate(float delta) {
        int horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.applyForceToCenter(0, 300, false);
        }

        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
    }

    private void updateCamera(float delta)
    {
        Vector3 position = cam.position;

        position.x = player.getPosition().x * PixelsPerMeter;
        position.y = player.getPosition().y * PixelsPerMeter;
        cam.position.set(position);

        cam.update();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            Gdx.app.exit();
        }

        update(delta);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, player.getPosition().x * PixelsPerMeter - (img.getWidth() / 2f), player.getPosition().y * PixelsPerMeter - (img.getHeight() / 2f));
        batch.end();

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

        updateCamera(delta);
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
