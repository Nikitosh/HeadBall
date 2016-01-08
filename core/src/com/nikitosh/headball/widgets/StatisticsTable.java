package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

import java.util.Comparator;

public class StatisticsTable extends Table implements ResultTable {
    private static final Array<String> COLUMN_NAMES = new Array<String>(new String[] {"Team", "W", "D", "L", "GF", "GA", "GD", "Points"});

    private Array<Array<Label>> statisticsLabels = new Array<Array<Label>>();

    public StatisticsTable(Array<Team> teams) {
        super();
        for (int i = 0; i < COLUMN_NAMES.size; i++) {
            add(new Label(COLUMN_NAMES.get(i), AssetLoader.defaultSkin));
        }
        row();
        for (int i = 0; i < teams.size; i++) {
            Team team = teams.get(i);
            Array<Label> teamStatisticsLabels = new Array<Label>();
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
        Array<Team> sortedTeams = new Array<Team>(teams);
        sortedTeams.sort(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if (o1.getPoints() == o2.getPoints()) {
                    return o1.getWinNumber() < o2.getWinNumber() ? 1 : -1;
                }
                return o1.getPoints() < o2.getPoints() ? 1 : -1;
            }
        });
        return sortedTeams;
    }

    public void clearHighlighting() {
        for (int i = 0; i < statisticsLabels.size; i++) {
            for (int j = 0; j < statisticsLabels.get(i).size; j++) {
                statisticsLabels.get(i).get(j).setStyle(AssetLoader.defaultSkin.get(Label.LabelStyle.class));
            }
        }
    }

    public void update(Array<Team> teams) {
        clearHighlighting();
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

    @Override
    public Group getTable() {
        return this;
    }

    @Override
    public void highlightTeam(Team team) {
        for (int i = 0; i < statisticsLabels.size; i++) {
            if (statisticsLabels.get(i).get(0).getText().toString().equals(team.getName())) {
                Array<Label> teamsStatisticsLabels = statisticsLabels.get(i);
                for (int j = 0; j < teamsStatisticsLabels.size; j++) {
                    teamsStatisticsLabels.get(j).setStyle(HIGHLIGHTED_STYLE);
                }
            }
        }
    }
}
