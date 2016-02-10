package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.jsonReaders.TournamentsReader;
import com.nikitosh.headball.widgets.TournamentChoosingTable;

public class TournamentChoosingScreen extends StageAbstractScreen {
    public TournamentChoosingScreen(final Game game, final Screen previousScreen) {
        TournamentsReader reader = new TournamentsReader();
        Array<Tournament> tournaments = new Array<>();
        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            tournaments.add(reader.getTournament(i));
        }

        final TournamentChoosingTable choosingTable = new TournamentChoosingTable(tournaments);
        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new TournamentTeamChoosingScreen(game, TournamentChoosingScreen.this,
                        choosingTable.getSelectedTournament()));
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
    }
}
