package com.nikitosh.headball.jsonReaders;

import com.nikitosh.headball.Team;
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
        assert(false);
        return null;
    }

    public JSONObject getJSONTeam(int index) {
        if (index < 0 || index >= teams.size())
            throw new IndexOutOfBoundsException();
        return (JSONObject) teams.get(index);
    }

    public String getTeamName(int index) {
        return (String) (getJSONTeam(index)).get(JSON_NAME_KEY);
    }

    public String getTeamIconName(int index) {
        return (String) (getJSONTeam(index)).get(JSON_ICON_KEY);
    }

    public int getTeamsNumber() {
        return teams.size();
    }
}
