package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.utils.AssetLoader;

public class LastRoundTable extends AbstractResultTable {

    private static final int COLUMN_COUNT = 3;

    public LastRoundTable() {
        super();
    }

    public void update(Array<Match> matches) {
        reset();
        statisticsLabels.clear();
        for (int i = 0; i < matches.size; i++) {
            Array<String> statistics = matches.get(i).getStatistics();
            Array<Label> matchStatisticsLabels = new Array<>();
            for (int j = 0; j < COLUMN_COUNT; j++) {
                matchStatisticsLabels.add(new Label(statistics.get(j), AssetLoader.getDefaultSkin()));
                add(matchStatisticsLabels.peek()).left();
            }
            statisticsLabels.add(matchStatisticsLabels);
            row();
        }
        for (Cell cell : getCells()) {
            cell.fillX();
        }
    }
}
