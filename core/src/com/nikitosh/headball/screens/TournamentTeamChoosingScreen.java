package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamReader;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.TeamChoosingTable;

public class TournamentTeamChoosingScreen extends StageAbstractScreen {

    public TournamentTeamChoosingScreen(final Game game, Screen previousScreen, final Tournament tournament) {
        Array<Team> teams = new Array<>();
        TeamReader reader = TeamReader.getTeamsReader();
        for (Team team : tournament.getParticipants()) {
            for (int i = 0; i < reader.getTeamsNumber(); i++) {
                if (team.equals(reader.getTeam(i))) {
                    teams.add(reader.getTeam(i));
                }
            }
        }
        final TeamChoosingTable choosingTable = new TeamChoosingTable(teams);
        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                game.setScreen(new TournamentScreen(game, tournament, choosingTable.getSelectedTeam()));
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
    }
}
