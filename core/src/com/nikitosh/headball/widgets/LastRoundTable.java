package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

public class LastRoundTable extends Table implements ResultTable {

    private final static int SCORE_COLUMN = 1;
    private final static int COLUMN_COUNT = 3;

    private Array<Array<Label>> statisticsLabels = new Array<Array<Label>>();

    public LastRoundTable(int size) {
        super();
        for (int i = 0; i < size; i++) {
            Array<Label> matchStatisticsLabels = new Array<Label>();
            for (int j = 0; j < COLUMN_COUNT; j++) {
                matchStatisticsLabels.add(new Label("", AssetLoader.defaultSkin));
                add(matchStatisticsLabels.peek()).left();
            }
            statisticsLabels.add(matchStatisticsLabels);
            row();
        }
        for (Cell cell : getCells()) {
            cell.fillX();
        }
    }

    public void update(Array<Match> matches) {
        for (int i = 0; i < matches.size; i++) {
            Match match = matches.get(i);
            Array<String> statistics = match.getStatistics();
            for (int j = 0; j < statistics.size; j++) {
                statisticsLabels.get(i).get(j).setText(statistics.get(j));
                if (j == SCORE_COLUMN) {
                    statisticsLabels.get(i).get(j).setAlignment(Align.center);
                }
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
            if (statisticsLabels.get(i).get(0).getText().toString().equals(team.getName()) ||
                    statisticsLabels.get(i).get(2).getText().toString().equals(team.getName())) {
                Array<Label> matchesStatisticsLabels = statisticsLabels.get(i);
                for (int j = 0; j < matchesStatisticsLabels.size; j++) {
                    matchesStatisticsLabels.get(j).setStyle(HIGHLIGHTED_STYLE);
                }
            }
        }
    }
}
