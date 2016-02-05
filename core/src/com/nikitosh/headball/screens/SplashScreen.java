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

public class SplashScreen extends StageAbstractScreen {
    private static final String SPLASH_TEXTURE_PATH = "images/splashScreen.jpg";

    private Game game;
    private float splashDuration;

    public SplashScreen(final Game game) {
        this.game = game;

        AssetLoader.loadFont();

        Texture splashTexture = new Texture(Gdx.files.internal(SPLASH_TEXTURE_PATH));
        Image background = new Image(splashTexture);
        background.setFillParent(true);

        stack.addActor(background);
    }

    @Override
    public void show() {
        AssetLoader.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        splashDuration += delta;
        if (splashDuration >= Constants.SPLASH_DURATION) {
            dispose();
            game.setScreen(new MainMenuScreen(game));
        }
    }
}
