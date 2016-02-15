package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

public abstract class AbstractResultTable extends Table implements ResultTable {
    protected Array<Array<Label>> statisticsLabels = new Array<>();

    @Override
    public Group getTable() {
        return this;
    }

    @Override
    public void highlightTeam(Team team) {
        for (int i = 0; i < statisticsLabels.size; i++) {
            Array<Label> rowStatisticsLabels = statisticsLabels.get(i);
            for (Label label : rowStatisticsLabels) {
                label.setStyle(AssetLoader.defaultSkin.get(Label.LabelStyle.class));
            }
            for (int j = 0; j < rowStatisticsLabels.size; j++) {
                if (rowStatisticsLabels.get(j).getText().toString().equals(team.getName())) {
                    for (Label label : rowStatisticsLabels) {
                        label.setStyle(HIGHLIGHTED_STYLE);
                    }
                }
            }
        }
    }
}
