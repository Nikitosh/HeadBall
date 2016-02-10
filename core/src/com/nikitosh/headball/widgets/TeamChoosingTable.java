package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class TeamChoosingTable extends ChoosingTable {
    private Array<Team> teams;

    public TeamChoosingTable(Array<Team> teams) {
        this.teams = teams;
        for (Team team : teams) {
            addTeamCell(team);
        }
    }

    protected void addTeamCell(Team team) {
        Image teamImage = new Image(AssetLoader.teamsSkin.getDrawable(team.getIconName()));
        Label teamName = new Label(team.getName(), AssetLoader.gameLabelStyle);

        Table teamCell = new Table();
        teamCell.add(teamImage).pad(Constants.UI_ELEMENTS_INDENT).row();
        teamCell.add(teamName).pad(Constants.UI_ELEMENTS_INDENT).row();

        addElement(teamCell);
    }

    public Team getSelectedTeam() {
        return teams.get(currentIndex);
    }
}
