package com.nikitosh.headball.screens;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamReader;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.TeamChoosingTable;

public class TournamentTeamChoosingScreen extends BackgroundStageAbstractScreen {

    public TournamentTeamChoosingScreen(final Tournament tournament) {
        Array<Team> teams = new Array<>();
        TeamReader reader = TeamReader.getTeamReader();
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
                ScreenManager.getInstance().disposeCurrentScreen();
                ScreenManager.getInstance().setScreen(
                        new TournamentScreen(tournament, choosingTable.getSelectedTeam()));
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable());
    }
}
