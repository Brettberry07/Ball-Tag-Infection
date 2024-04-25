package com.libgdx.example.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.libgdx.example.Views.GameView;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private GameView gameView;
    @Override
    public void show() {
        gameView = new GameView();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

        gameView.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameView.dispose();
    }
}