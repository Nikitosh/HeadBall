package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.ChoosingTable;
import com.nikitosh.headball.jsonReaders.TournamentsReader;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class TournamentChoosingScreen extends StageAbstractScreen {
    public TournamentChoosingScreen(final Game game, final Screen previousScreen) {
        ChoosingTable choosingTable = new ChoosingTable();

        final TournamentsReader reader = new TournamentsReader();

        for (int i = 0; i < reader.getTournamentsNumber(); i++) {
            Table tournamentCell = new Table();

            ImageButton tournamentImage = new ImageButton(AssetLoader.tournamentsSkin.getDrawable(reader.getTournamentIconName(i)));
            tournamentCell.add(tournamentImage).pad(Constants.UI_ELEMENTS_INDENT).row();
            final int index = i;
            tournamentImage.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Tournament tournament = reader.getTournament(index);
                    game.setScreen(new TeamChoosingScreen(game, TournamentChoosingScreen.this, tournament));
                }
            });

            Label tournamentName = new Label(reader.getTournamentName(i), AssetLoader.gameLabelStyle);
            tournamentCell.add(tournamentName).pad(Constants.UI_ELEMENTS_INDENT).row();

            choosingTable.addElement(tournamentCell);
        }
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
    }
}
