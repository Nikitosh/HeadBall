package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.Pair;

/**
 * Created by Wowember on 18.02.2016.
 */
public class TournamentDeserializer {

    private TournamentDeserializer() {

    }

    public static Pair<Tournament, Team> deserialize(Class<? extends Tournament> tournamentClass, String tournamentName) {
        Json json = new Json();
        FileHandle file = Gdx.files.internal("tournaments/saves/"+tournamentName+".json");
        JsonValue input = new JsonReader().parse(file.readString());
        Tournament tournament = json.readValue(tournamentClass, input.get(0));
        Team team = json.readValue(Team.class, input.get(1));
        return new Pair<>(tournament, team);
    }

}
