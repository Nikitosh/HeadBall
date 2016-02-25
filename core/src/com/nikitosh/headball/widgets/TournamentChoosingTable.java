package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.jsonReaders.TournamentReader;
import com.nikitosh.headball.tournaments.PlayOffTournament;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class TournamentChoosingTable extends ChoosingTable {
    private Array<Tournament> tournaments;

    public TournamentChoosingTable(Array<Tournament> tournaments) {
        this.tournaments = tournaments;
        for (Tournament tournament : tournaments) {
            addTournamentCell(tournament);
        }
    }

    protected void addTournamentCell(Tournament tournament) {
        Image tournamentImage = new Image(AssetLoader.tournamentsSkin.getDrawable(tournament.getIconName()));
        Label tournamentName = new Label(tournament.getName(), AssetLoader.gameSkin);

        Table teamCell = new Table();
        teamCell.add(tournamentImage).pad(Constants.UI_ELEMENTS_INDENT).row();
        teamCell.add(tournamentName).pad(Constants.UI_ELEMENTS_INDENT).row();

        addElement(teamCell);
    }

    public Tournament getSelectedTournament() {
        return TournamentReader.getTournamentsReader()
                .getTournament(tournaments.get(currentIndex).getName());
    }
}
