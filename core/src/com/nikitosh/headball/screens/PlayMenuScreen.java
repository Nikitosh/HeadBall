package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.widgets.BackButtonTable;

public class PlayMenuScreen implements Screen {
    private static final String PRACTICE = "Practice";
    private static final String TOURNAMENT = "Tournament";
    private static final String MULTIPLAYER = "Multiplayer";

    private Stage stage;

    public PlayMenuScreen(final Game game, final Screen previousScreen) {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Button practiceTextButton = new GameTextButtonTouchable(PRACTICE);
        practiceTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SinglePlayerScreen(game, new Team("Player"), new Team("Bot"), PlayMenuScreen.this, true));
            }
        });

        Button tournamentTextButton = new GameTextButtonTouchable(TOURNAMENT);
        tournamentTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TournamentChoosingScreen(game, PlayMenuScreen.this));
            }
        });

        Button multiPlayerTextButton = new GameTextButtonTouchable(MULTIPLAYER);
        multiPlayerTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MultiPlayerScreen(game, new Team(""), new Team(""), PlayMenuScreen.this, true));
            }
        });

        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.add(practiceTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        menuTable.add(tournamentTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        menuTable.add(multiPlayerTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.addActor(background);
        stack.addActor(menuTable);
        stack.addActor(new BackButtonTable(game, this, previousScreen));

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(stack);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(delta);
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
