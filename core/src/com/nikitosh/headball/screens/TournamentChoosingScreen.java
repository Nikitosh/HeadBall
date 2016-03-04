package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.tournaments.TournamentDeserializer;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Pair;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.jsonReaders.TournamentReader;
import com.nikitosh.headball.widgets.TournamentChoosingTable;

import java.util.NoSuchElementException;

public class TournamentChoosingScreen extends BackgroundStageAbstractScreen {

    private static final String LOG_TAG = "TournamentChoosingScreen";
    private static final String GET_TOURNAMENT_BY_INDEX_ERROR_MESSAGE = "Can't get tournament with index:";

    public TournamentChoosingScreen() {
        TournamentReader reader = TournamentReader.getTournamentReader();
        Array<Tournament> tournaments = new Array<>();
        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            try {
                tournaments.add(reader.getTournament(i));
            } catch (NoSuchElementException e) {
                Gdx.app.error(LOG_TAG, GET_TOURNAMENT_BY_INDEX_ERROR_MESSAGE + i, e);
            }
        }

        final TournamentChoosingTable choosingTable = new TournamentChoosingTable(tournaments);
        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                if (Gdx.files.local(
                        Constants.TOURNAMENTS_SAVES_PATH + choosingTable.getSelectedTournament().getName()
                                + Constants.JSON).exists()) {
                    Pair<Tournament, Team> tournamentInfo =
                            TournamentDeserializer.deserialize(choosingTable.getSelectedTournament().getClass(),
                            choosingTable.getSelectedTournament().getName());
                    ScreenManager.getInstance().setScreen(
                            new TournamentScreen(tournamentInfo.getFirst(), tournamentInfo.getSecond()));
                } else {
                    ScreenManager.getInstance().setScreen(
                        new TournamentTeamChoosingScreen(choosingTable.getSelectedTournament()));
                }
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable());
    }
}
