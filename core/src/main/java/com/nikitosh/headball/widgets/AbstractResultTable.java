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

public abstract class AbstractResultTable extends Table implements ResultTable, Json.Serializable {
    protected final Array<Array<Label>> statisticsLabels = new Array<>();

    @Override
    public Group getTable() {
        return this;
    }

    @Override
    public void highlightTeam(Team team) {
        for (Array<Label> rowStatisticsLabels : statisticsLabels) {
            for (Label label : rowStatisticsLabels) {
                label.setStyle(AssetLoader.getDefaultSkin().get(Label.LabelStyle.class));
            }
            boolean needHighLighting = false;
            for (Label rowLabel : rowStatisticsLabels) {
                if (rowLabel.getText().toString().equals(team.getName())) {
                    needHighLighting = true;
                    break;
                }
            }
            if (needHighLighting) {
                for (Label rowLabel : rowStatisticsLabels) {
                    rowLabel.setStyle(HIGHLIGHTED_STYLE);
                }
            }
        }
    }

    @Override
    public void write(Json json) {
        Array<Array<String>> statistics = new Array<>();
        for (Array<Label> rowStatisticsLabels : statisticsLabels) {
            Array<String> rowStatistics = new Array<>();
            for (Label label : rowStatisticsLabels) {
                rowStatistics.add(label.getText().toString());
            }
            statistics.add(rowStatistics);
        }
        json.writeValue("statistics", statistics);
        json.writeValue("isVisible", this.isVisible());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        Array<Array<String>> statistics = new Array<>();
        statistics = json.readValue(statistics.getClass(), jsonData.child());
        for (Array<String> rowStatistics : statistics) {
            Array<Label> teamStatisticLabel = new Array<>();
            for (String text : rowStatistics) {
                teamStatisticLabel.add(new Label(text, AssetLoader.getDefaultSkin()));
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
