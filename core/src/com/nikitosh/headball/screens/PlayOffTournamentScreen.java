package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.Constants;

public class PlayOffTournamentScreen implements Screen {
    private static final String PLAY = "Play next match";

    private Stage stage;
    private Table table = new Table();

    public PlayOffTournamentScreen(final Game game, final Tournament tournament, final Team playerTeam) {
        tournament.setSelectedTeam(playerTeam);

        GameTextButtonTouchable playButton = new GameTextButtonTouchable(PLAY);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tournament.isEnded()) {
                    return;
                }
                final Team opponentTeam = tournament.getNextOpponent();
                final GameScreen gameScreen = new SinglePlayerScreen(game,
                        playerTeam, opponentTeam, PlayOffTournamentScreen.this, false);
                game.setScreen(gameScreen);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (gameScreen) {
                            while (!gameScreen.isGameFinished()) {
                                try {
                                    gameScreen.wait();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            int[] score = gameScreen.getScore();
                            tournament.simulateNextRound();
                            tournament.handlePlayerMatch(score[0], score[1]);
                        }
                    }
                }).start();
            }
        });

        Table upTable = new Table();
        upTable.defaults().padRight(5 * Constants.UI_ELEMENTS_INDENT);
        upTable.add(tournament.getResultTable());
        upTable.add(tournament.getStatisticsTable()).row();

        table.setFillParent(true);
        table.add(upTable).row();
        table.add(playButton).pad(Constants.UI_ELEMENTS_INDENT);

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        stage.addActor(table);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    }
}
