package com.Infection.main.views;

import com.Infection.main.GameObject;
import com.Infection.main.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.*;
import com.crashinvaders.vfx.effects.util.MixEffect;

public class GameView implements Disposable {

    private final VfxManager vfxManager;

    private final BloomEffect bloomEffect;
    private final FxaaEffect fxaaEffect;
    private final CrtEffect crtEffect;
    private final Texture bg;

    private Box2DDebugRenderer debugRenderer;

    /*
        This class holds all the rendering components for the Game
        It also facilitates all the shader renders as well as basis
        for rendering in general (ex: bg, debug, gameobejcts, etc)
     */

    public GameView()
    {
        debugRenderer = new Box2DDebugRenderer();

        vfxManager = new VfxManager(Pixmap.Format.RGB888);

        bloomEffect = new BloomEffect();
        fxaaEffect = new FxaaEffect();
        crtEffect = new CrtEffect();

        vfxManager.addEffect(bloomEffect);
        vfxManager.addEffect(fxaaEffect);
        vfxManager.addEffect(crtEffect);

        bg = new Texture("Images/untitled.png");
    }

    public void render(float delta, SpriteBatch batch, Array<GameObject> gameObjects, World world, Camera cam) {
        vfxManager.cleanUpBuffers();

        updateEffects(delta);

        vfxManager.beginInputCapture();
        batch.begin();
        batch.draw(bg, -Gdx.graphics.getWidth()/4f, -Gdx.graphics.getHeight()/4f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (GameObject thing: gameObjects)
        {
            thing.draw(batch);
        }
        batch.end();
        vfxManager.endInputCapture();

        vfxManager.applyEffects();
        vfxManager.renderToScreen();

        debugRenderer.render(world, cam.combined.scl(32f));
    }

    public OrthographicCamera createCam()
    {
        OrthographicCamera cam;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, (float) Gdx.graphics.getWidth() / 2.0f, (float) Gdx.graphics.getHeight() / 2.0f);
        return cam;
    }

    private void updateEffects(float delta)
    {
        bloomEffect.update(delta);
        fxaaEffect.update(delta);
        crtEffect.update(delta);
    }

    public void resize(int width, int height) {
        vfxManager.resize(width, height);
    }

    public void dispose()
    {
        bloomEffect.dispose();
        fxaaEffect.dispose();
        crtEffect.dispose();
        bg.dispose();
        debugRenderer.dispose();
    }
}
