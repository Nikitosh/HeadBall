package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class SplashScreen implements Screen {
    private static final String SPLASH_TEXTURE_PATH = "images/splashScreen.jpg";

    private Game game;
    private float splashDuration;

    private Stage stage;

    public SplashScreen(final Game game) {
        this.game = game;

        AssetLoader.loadFont();

        Texture splashTexture = new Texture(Gdx.files.internal(SPLASH_TEXTURE_PATH));
        Image background = new Image(splashTexture);
        background.setFillParent(true);

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.addActor(background);

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(stack);
    }

    @Override
    public void show() {
        AssetLoader.load();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        splashDuration += delta;
        if (splashDuration >= Constants.SPLASH_DURATION) {
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
