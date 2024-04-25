package com.libgdx.example.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.example.Player;
import com.libgdx.example.Views.GameView;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private GameView gameView;

    SpriteBatch batch;
    Texture img;
    Player player1;

    public GameScreen()
    {
        String[] player1Controls = {"W","A","S","D"};

        img = new Texture("player.png");
        player1 = new Player(img, player1Controls);
        player1.setPosition(250,100);
    }
    @Override
    public void show() {
        gameView = new GameView(player1);
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