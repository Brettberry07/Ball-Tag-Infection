package com.Infection.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.FisheyeEffect;
import com.Infection.main.screens.GameScreen;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
