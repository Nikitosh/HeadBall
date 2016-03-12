package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.HeadballGame;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TournamentReader;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.tournaments.TournamentDeserializer;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Pair;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.TournamentChoosingTable;

import java.util.NoSuchElementException;

public class TournamentChoosingScreen extends BackgroundStageAbstractScreen {

    private static final String LOG_TAG = "TournamentChoosingScreen";
    private static final String GET_TOURNAMENT_BY_INDEX_ERROR_MESSAGE = "Can't get tournament with index: ";

    public TournamentChoosingScreen() {
        TournamentReader reader = TournamentReader.getTournamentReader();
        Array<Tournament> tournaments = new Array<>();
        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            try {
                tournaments.add(reader.getTournament(i, true));
            } catch (NoSuchElementException e) {
                Gdx.app.error(LOG_TAG, GET_TOURNAMENT_BY_INDEX_ERROR_MESSAGE + i, e);
                HeadballGame.getActionResolver().showToast(GET_TOURNAMENT_BY_INDEX_ERROR_MESSAGE + i);
            }
        }

        final TournamentChoosingTable choosingTable = new TournamentChoosingTable(tournaments);
        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                if (!Gdx.files.local(Constants.TOURNAMENTS_SAVES_PATH +
                        choosingTable.getSelectedTournament().getName() + Constants.JSON).exists()) {
                    ScreenManager.getInstance().setScreen(
                            new TournamentTeamChoosingScreen(choosingTable.getSelectedTournament()));
                    return;
                }

                final Table dialogTable = new Table();

                TextButton continueButton = new TextButton("Continue", AssetLoader.getGameSkin());
                continueButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dialogTable.remove();
                        AssetLoader.getDarkBackgroundImage().remove();
                        Pair<Tournament, Team> tournamentInfo =
                                TournamentDeserializer.deserialize(choosingTable.getSelectedTournament().getClass(),
                                        choosingTable.getSelectedTournament().getName());
                        ScreenManager.getInstance().setScreen(
                                new TournamentScreen(tournamentInfo.getFirst(), tournamentInfo.getSecond()));
                    }
                });

                TextButton startButton = new TextButton("Start new", AssetLoader.getGameSkin());
                startButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        dialogTable.remove();
                        AssetLoader.getDarkBackgroundImage().remove();
                        ScreenManager.getInstance().setScreen(
                                new TournamentTeamChoosingScreen(choosingTable.getSelectedTournament()));
                    }
                });

                Dialog tournamentDialog = new Dialog("", AssetLoader.getDefaultSkin());
                tournamentDialog.setMovable(false);
                tournamentDialog.text("Do you want to continue saved or start new tournament?");
                tournamentDialog.button(continueButton);
                tournamentDialog.button(startButton);
                dialogTable.setFillParent(true);
                dialogTable.add(tournamentDialog);
                stack.addActor(AssetLoader.getDarkBackgroundImage());
                stack.addActor(dialogTable);
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable());
    }
}
