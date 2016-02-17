package com.nikitosh.headball.screens;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.jsonReaders.TournamentReader;
import com.nikitosh.headball.widgets.TournamentChoosingTable;

public class TournamentChoosingScreen extends BackgroundStageAbstractScreen {
    public TournamentChoosingScreen() {
        TournamentReader reader = TournamentReader.getTournamentsReader();
        Array<Tournament> tournaments = new Array<>();
        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            tournaments.add(reader.getTournament(i));
        }

        final TournamentChoosingTable choosingTable = new TournamentChoosingTable(tournaments);
        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                ScreenManager.getInstance().setScreen(
                        new TournamentTeamChoosingScreen(choosingTable.getSelectedTournament()));
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable());
    }
}
