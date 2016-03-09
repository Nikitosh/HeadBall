package com.nikitosh.headball.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.jsonReaders.TournamentReader;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

import java.util.NoSuchElementException;

public class TournamentChoosingTable extends ChoosingTable {
    private final Array<Tournament> tournaments;

    private static final String LOG_TAG = "TournamentChoosingTable";
    private static final String GET_TOURNAMENT_BY_NAME_ERROR_MESSAGE = "Can't get tournament with name: ";

    public TournamentChoosingTable(Array<Tournament> tournaments) {
        this.tournaments = tournaments;
        for (Tournament tournament : tournaments) {
            addTournamentCell(tournament);
        }
    }

    private void addTournamentCell(Tournament tournament) {
        Image tournamentImage = new Image(AssetLoader.getTournamentsSkin().getDrawable(tournament.getIconName()));
        Label tournamentName = new Label(tournament.getName(), AssetLoader.getGameSkin());

        Table teamCell = new Table();
        teamCell.add(tournamentImage).pad(Constants.UI_ELEMENTS_INDENT).row();
        teamCell.add(tournamentName).pad(Constants.UI_ELEMENTS_INDENT).row();

        addElement(teamCell);
    }

    public Tournament getSelectedTournament() {
        Tournament tournament;
        try {
            tournament = TournamentReader.getTournamentReader()
                .getTournament(tournaments.get(currentIndex).getName(), true);
        } catch (NoSuchElementException e) {
            Gdx.app.error(LOG_TAG,
                    GET_TOURNAMENT_BY_NAME_ERROR_MESSAGE + tournaments.get(currentIndex).getName(), e);
            throw e;
        }
        return tournament;
    }
}
