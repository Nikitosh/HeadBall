package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.tournaments.LeagueTournament;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.ui.GameTextButton;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

import java.awt.*;

public class TournamentScreen implements Screen {
    private static final String[] TOURNAMENT_ENDED_TITLES = {
            "You lose :(",
            "You win! Congratulations!"
    };
    private static final String EXIT = "Exit";
    private static final boolean IS_PRACTICE = false;

    private Stage stage;
    private final Game game;
    private Tournament tournament;

    public TournamentScreen(final Game game, final Tournament tournament, final Team playerTeam) {
        this.game = game;
        this.tournament = tournament;

        stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        GameTextButtonTouchable playButton = new GameTextButtonTouchable("Play next match");
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tournament.isEnded(playerTeam)) {
                    return;
                }
                Team opponentTeam = tournament.getNextOpponent(playerTeam);
                final GameScreen gameScreen = new SinglePlayerScreen(game, TournamentScreen.this,
                        new MatchInfo(playerTeam, opponentTeam, tournament.isDrawResultPossible(), IS_PRACTICE));
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
                            handleMatchEnd(playerTeam, gameScreen.getScore());
                        }
                    }
                }).start();
            }
        });

        tournament.getStatisticsTable().highlightTeam(playerTeam);
        tournament.getResultTable().highlightTeam(playerTeam);

        Table upTable = new Table();
        upTable.defaults().padRight(5 * Constants.UI_ELEMENTS_INDENT);
        upTable.add(tournament.getResultTable().getTable());
        upTable.add(tournament.getStatisticsTable().getTable()).row();

        Table table = new Table();
        table.setFillParent(true);
        table.add(upTable).row();
        table.add(playButton).pad(Constants.UI_ELEMENTS_INDENT);

        stage.addActor(table);
    }

    private void handleMatchEnd(Team playerTeam, int[] score) {
        tournament.startNewRound();
        tournament.handlePlayerMatch(playerTeam, score[0], score[1]);
        tournament.simulateNextRound();
        tournament.endCurrentRound();
        tournament.getStatisticsTable().highlightTeam(playerTeam);
        tournament.getResultTable().highlightTeam(playerTeam);
        if (tournament.isEnded(playerTeam)) {
            Button exitButton = new GameTextButtonTouchable(EXIT);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
            });

            Dialog exitDialog = new Dialog("", AssetLoader.defaultSkin);
            exitDialog.text(TOURNAMENT_ENDED_TITLES[tournament.isWinner(playerTeam) ? 1 : 0],
                    AssetLoader.defaultSkin.get(Label.LabelStyle.class));
            exitDialog.button(exitButton);

            Table exitTable = new Table();
            exitTable.setFillParent(true);
            exitTable.add(exitDialog).expand();

            stage.addActor(new Image(AssetLoader.darkBackgroundTexture));
            stage.addActor(exitTable);
        }
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
