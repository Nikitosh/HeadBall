package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

public class NextRoundTable extends AbstractResultTable {

    public NextRoundTable() {
        super();
    }

    public NextRoundTable(Array<Integer> nextRoundTimeTable, Array<Team> teams) {
        super();
        update(nextRoundTimeTable, teams);
    }

    public void update(Array<Integer> nextRoundTimeTable, Array<Team> teams) {
        reset();
        statisticsLabels.clear();
        for (int i = 0; i < nextRoundTimeTable.size / 2; i++) {
            Array<Label> matchLabels = new Array<>();
            matchLabels.add(new Label(teams.get(nextRoundTimeTable.get(i * 2)).getName(),
                    AssetLoader.getDefaultSkin()));
            add(matchLabels.peek()).left();
            matchLabels.add(new Label("vs", AssetLoader.getDefaultSkin()));
            add(matchLabels.peek()).left();
            matchLabels.add(new Label(teams.get(nextRoundTimeTable.get(i * 2 + 1)).getName(),
                    AssetLoader.getDefaultSkin()));
            add(matchLabels.peek()).left();
            statisticsLabels.add(matchLabels);
            row();
        }
        for (Cell cell : getCells()) {
            cell.fillX();
        }
    }
}
