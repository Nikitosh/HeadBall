package com.nikitosh.headball.tournaments;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayOffTournament extends Tournament {
    private int lapNumber;
    private int selectedTeamIndex = 0;
    private ArrayList<String> teams = new ArrayList<String>();

    public PlayOffTournament(JSONObject tournament) {
        lapNumber = ((Long) tournament.get("lapNumber")).intValue();
        JSONArray participantsNames = (JSONArray) tournament.get("participants");
        for (int i = 0; i < participantsNames.size(); i++) {
            String teamName = (String) participantsNames.get(i);
            teams.add(teamName);
        }
        generateTimetable();
    }

    @Override
    protected void generateTimetable() {

    }

    @Override
    public void setSelectedTeam(String teamName) {
        selectedTeamIndex = teams.indexOf(teamName);
        assert(selectedTeamIndex != -1);
    }

    @Override
    public void playNextMatch() {

    }

    @Override
    public String getNextOpponent() {
        return null;
    }

}
