package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamsReader;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.TeamChoosingTable;

public class PracticeTeamChoosingScreen extends StageAbstractScreen {
    private static final boolean IS_PRACTICE = true;
    private static final boolean IS_DRAW_POSSIBLE = true;

    public PracticeTeamChoosingScreen(final Game game, Screen previousScreen) {
        TeamsReader reader = new TeamsReader();
        Array<Team> teams = new Array();
        for (int i = 0; i < reader.getTeamsNumber(); i++) {
            teams.add(reader.getTeam(i));
        }
        final TeamChoosingTable choosingTable1 = new TeamChoosingTable(teams);
        final TeamChoosingTable choosingTable2 = new TeamChoosingTable(teams);

        GameTextButtonTouchable continueButton = new GameTextButtonTouchable("Continue");
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelChoosingScreen(game, new MainMenuScreen(game),
                        new MatchInfo(choosingTable1.getSelectedTeam(), choosingTable2.getSelectedTeam(),
                                IS_DRAW_POSSIBLE, IS_PRACTICE)));
            }
        });

        Table table = new Table();
        table.add(choosingTable1);
        table.add(choosingTable2).row();
        table.add(continueButton).colspan(2);
        table.setFillParent(true);

        stack.addActor(table);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
    }
}
