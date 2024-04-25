package com.libgdx.example.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.libgdx.example.Player;

public class GameView implements Disposable {
    SpriteBatch batch;
    Texture img;
    Player player1;

    private VfxManager vfxManager;

    private BloomEffect bloomEffect;

    public GameView()
    {
        String[] player1Controls = {"W","A","S","D"};

        batch = new SpriteBatch();
        img = new Texture("player.png");
        player1 = new Player(img, player1Controls);
        player1.setPosition(250,100);

        OrthographicCamera cam = new OrthographicCamera();

        Texture player_img = new Texture("Player.png");
        Player player = new Player(player_img, player1Controls);

        vfxManager = new VfxManager(Pixmap.Format.RGB888);

        bloomEffect = new BloomEffect();
        vfxManager.addEffect(bloomEffect);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        player1.draw(batch);
        player1.update();
        batch.end();
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
