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

public class TournamentChoosingScreen extends BackgroundStageAbstractScreen {
    public TournamentChoosingScreen() {
        TournamentReader reader = TournamentReader.getTournamentReader();
        Array<Tournament> tournaments = new Array<>();
        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            tournaments.add(reader.getTournament(i));
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
