package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.*;
import com.nikitosh.headball.gamecontrollers.GameController;
import com.nikitosh.headball.gamecontrollers.SinglePlayerGameController;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.tournaments.TournamentSerializer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.widgets.BackButtonTable;

import java.util.NoSuchElementException;

public class TournamentScreen extends BackgroundStageAbstractScreen {

    private static final String LOG_TAG = "TournamentScreen";
    private static final String GET_NEXT_OPPONENT_ERROR_MESSAGE = "No next opponent for team: ";

    private static final String[] TOURNAMENT_ENDED_TITLES = {
            "You lose :(",
            "You win! Congratulations!"
    };
    private static final String EXIT = "Exit";
    private static final boolean IS_PRACTICE = false;

    private Tournament tournament;

    public TournamentScreen(final Tournament tournament, final Team playerTeam) {
        this.tournament = tournament;

        TextButton playButton = new TextButton("Play next match", AssetLoader.getGameSkin());
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tournament.isEnded(playerTeam)) {
                    return;
                }
                Team opponentTeam;
                try {
                    opponentTeam = tournament.getNextOpponent(playerTeam);
                } catch (NoSuchElementException e) {
                    Gdx.app.error(LOG_TAG, GET_NEXT_OPPONENT_ERROR_MESSAGE + playerTeam.getName(), e);
                    HeadballGame.getActionResolver().showToast(GET_NEXT_OPPONENT_ERROR_MESSAGE
                            + playerTeam.getName());
                    return;
                }
                final GameScreen gameScreen = new GameScreen();
                final GameController gameController = new SinglePlayerGameController(gameScreen,
                        new MatchInfo(playerTeam, opponentTeam, tournament.isDrawResultPossible(), IS_PRACTICE));
                ScreenManager.getInstance().setScreen(gameScreen);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (gameController) {
                            while (gameController.isGameNotFinished()) {
                                try {
                                    gameController.wait();
                                } catch (Exception e) {
                                    Gdx.app.error(LOG_TAG, "", e);
                                }

                            }
                            handleMatchEnd(playerTeam, gameController.getScore());
                        }
                    }
                }).start();
            }
        });

        tournament.getStatisticsTable().highlightTeam(playerTeam);
        tournament.getResultTable().highlightTeam(playerTeam);

        Table upTable = new Table();
        upTable.defaults().padRight(Constants.UI_ELEMENTS_INDENT);
        upTable.add(tournament.getResultTable().getTable());
        upTable.add(tournament.getStatisticsTable().getTable()).row();

        Table table = new Table();
        table.setFillParent(true);
        table.add(upTable).row();
        table.add(playButton).pad(Constants.UI_ELEMENTS_INDENT);

        stack.addActor(table);
        stack.addActor(new BackButtonTable(new Runnable() {
            @Override
            public void run() {
                TournamentSerializer.serialize(tournament, playerTeam);
            }
        }));
    }

    private void handleMatchEnd(Team playerTeam, int[] score) {
        tournament.startNewRound();
        tournament.handlePlayerMatch(playerTeam, score[0], score[1]);
        tournament.simulateNextRound();
        tournament.endCurrentRound();
        tournament.getStatisticsTable().highlightTeam(playerTeam);
        tournament.getResultTable().highlightTeam(playerTeam);
        if (tournament.isEnded(playerTeam)) {
            Button exitButton = new TextButton(EXIT, AssetLoader.getGameSkin());
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.files.local(Constants.TOURNAMENTS_SAVES_PATH + tournament.getName()
                            + Constants.JSON).delete();
                    ScreenManager.getInstance().disposeCurrentScreen();
                }
            });

            Dialog exitDialog = new Dialog("", AssetLoader.getDefaultSkin());
            exitDialog.text(TOURNAMENT_ENDED_TITLES[tournament.isWinner(playerTeam) ? 1 : 0],
                    AssetLoader.getDefaultSkin().get(Label.LabelStyle.class));
            exitDialog.button(exitButton);

            Table exitTable = new Table();
            exitTable.setFillParent(true);
            exitTable.add(exitDialog).expand();

            stack.addActor(new Image(AssetLoader.getDarkBackgroundTexture()));
            stack.addActor(exitTable);
        }
    }
}
