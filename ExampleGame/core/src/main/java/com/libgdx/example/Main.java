package com.libgdx.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.FisheyeEffect;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.crashinvaders.vfx.effects.VfxEffect;

import static com.badlogic.gdx.graphics.TextureData.TextureDataType.Pixmap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    SpriteBatch batch;
    Texture img;
    Player player1;
    Player player2;

    VfxManager vfxManager;
    FisheyeEffect effectOne;

    @Override
    public void create() {
        String[] player1Controls = {"W","A","S","D"};
        String[] player2Controls = {"I","J","K","L"};

        batch = new SpriteBatch();
        img = new Texture("player.png");
        player1 = new Player(img, player1Controls);
        player1.setPosition(0,0);
        player2 = new Player(img, player2Controls);
        player2.setPosition(500,500);

        //Vfx manager
        vfxManager = new VfxManager(com.badlogic.gdx.graphics.Pixmap.Format.RGB888);
        effectOne = new FisheyeEffect();
//        vfxManager.addEffect(effectOne);

        setScreen(new FirstScreen());
    }

    @Override
    public void render(){
        ScreenUtils.clear(1f,1f,1f,1f);
//        vfxManager.cleanUpBuffers();
//        vfxManager.beginInputCapture();


        batch.begin();

        player1.Draw(batch);
        player1.movement();
        player2.Draw(batch);
        player2.movement();
        batch.end();

//        vfxManager.endInputCapture();
//        vfxManager.applyEffects();
//        vfxManager.renderToScreen();
    }

    @Override
    public void dispose(){
        batch.dispose();
        img.dispose();
        vfxManager.dispose();
        effectOne.dispose();
    }
}
