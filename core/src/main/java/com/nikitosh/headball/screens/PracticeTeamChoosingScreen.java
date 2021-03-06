package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamReader;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.TeamChoosingTable;

public class PracticeTeamChoosingScreen extends BackgroundStageAbstractScreen {
    private static final boolean IS_PRACTICE = true;
    private static final boolean IS_DRAW_POSSIBLE = true;
    private static final String CONTINUE = "Continue";

    public PracticeTeamChoosingScreen() {
        TeamReader reader = TeamReader.getTeamReader();
        Array<Team> teams = new Array<>();
        for (int i = 0; i < reader.getTeamsNumber(); i++) {
            teams.add(reader.getTeam(i));
        }
        final TeamChoosingTable choosingTable1 = new TeamChoosingTable(teams);
        final TeamChoosingTable choosingTable2 = new TeamChoosingTable(teams);

        TextButton continueButton = new TextButton(CONTINUE, AssetLoader.getGameSkin());
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new LevelChoosingScreen(new MatchInfo(
                        choosingTable1.getSelectedTeam(), choosingTable2.getSelectedTeam(),
                        IS_DRAW_POSSIBLE, IS_PRACTICE)));
            }
        });

        Table table = new Table();
        table.add(choosingTable1);
        table.add(choosingTable2).row();
        table.add(continueButton).colspan(2);
        table.setFillParent(true);

        stack.addActor(table);
        stack.addActor(new BackButtonTable());
    }
}
