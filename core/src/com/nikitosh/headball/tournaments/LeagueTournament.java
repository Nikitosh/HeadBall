package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.widgets.ResultTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LeagueTournament implements Tournament {
    private int lapNumber;
    private int selectedTeamIndex = 0;
    private Array<Team> teams = new Array<Team>();

    public LeagueTournament(JSONObject tournament) {
        lapNumber = ((Long) tournament.get("lapNumber")).intValue();
        JSONArray participantsNames = (JSONArray) tournament.get("participants");
        for (int i = 0; i < participantsNames.size(); i++) {
            String teamName = (String) participantsNames.get(i);
            teams.add(new Team(teamName));
        }
        generateTimetable();
    }

    private void generateTimetable() {

    }

    @Override
    public void setSelectedTeam(Team teamName) {
        selectedTeamIndex = teams.indexOf(teamName, false);
        assert(selectedTeamIndex != -1);
    }

    @Override
    public void simulateNextRound() {

    }

    @Override
    public void handlePlayerMatch(int playerScore, int opponentScore) {

    }

    @Override
    public Team getNextOpponent() {
        return null;
    }

    @Override
    public Array<Team> getParticipants() {
        return null;
    }

    @Override
    public ResultTable getResultTable() {
        return null;
    }

    @Override
    public ResultTable getStatisticsTable() {
        return null;
    }

    @Override
    public boolean isEnded() {
        return false;
    }
}
