package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class SplashScreen implements Screen {
    private Game game;
    private Sprite sprite;
    private SpriteBatch batch;
    private long startTime;
    private OrthographicCamera camera;

    private float width;
    private float height;

    public SplashScreen(Game game) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        this.game = game;

        batch = new SpriteBatch();
        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false, width, height);

        Texture splashTexture = new Texture(Gdx.files.internal("images/splashScreen.jpg"));
        sprite = new Sprite(splashTexture);
        sprite.setSize(width, height);

        AssetLoader.loadFont();
    }

    @Override
    public void show() {
        AssetLoader.load();
        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);

        String waitText = "Loading! Wait for a while!";

        GlyphLayout layout = new GlyphLayout(AssetLoader.font, waitText);

        AssetLoader.font.draw(batch, layout, width / 2 - layout.width / 2, 6 * height / 7 - layout.height / 2);
        batch.end();

        if (TimeUtils.millis() > startTime + Constants.SPLASH_DURATION) {
            dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
