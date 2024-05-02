package com.Infection.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.*;
import com.crashinvaders.vfx.effects.util.MixEffect;
import com.Infection.main.Player;

import static com.Infection.main.uitls.Settings.PixelsPerMeter;
import static com.Infection.main.uitls.Settings.SCALE;

public class GameView implements Disposable {

    private final VfxManager vfxManager;

    private final BloomEffect bloomEffect;
    private final FxaaEffect fxaaEffect;
    private final MotionBlurEffect motionBlurEffect;
    private final CrtEffect crtEffect;
    private final OldTvEffect oldTvEffect;
    private final Texture bg;

    private Box2DDebugRenderer debugRenderer;

    /*
        We will need to make a GameObjects array
        that will be iterated and drawn soon, one
        player may be okay but once we add two or
        more it'll be quite hectic to manage this codebase
     */

    public GameView()
    {
        debugRenderer = new Box2DDebugRenderer();

        vfxManager = new VfxManager(Pixmap.Format.RGB888);

        bloomEffect = new BloomEffect();
        fxaaEffect = new FxaaEffect();
        motionBlurEffect = new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MAX, 0.25f);
        crtEffect = new CrtEffect();
        oldTvEffect = new OldTvEffect();

        vfxManager.addEffect(bloomEffect);
        vfxManager.addEffect(fxaaEffect);
        vfxManager.addEffect(motionBlurEffect);
        vfxManager.addEffect(crtEffect);
        vfxManager.addEffect(oldTvEffect);

        bg = new Texture("untitled.png");
    }

    public void render(float delta, SpriteBatch batch, Texture img, Body player, Body player1) {
        vfxManager.cleanUpBuffers();

        updateEffects(delta);

        vfxManager.beginInputCapture();
        batch.begin();
        batch.draw(bg, -Gdx.graphics.getWidth()/4f, -Gdx.graphics.getHeight()/4f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(img, player.getPosition().x * PixelsPerMeter - (img.getWidth() / 2f), player.getPosition().y * PixelsPerMeter - (img.getHeight() / 2f));
        batch.draw(img, player1.getPosition().x * PixelsPerMeter - (img.getWidth() / 2f), player1.getPosition().y * PixelsPerMeter - (img.getHeight() / 2f));
        batch.end();
        vfxManager.endInputCapture();

        vfxManager.applyEffects();
        vfxManager.renderToScreen();
    }

    public OrthographicCamera createCam()
    {
        OrthographicCamera cam;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, (float) Gdx.graphics.getWidth() / SCALE, (float) Gdx.graphics.getHeight() / SCALE);
        return cam;
    }

    private void updateEffects(float delta)
    {
        bloomEffect.update(delta);
        fxaaEffect.update(delta);
        motionBlurEffect.update(delta);
        crtEffect.update(delta);
        //oldTvEffect.update(delta);
    }

    public void resize(int width, int height) {
        vfxManager.resize(width, height);
    }

    public void dispose()
    {
        bloomEffect.dispose();
        fxaaEffect.dispose();
        motionBlurEffect.dispose();
        crtEffect.dispose();
        oldTvEffect.dispose();
        bg.dispose();
        debugRenderer.dispose();
    }
}
