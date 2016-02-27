package com.nikitosh.headball.jsonReaders;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.tournaments.LeagueTournament;
import com.nikitosh.headball.tournaments.PlayOffTournament;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class TournamentReader {
    private static final String TOURNAMENTS_PATH = "info/tournaments.json";

    private static final String JSON_TOURNAMENTS_KEY = "tournaments";
    private static final String JSON_TYPE_KEY = "type";
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_ICON_KEY = "icon";
    private static final String JSON_LAP_NUMBER_KEY = "lapNumber";
    private static final String JSON_PARTICIPANTS_KEY = "participants";
    private static final String JSON_LEAGUE_KEY = "league";
    private static final String JSON_PLAYOFF_KEY = "playoff";

    private static TournamentReader tournamentReader;
    private JSONArray tournaments;

    private TournamentReader() {
        tournaments = (JSONArray) Utilities.parseJSONFile(TOURNAMENTS_PATH).get(JSON_TOURNAMENTS_KEY);
    }

    public static TournamentReader getTournamentReader() {
        if (tournamentReader == null) {
            tournamentReader = new TournamentReader();
        }
        return tournamentReader;
    }

    public Tournament getTournament(int index) {
        JSONObject tournament = getJSONTournament(index);
        String name = (String) tournament.get(JSON_NAME_KEY);
        String iconName = (String) tournament.get(JSON_ICON_KEY);
        int lapNumber = ((Long) tournament.get(JSON_LAP_NUMBER_KEY)).intValue();
        JSONArray participants = (JSONArray) tournament.get(JSON_PARTICIPANTS_KEY);
        Array<Team> teams = new Array<>();
        TeamReader teamsReader = TeamReader.getTeamReader();
        for (Object teamName : participants) {
            teams.add(teamsReader.getTeam((String) teamName));
        }

        if (tournament.get(JSON_TYPE_KEY).equals(JSON_LEAGUE_KEY)) {
            return new LeagueTournament(name, iconName, teams, lapNumber);
        }
        if (tournament.get(JSON_TYPE_KEY).equals(JSON_PLAYOFF_KEY)) {
            return new PlayOffTournament(name, iconName, teams, lapNumber);
        }
        assert (false);
        return null;
    }

    public Tournament getTournament(String name) {
        for (int i = 0; i < getTournamentsNumber(); i++) {
            if (name.equals(getTournamentName(i))) {
                return getTournament(i);
            }
        }
        assert (false);
        return null;
    }

    public String getTournamentName(int index) {
        return (String) (getJSONTournament(index)).get(JSON_NAME_KEY);
    }

    public int getTournamentsNumber() {
        return tournaments.size();
    }

    private JSONObject getJSONTournament(int index) {
        assert (index >= 0 && index < tournaments.size());
        return (JSONObject) tournaments.get(index);
    }
}
