package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class TournamentScreen extends BackgroundStageAbstractScreen {
    private static final String[] TOURNAMENT_ENDED_TITLES = {
            "You lose :(",
            "You win! Congratulations!"
    };
    private static final String EXIT = "Exit";
    private static final boolean IS_PRACTICE = false;

    private Tournament tournament;

    public TournamentScreen(final Tournament tournament, final Team playerTeam) {
        this.tournament = tournament;

        TextButton playButton = new TextButton("Play next match", AssetLoader.gameSkin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (tournament.isEnded(playerTeam)) {
                    return;
                }
                Team opponentTeam = tournament.getNextOpponent(playerTeam);
                final GameScreen gameScreen = new SinglePlayerScreen(
                        new MatchInfo(playerTeam, opponentTeam, tournament.isDrawResultPossible(), IS_PRACTICE));
                ScreenManager.getInstance().setScreen(gameScreen);

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

        stack.addActor(table);
    }

    private void handleMatchEnd(Team playerTeam, int[] score) {
        tournament.startNewRound();
        tournament.handlePlayerMatch(playerTeam, score[0], score[1]);
        tournament.simulateNextRound();
        tournament.endCurrentRound();
        tournament.getStatisticsTable().highlightTeam(playerTeam);
        tournament.getResultTable().highlightTeam(playerTeam);
        if (tournament.isEnded(playerTeam)) {
            Button exitButton = new TextButton(EXIT, AssetLoader.gameSkin);
            exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ScreenManager.getInstance().disposeCurrentScreen();
                }
            });

            Dialog exitDialog = new Dialog("", AssetLoader.defaultSkin);
            exitDialog.text(TOURNAMENT_ENDED_TITLES[tournament.isWinner(playerTeam) ? 1 : 0],
                    AssetLoader.defaultSkin.get(Label.LabelStyle.class));
            exitDialog.button(exitButton);

            Table exitTable = new Table();
            exitTable.setFillParent(true);
            exitTable.add(exitDialog).expand();

            stack.addActor(new Image(AssetLoader.darkBackgroundTexture));
            stack.addActor(exitTable);
        }
    }
}
