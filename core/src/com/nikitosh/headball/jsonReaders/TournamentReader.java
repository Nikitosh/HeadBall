package com.nikitosh.headball.jsonReaders;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.tournaments.LeagueTournament;
import com.nikitosh.headball.tournaments.PlayOffTournament;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TournamentReader {
    private final static String TOURNAMENTS_PATH = "info/tournaments.json";

    private final static String LOG_TAG = "TournamentReader";
    private final static String LOADING_EXCEPTION = "Unable to load tournaments list";
    private final static String JSON_EXCEPTION = "Wrong JSON file format";

    private final static String JSON_TOURNAMENTS_KEY = "tournaments";
    private final static String JSON_TYPE_KEY = "type";
    private final static String JSON_NAME_KEY = "name";
    private final static String JSON_ICON_KEY = "icon";
    private final static String JSON_LAP_NUMBER_KEY = "lapNumber";
    private final static String JSON_PARTICIPANTS_KEY = "participants";
    private final static String JSON_LEAGUE_KEY = "league";
    private final static String JSON_PLAYOFF_KEY = "playoff";

    private static TournamentReader tournamentReader;
    private JSONArray tournaments;

    private TournamentReader() {
        tournaments = (JSONArray) Utilities.parseJSONFile(TOURNAMENTS_PATH).get(JSON_TOURNAMENTS_KEY);
    }

    public static TournamentReader getTournamentsReader() {
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
        TeamReader teamsReader = TeamReader.getTeamsReader();
        for (Object teamName : participants) {
            teams.add(teamsReader.getTeam((String) teamName));
        }

        if (tournament.get(JSON_TYPE_KEY).equals(JSON_LEAGUE_KEY)) {
            return new LeagueTournament(name, iconName, teams, lapNumber);
        }
        if (tournament.get(JSON_TYPE_KEY).equals(JSON_PLAYOFF_KEY)) {
            return new PlayOffTournament(name, iconName, teams, lapNumber);
        }
        assert(false);
        return null;
    }

    public int getTournamentsNumber() {
        return tournaments.size();
    }

    private JSONObject getJSONTournament(int index) {
        if (index < 0 || index >= tournaments.size())
            throw new IndexOutOfBoundsException();
        return (JSONObject) tournaments.get(index);
    }
}
