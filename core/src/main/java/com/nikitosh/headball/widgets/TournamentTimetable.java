package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.Constants;

public class TournamentTimetable extends Table implements ResultTable, Json.Serializable {

    private NextRoundTable nextRoundTable;
    private LastRoundTable lastRoundTable;

    public TournamentTimetable() {}

    public TournamentTimetable(NextRoundTable nextRoundTable, LastRoundTable lastRoundTable) {
        this.nextRoundTable = nextRoundTable;
        this.lastRoundTable = lastRoundTable;

        add(new ScrollPane(nextRoundTable.getTable())).height(Constants.TABLES_HEGIHT / 2).row();
        add(new ScrollPane(lastRoundTable.getTable())).height(Constants.TABLES_HEGIHT / 2)
                .pad(Constants.UI_ELEMENTS_INDENT);
    }

    public LastRoundTable getLastRoundTable() {
        return lastRoundTable;
    }

    public void setLastRoundTable(LastRoundTable lastRoundTable) {
        this.lastRoundTable = lastRoundTable;
    }

    public NextRoundTable getNextRoundTable() {
        return nextRoundTable;
    }

    public void setNextRoundTable(NextRoundTable nextRoundTable) {
        this.nextRoundTable = nextRoundTable;
    }

    @Override
    public Group getTable() {
        return this;
    }

    @Override
    public void highlightTeam(Team team) {
        nextRoundTable.highlightTeam(team);
        lastRoundTable.highlightTeam(team);
    }

    @Override
    public void write(Json json) {
        json.writeValue("nextRoundTable", nextRoundTable);
        json.writeValue("lastRoundTable", lastRoundTable);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        json.readField(this, "nextRoundTable", jsonData);
        json.readField(this, "lastRoundTable", jsonData);
        add(new ScrollPane(nextRoundTable.getTable())).height(Constants.TABLES_HEGIHT / 2).row();
        add(new ScrollPane(lastRoundTable.getTable())).height(Constants.TABLES_HEGIHT / 2)
                .pad(Constants.UI_ELEMENTS_INDENT);
    }
}
