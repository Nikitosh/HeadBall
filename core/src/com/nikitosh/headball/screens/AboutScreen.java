package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.widgets.BackButtonTable;

public class AboutScreen implements Screen {
    private static final String ABOUT_TEXT = "This game was developed by Nikitosh & Wowember";

    private Stage stage;
    private Table aboutTable;

    public AboutScreen(final Game game, final Screen previousScreen) {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Label aboutLabel = new Label(ABOUT_TEXT, AssetLoader.defaultSkin);

        aboutTable = new Table();
        aboutTable.setFillParent(true);
        aboutTable.add(aboutLabel);

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.addActor(background);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
        stack.addActor(aboutTable);

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(stack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
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
