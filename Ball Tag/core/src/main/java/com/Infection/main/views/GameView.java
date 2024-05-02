package com.Infection.main.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.*;
import com.crashinvaders.vfx.effects.util.MixEffect;
import com.Infection.main.Player;

public class GameView implements Disposable {
    SpriteBatch batch;
    Texture img;
    Player player1;
    Player player2;

    private final VfxManager vfxManager;

    private final BloomEffect bloomEffect;
    private final FxaaEffect fxaaEffect;
    private final MotionBlurEffect motionBlurEffect;

    /*
        We will need to make a GameObjects array
        that will be iterated and drawn soon, one
        player may be okay but once we add two or
        more it'll be quite hectic to manage this codebase
     */

    public GameView(Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;

        batch = new SpriteBatch();

        OrthographicCamera cam = new OrthographicCamera();

        vfxManager = new VfxManager(Pixmap.Format.RGB888);

        bloomEffect = new BloomEffect();
        fxaaEffect = new FxaaEffect();
        motionBlurEffect = new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MAX, 0.25f);

        vfxManager.addEffect(bloomEffect);
        vfxManager.addEffect(fxaaEffect);
        vfxManager.addEffect(motionBlurEffect);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        vfxManager.cleanUpBuffers();

        updateEffects(delta);

        vfxManager.beginInputCapture();
        batch.begin();
        player1.draw(batch);
        player1.update(delta);
        player2.draw(batch);
        player2.update(delta);
        batch.end();
        vfxManager.endInputCapture();

        vfxManager.applyEffects();
        vfxManager.renderToScreen();
    }

    private void updateEffects(float delta)
    {
        bloomEffect.update(delta);
        fxaaEffect.update(delta);
        motionBlurEffect.update(delta);
    }

    public void resize(int width, int height) {
        // resize viewports and vfxManager here using parameters above
    }

    public void dispose()
    {
        img.dispose();
        batch.dispose();
    }
}