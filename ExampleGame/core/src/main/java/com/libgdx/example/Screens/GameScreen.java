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
    Player player2;

    public GameScreen()
    {
        String[] player1Controls = {"W","A","S","D"};
        String[] player2Controls = {"I","J","K","L"};

        img = new Texture("player.png");
        player1 = new Player(img, player1Controls);
        player1.setPosition(250,100);
        player2 = new Player(img, player2Controls);
        player2.setPosition(0,0);
    }
    @Override
    public void show() {
        gameView = new GameView(player1, player2);
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