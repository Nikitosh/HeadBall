package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.Pair;

public final class TournamentSerializer {

    private TournamentSerializer() {}

    public static void serialize(Tournament tournament, Team playerTeam) {
        FileHandle file = Gdx.files.local(Constants.TOURNAMENTS_SAVES_PATH + tournament.getName()
                + Constants.JSON);
        Json json = new Json();
        file.writeString(json.prettyPrint(new Pair<>(tournament, playerTeam)), false);
    }
}
