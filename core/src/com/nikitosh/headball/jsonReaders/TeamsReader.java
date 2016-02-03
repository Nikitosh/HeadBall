package com.nikitosh.headball.jsonReaders;

import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TeamsReader {
    private final static String TEAMS_PATH = "info/teams.json";

    private final static String JSON_TEAMS_KEY = "teams";
    private final static String JSON_NAME_KEY = "name";
    private final static String JSON_ICON_KEY = "icon";

    private JSONArray teams;

    public TeamsReader() {
        teams = (JSONArray) Utilities.parseJSONFile(TEAMS_PATH).get(JSON_TEAMS_KEY);
    }

    public JSONObject getJSONTeam(int index) {
        if (index < 0 || index >= teams.size())
            throw new IndexOutOfBoundsException();
        return (JSONObject) teams.get(index);
    }

    public String getTeamName(int index) {
        return (String) ((JSONObject) getJSONTeam(index)).get(JSON_NAME_KEY);
    }

    public String getTeamIconName(int index) {
        return (String) ((JSONObject) getJSONTeam(index)).get(JSON_ICON_KEY);
    }

    public int getTeamsNumber() {
        return teams.size();
    }
}
