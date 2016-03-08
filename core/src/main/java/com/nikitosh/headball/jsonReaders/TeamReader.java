package com.nikitosh.headball.jsonReaders;

import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.NoSuchElementException;

public final class TeamReader {
    private static final String TEAMS_PATH = "info/teams.json";

    private static final String JSON_TEAMS_KEY = "teams";
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_ICON_KEY = "icon";

    private static TeamReader teamReader;
    private final JSONArray teams;

    private TeamReader() {
        teams = (JSONArray) Utilities.parseJSONFile(TEAMS_PATH).get(JSON_TEAMS_KEY);
    }

    public static TeamReader getTeamReader() {
        if (teamReader == null) {
            teamReader = new TeamReader();
        }
        return teamReader;
    }

    public Team getTeam(int index) {
        JSONObject team = getJSONTeam(index);
        return new Team((String) team.get(JSON_NAME_KEY), (String) team.get(JSON_ICON_KEY));
    }

    public Team getTeam(String name) {
        for (int i = 0; i < teams.size(); i++) {
            if (name.equals(getTeamName(i))) {
                return getTeam(i);
            }
        }
        throw new NoSuchElementException();
    }

    private JSONObject getJSONTeam(int index) {
        if (index < 0 || index >= teams.size()) {
            throw new NoSuchElementException();
        }
        return (JSONObject) teams.get(index);
    }

    private String getTeamName(int index) {
        return (String) (getJSONTeam(index)).get(JSON_NAME_KEY);
    }

    public int getTeamsNumber() {
        return teams.size();
    }
}
