package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.Constants;

public class LeagueTournamentStatisticsTable extends Table implements ResultTable, Json.Serializable {

    private NextRoundTable nextRoundTable;
    private LastRoundTable lastRoundTable;

    public LeagueTournamentStatisticsTable() {

    }

    public LeagueTournamentStatisticsTable(NextRoundTable nextRoundTable, LastRoundTable lastRoundTable) {
        this.nextRoundTable = nextRoundTable;
        this.lastRoundTable = lastRoundTable;

        add(nextRoundTable.getTable()).row();
        add(lastRoundTable.getTable()).pad(Constants.UI_ELEMENTS_INDENT);
    }

    public LastRoundTable getLastRoundTable() {
        return lastRoundTable;
    }

    public NextRoundTable getNextRoundTable() {
        return nextRoundTable;
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
        add(nextRoundTable.getTable()).row();
        add(lastRoundTable.getTable()).pad(Constants.UI_ELEMENTS_INDENT);
    }
}
