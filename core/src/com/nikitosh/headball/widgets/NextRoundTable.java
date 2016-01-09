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

public class NextRoundTable extends Table implements ResultTable {

    private Array<Array<Label>> statisticsLabels = new Array<Array<Label>>();

    public NextRoundTable(Array<Integer> nextRoundTimeTable, Array<Team> teams) {
        super();
        for (int i = 0; i < teams.size / 2; i++) {
            Array<Label> matchLabels = new Array<>();
            matchLabels.add(new Label(teams.get(nextRoundTimeTable.get(i * 2)).getName(), AssetLoader.defaultSkin));
            add(matchLabels.peek()).left();
            matchLabels.add(new Label("vs", AssetLoader.defaultSkin));
            add(matchLabels.peek()).left();
            matchLabels.add(new Label(teams.get(nextRoundTimeTable.get(i * 2 + 1)).getName(), AssetLoader.defaultSkin));
            add(matchLabels.peek()).left();
            statisticsLabels.add(matchLabels);
            row();
        }
        for (Cell cell : getCells()) {
            cell.fillX();
        }
    }

    public void update(Array<Integer> nextRoundTimeTable, Array<Team> teams) {
        for (int i = 0; i < teams.size / 2; i++) {
            statisticsLabels.get(i).get(0).setText(teams.get(nextRoundTimeTable.get(i * 2)).getName());
            statisticsLabels.get(i).get(1).setText("vs");
            statisticsLabels.get(i).get(2).setText(teams.get(nextRoundTimeTable.get(i * 2 + 1)).getName());
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
                Array<Label> matchesLabels = statisticsLabels.get(i);
                for (int j = 0; j < matchesLabels.size; j++) {
                    matchesLabels.get(j).setStyle(HIGHLIGHTED_STYLE);
                }
            }
        }
    }
}
