package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.jsonReaders.TeamsReader;
import com.nikitosh.headball.tournaments.Tournament;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.ChoosingTable;

public class TeamChoosingScreen extends StageAbstractScreen {

    public TeamChoosingScreen(final Game game, final Screen previousScreen, final Tournament tournament) {
        ChoosingTable choosingTable = new ChoosingTable();

        final TeamsReader reader = new TeamsReader();
        for (int j = 0; j < tournament.getParticipants().size; j++) {
            for (int i = 0; i < reader.getTeamsNumber(); i++) {
                if (tournament.getParticipants().get(j).getName().equals(reader.getTeamName(i))) {
                    Table teamCell = new Table();

                    ImageButton teamImage = new ImageButton(AssetLoader.teamsSkin.getDrawable(reader.getTeamIconName(i)));
                    teamCell.add(teamImage).pad(Constants.UI_ELEMENTS_INDENT).row();
                    final int index = j;
                    teamImage.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            game.setScreen(new TournamentScreen(game, tournament, tournament.getParticipants().get(index)));
                        }
                    });

                    Label teamName = new Label(reader.getTeamName(i), AssetLoader.gameLabelStyle);
                    teamCell.add(teamName).pad(Constants.UI_ELEMENTS_INDENT).row();

                    choosingTable.addElement(teamCell);
                }
                choosingTable.setFillParent(true);

                stack.addActor(choosingTable);
                stack.addActor(new BackButtonTable(game, this, previousScreen));
            }
        }
    }
}
