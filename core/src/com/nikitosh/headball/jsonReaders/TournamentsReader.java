package com.nikitosh.headball.jsonReaders;

import com.badlogic.gdx.Gdx;
import com.nikitosh.headball.tournaments.LeagueTournament;
import com.nikitosh.headball.tournaments.PlayOffTournament;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.nio.charset.Charset;

public class TournamentsReader {
    private final static String TOURNAMENTS_PATH = "info/tournaments.json";

    private final static String LOG_TAG = "TournamentReader";
    private final static String LOADING_EXCEPTION = "Unable to load tournaments list";
    private final static String JSON_EXCEPTION = "Wrong JSON file format";

    private final static String JSON_TOURNAMENTS_KEY = "tournaments";
    private final static String JSON_TYPE_KEY = "type";
    private final static String JSON_INFO_KEY = "info";
    private final static String JSON_NAME_KEY = "name";
    private final static String JSON_ICON_KEY = "icon";
    private final static String JSON_LEAGUE_KEY = "league";
    private final static String JSON_PLAYOFF_KEY = "playoff";

    private JSONArray tournaments;

    public TournamentsReader() {
        String content = null;
        try {
            content = Utilities.readFile(TOURNAMENTS_PATH, Charset.defaultCharset());
        }
        catch (Exception e) {
            e.printStackTrace();
            Gdx.app.log(LOG_TAG, LOADING_EXCEPTION);
        }

        JSONParser parser = new JSONParser();
        try {
            tournaments = (JSONArray) ((JSONObject) parser.parse(content)).get(JSON_TOURNAMENTS_KEY);
        }
        catch (Exception e) {
            Gdx.app.log(LOG_TAG, JSON_EXCEPTION);
        }

    }

    public Tournament getTournament(int index) {
        JSONObject tournament = getJSONTournament(index);
        if (tournament.get(JSON_TYPE_KEY).equals(JSON_LEAGUE_KEY)) {
            return new LeagueTournament((JSONObject) tournament.get(JSON_INFO_KEY));
        }
        if (tournament.get(JSON_TYPE_KEY).equals(JSON_PLAYOFF_KEY)) {
            return new PlayOffTournament((JSONObject) tournament.get(JSON_INFO_KEY));
        }
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

    public String getTournamentName(int index) {
        return (String) ((JSONObject) getJSONTournament(index).get(JSON_INFO_KEY)).get(JSON_NAME_KEY);
    }

    public String getTournamentIconName(int index) {
        return (String) ((JSONObject) getJSONTournament(index).get(JSON_INFO_KEY)).get(JSON_ICON_KEY);
    }

    public String getTournamentType(int index) {
        return (String) getJSONTournament(index).get(JSON_TYPE_KEY);
    }
}
