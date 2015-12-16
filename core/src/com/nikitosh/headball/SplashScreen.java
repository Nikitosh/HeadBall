package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

public class SplashScreen implements Screen {
    private Game game;
    private Texture splashBallTexture;
    private Sprite sprite;
    private SpriteBatch batch;
    private BitmapFont font;
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
        splashBallTexture = new Texture(Gdx.files.internal("images/splashBall.jpg"));
        TextureRegion region = new TextureRegion(splashBallTexture);
        //region.flip(false, true);
        sprite = new Sprite(region);
        sprite.setSize(width, height);

        font = new BitmapFont();
    }

    @Override
    public void show() {
        AssetLoader.load();
        Gdx.app.log("SplashScreen", "show splashScreen");
        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        font.draw(batch, "Loading! Wait for a while!", width / 2, height / 2);
        batch.end();

        if (TimeUtils.millis() > startTime + 500) {
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
