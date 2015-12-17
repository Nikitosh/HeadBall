package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.dnd.DragGestureEvent;

public class SettingsScreen implements Screen {
    private Stage stage;
    private Table table;

    private final Game game;
    private Drawable[] drawables;
    private int soundState = GameSettings.getBoolean("sound") ? 0 : 1;
    private int musicState = GameSettings.getBoolean("music") ? 0 : 1;

    public SettingsScreen(final Game game) {
        this.game = game;
        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setBounds(0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);

        drawables = new Drawable[2];
        drawables[0] = AssetLoader.skin.getDrawable("blue_boxCheckmark");
        drawables[1] = AssetLoader.skin.getDrawable("blue_boxCross");

        Button backButton = new GameTextButton("Back");
        backButton.setBounds(Constants.BUTTON_INDENT, Constants.VIRTUAL_HEIGHT - Constants.BUTTON_INDENT - backButton.getHeight(), backButton.getWidth(), backButton.getHeight());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (soundState == 0) {
                    GameSettings.putBoolean("sound", true);
                } else {
                    GameSettings.putBoolean("sound", false);
                }
                if (musicState == 0) {
                    GameSettings.putBoolean("music", true);
                } else {
                    GameSettings.putBoolean("music", false);
                }
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });

        stage.addActor(backButton);

        Button soundTextButton = new GameTextButton("Sound");
        table.add(soundTextButton);

        final Button soundButton = new Button(new GameButtonStyle("blue_boxCheckmark"));
        soundButton.getStyle().up = drawables[soundState];
        table.add(soundButton).row();

        Button musicTextButton = new GameTextButton("Music");
        table.add(musicTextButton);

        final Button musicButton = new Button(new GameButtonStyle("blue_boxCheckmark"));
        musicButton.getStyle().up = drawables[musicState];
        table.add(musicButton).row();

        stage.addActor(table);

        soundButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundState = 1 - soundState;
                soundButton.getStyle().up = drawables[soundState];
            }
        });
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicState = 1 - musicState;
                musicButton.getStyle().up = drawables[musicState];
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        stage.dispose();
    }
}
