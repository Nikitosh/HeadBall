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

    private JSONArray tournaments;

    public TournamentsReader() {
        String content = null;
        try {
            content = Utilities.readFile("info/tournaments.json", Charset.defaultCharset());
        }
        catch (Exception e) {
            Gdx.app.log("TournamentsReader", "Unable to load tournaments list");
        }

        JSONParser parser = new JSONParser();
        try {
            tournaments = (JSONArray) ((JSONObject) parser.parse(content)).get("tournaments");
        }
        catch (Exception e) {
            Gdx.app.log("TournamentsReader", "Wrong JSON file format");
        }

    }

    public Tournament getTournament(int index) {
        JSONObject tournament = getJSONTournament(index);
        if (tournament.get("type").equals("league")) {
            return new LeagueTournament((JSONObject) tournament.get("info"));
        }
        if (tournament.get("type").equals("playoff")) {
            return new PlayOffTournament((JSONObject) tournament.get("info"));
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
        return (String) ((JSONObject) getJSONTournament(index).get("info")).get("name");
    }

    public String getTournamentIconName(int index) {
        return (String) ((JSONObject) getJSONTournament(index).get("info")).get("icon");
    }

    public String getTournamentType(int index) {
        return (String) getJSONTournament(index).get("type");
    }
}
