package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

import java.util.Comparator;

public class StatisticsTable extends AbstractResultTable {
    private static final Array<String> COLUMN_NAMES = new Array<>(new String[] {"Team", "W", "D", "L", "GF", "GA", "GD", "Points"});

    public StatisticsTable(Array<Team> teams) {
        super();
        for (int i = 0; i < COLUMN_NAMES.size; i++) {
            add(new Label(COLUMN_NAMES.get(i), AssetLoader.defaultSkin));
        }
        row();
        for (int i = 0; i < teams.size; i++) {
            Team team = teams.get(i);
            Array<Label> teamStatisticsLabels = new Array<>();
            teamStatisticsLabels.add(new Label(team.getName(), AssetLoader.defaultSkin));
            add(teamStatisticsLabels.peek()).left();
            Array<Integer> statistics = team.getStatistics();
            for (int j = 0; j < team.getStatistics().size; j++) {
                teamStatisticsLabels.add(new Label(Integer.toString(statistics.get(j)), AssetLoader.defaultSkin));
                add(teamStatisticsLabels.peek()).left();
            }
            statisticsLabels.add(teamStatisticsLabels);
            row();
        }
        for (Cell cell : getCells()) {
            cell.fillX();
        }
    }

    public Array<Team> getSortedTeams(Array<Team> teams) {
        Array<Team> sortedTeams = new Array<>(teams);
        sortedTeams.sort(Team.getComparator());
        return sortedTeams;
    }

    public void update(Array<Team> teams) {
        Array<Team> sortedTeams = getSortedTeams(teams);
        for (int i = 0; i < sortedTeams.size; i++) {
            Team team = sortedTeams.get(i);
            statisticsLabels.get(i).get(0).setText(team.getName());
            Array<Integer> statistics = team.getStatistics();
            for (int j = 0; j < team.getStatistics().size; j++) {
                statisticsLabels.get(i).get(j + 1).setText(Integer.toString(statistics.get(j)));
            }
        }
    }
}
