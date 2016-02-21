package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;

import java.io.Serializable;

public abstract class AbstractResultTable extends Table implements ResultTable, Json.Serializable {
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

    @Override
    public void write(Json json) {
        Array<Array<String>> statistics = new Array<>();
        for (int i = 0; i < statisticsLabels.size; i++) {
            Array<String> tmp = new Array<>();
            for (int j = 0; j < statisticsLabels.get(i).size; j++) {
                tmp.add(statisticsLabels.get(i).get(j).getText().toString());
            }
            statistics.add(tmp);
        }
        json.writeValue("statistics", statistics);
        json.writeValue("isVisible", this.isVisible());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        Array<Array<String>> statistics = new Array<>();
        statistics = json.readValue(statistics.getClass(), jsonData.child());
        for (int i = 0; i < statistics.size; i++) {
            Array<Label> teamStatisticLabel = new Array<>();
            for (int j = 0; j < statistics.get(i).size; j++) {
                teamStatisticLabel.add(new Label(statistics.get(i).get(j), AssetLoader.defaultSkin));
                add(teamStatisticLabel.peek()).left();
            }
            row();
            statisticsLabels.add(teamStatisticLabel);
        }
        this.setVisible(jsonData.get("isVisible").asBoolean());
        for (Cell cell : getCells()) {
            cell.fillX();
        }
    }

}
