package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class OlympicSystemTournamentWidget extends WidgetGroup implements ResultTable, Json.Serializable {
    private int currentKnownTeamNumber = 0;
    private Array<Label> labels = new Array<>();
    private float width;
    private float height;
    private int roundNumber;
    private Array<Team> teams;
    private Array<Array<Integer>> tournamentBracket;

    private void initialise() {
        //maxWidth and maxHeight are used to make all labels in widget equal size
        float maxWidth = 0;
        float maxHeight = 0;
        for (int i = 0; i < teams.size; i++) {
            maxWidth = Math.max(maxWidth, new Label(teams.get(i).getName(), AssetLoader.defaultSkin).getPrefWidth());
            maxHeight = Math.max(maxHeight, new Label(teams.get(i).getName(), AssetLoader.defaultSkin).getPrefHeight());
        }

        int currentTeamIndex = 0;
        int currentTeamNumber = teams.size;
        for (int i = 0; i < roundNumber; i++) {
            for (int j = 0; j < currentTeamNumber; j++) {
                String name = "";
                //if there is result of current round in tournamentBracket
                if (i < tournamentBracket.size && j < tournamentBracket.get(i).size) {
                    name = teams.get(tournamentBracket.get(i).get(j)).getName();
                    currentKnownTeamNumber++;
                }

                Label label = new Label(name, AssetLoader.defaultSkin);
                label.setText(name);
                label.setSize(maxWidth, maxHeight);
                labels.add(label);

                if (currentTeamIndex >= teams.size) { //if it's not first column
                    //prevTeamIndex and prevTeamIndex + 1 are indices of two teams, winner of match between them
                    //will take place in current label
                    int prevTeamIndex = currentTeamIndex - j - 2 * currentTeamNumber + 2 * j;
                    label.setPosition(labels.get(prevTeamIndex).getX() + maxWidth + Constants.UI_ELEMENTS_INDENT,
                            (labels.get(prevTeamIndex).getY() + labels.get(prevTeamIndex + 1).getY()) / 2);
                }
                else { //first column, every next team is under previous
                    label.setPosition(Constants.UI_ELEMENTS_INDENT,
                            (maxHeight + Constants.UI_ELEMENTS_INDENT) * (teams.size - currentTeamIndex - 1) +
                                    Constants.UI_ELEMENTS_INDENT);
                }
                addActor(label);
                currentTeamIndex++;
            }
            currentTeamNumber /= 2;
        }
        width = labels.peek().getX() + maxWidth + Constants.UI_ELEMENTS_INDENT;
        height = labels.get(0).getY() + maxHeight + Constants.UI_ELEMENTS_INDENT;
    }

    public OlympicSystemTournamentWidget() {

    }

    public OlympicSystemTournamentWidget(int roundNumber, Array<Team> teams, Array<Array<Integer>> tournamentBracket) {
        this.roundNumber = roundNumber;
        this.teams = teams;
        this.tournamentBracket = tournamentBracket;
        initialise();
    }

    public void clearHighlighting() {
        for (int i = 0; i < labels.size; i++) {
            labels.get(i).setStyle(AssetLoader.defaultSkin.get(Label.LabelStyle.class));
        }
    }

    public void update(Array<Team> teams, Array<Integer> nextRoundParticipants) {
        clearHighlighting();
        for (int i = 0; i < nextRoundParticipants.size; i++) {
            labels.get(currentKnownTeamNumber++).setText(teams.get(nextRoundParticipants.get(i)).getName());
        }
    }

    @Override
    public Group getTable() {
        return this;
    }

    @Override
    public void highlightTeam(Team team) {
        for (int i = 0; i < labels.size; i++) {
            if (labels.get(i).getText().toString().equals(team.getName())) {
                labels.get(i).setStyle(HIGHLIGHTED_STYLE);
            }
        }
    }

    @Override
    public float getPrefWidth() {
        return width;
    }

    @Override
    public float getPrefHeight() {
        return height;
    }

    @Override
    public void write(Json json) {
        json.writeValue("roundNumber", roundNumber);
        json.writeValue("teams", teams);
        json.writeValue("tournamentBracket", tournamentBracket);
        Array<String> results = new Array<>();
        for (int i = 0; i < labels.size; i++) {
            results.add(labels.get(i).getText().toString());
        }
        json.writeValue("results", results);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        json.readField(this, "roundNumber", jsonData);
        json.readField(this, "teams", jsonData);
        json.readField(this, "tournamentBracket", jsonData);
        Array<String> results = new Array<>();
        initialise();
        results = json.readValue(results.getClass(), jsonData.get("results"));
        for (int i = 0; i < results.size; i++)
            labels.get(i).setText(results.get(i));
    }
}
