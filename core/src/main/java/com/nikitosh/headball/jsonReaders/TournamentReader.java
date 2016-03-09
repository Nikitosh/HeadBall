package com.nikitosh.headball.jsonReaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.HeadballGame;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.tournaments.LeagueTournament;
import com.nikitosh.headball.tournaments.PlayOffTournament;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.NoSuchElementException;

public final class TournamentReader {
    private static final String JSON_TOURNAMENTS_KEY = "tournaments";
    private static final String JSON_TYPE_KEY = "type";
    private static final String JSON_NAME_KEY = "name";
    private static final String JSON_ICON_KEY = "icon";
    private static final String JSON_LAP_NUMBER_KEY = "lapNumber";
    private static final String JSON_PARTICIPANTS_KEY = "participants";
    private static final String JSON_LEAGUE_KEY = "league";
    private static final String JSON_PLAYOFF_KEY = "playoff";

    private static final String LOG_TAG = "TournamentReader";
    private static final String GET_TEAM_BY_NAME_ERROR_MESSAGE = "Can't get team with name: ";

    private static String tournamentsPath = "info/tournaments.json";

    private static TournamentReader tournamentReader;
    private final JSONArray tournaments;

    private TournamentReader() {
        tournaments = (JSONArray) Utilities.parseJSONFile(tournamentsPath).get(JSON_TOURNAMENTS_KEY);
    }

    public static TournamentReader getTournamentReader() {
        if (tournamentReader == null) {
            tournamentReader = new TournamentReader();
        }
        return tournamentReader;
    }

    public Tournament getTournament(int index, boolean toCreate) {
        JSONObject tournament = getJSONTournament(index);
        String name = (String) tournament.get(JSON_NAME_KEY);
        String iconName = (String) tournament.get(JSON_ICON_KEY);
        int lapNumber = ((Long) tournament.get(JSON_LAP_NUMBER_KEY)).intValue();
        JSONArray participants = (JSONArray) tournament.get(JSON_PARTICIPANTS_KEY);
        Array<Team> teams = new Array<>();
        TeamReader teamsReader = TeamReader.getTeamReader();
        for (Object teamName : participants) {
            try {
                teams.add(teamsReader.getTeam((String) teamName));
            } catch (NoSuchElementException e) {
                Gdx.app.error(LOG_TAG, GET_TEAM_BY_NAME_ERROR_MESSAGE + teamName, e);
                HeadballGame.getActionResolver().showToast(GET_TEAM_BY_NAME_ERROR_MESSAGE + teamName);
            }
        }
        if (!toCreate) {
            return null;
        }
        if (tournament.get(JSON_TYPE_KEY).equals(JSON_LEAGUE_KEY)) {
            return new LeagueTournament(name, iconName, teams, lapNumber);
        }
        if (tournament.get(JSON_TYPE_KEY).equals(JSON_PLAYOFF_KEY)) {
            return new PlayOffTournament(name, iconName, teams, lapNumber);
        }
        throw new NoSuchElementException();
    }

    public Tournament getTournament(String name, boolean toCreate) {
        for (int i = 0; i < getTournamentsNumber(); i++) {
            if (name.equals(getTournamentName(i))) {
                return getTournament(i, toCreate);
            }
        }
        throw new NoSuchElementException();
    }

    private String getTournamentName(int index) {
        return (String) (getJSONTournament(index)).get(JSON_NAME_KEY);
    }

    public int getTournamentsNumber() {
        return tournaments.size();
    }

    private JSONObject getJSONTournament(int index) {
        if (index < 0 || index >= tournaments.size()) {
            throw new NoSuchElementException();
        }
        return (JSONObject) tournaments.get(index);
    }

    public static void setTournamentsPath(String tournamentsPath) {
        TournamentReader.tournamentsPath = tournamentsPath;
    }
}
