package com.nikitosh.headball.widgets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class OlympicSystemTournamentWidget extends WidgetGroup implements ResultTable {
    private int currentKnownTeamNumber = 0;
    private Array<Label> labels = new Array<Label>();
    private float width;
    private float height;

    public OlympicSystemTournamentWidget(int roundNumber, Array<Team> teams, Array<Array<Integer>> tournamentBracket) {
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
                if (i < tournamentBracket.size && j < tournamentBracket.get(i).size) {
                    name = teams.get(tournamentBracket.get(i).get(j)).getName();
                    currentKnownTeamNumber++;
                }
                Label label = new Label(name, AssetLoader.defaultSkin);
                label.setText(name);
                label.setSize(maxWidth, maxHeight);
                labels.add(label);

                if (currentTeamIndex >= teams.size) {
                    int prevTeamIndex = currentTeamIndex - j - 2 * currentTeamNumber + 2 * j;
                    label.setPosition(labels.get(prevTeamIndex).getX() + maxWidth + Constants.UI_ELEMENTS_INDENT,
                            (labels.get(prevTeamIndex).getY() + labels.get(prevTeamIndex + 1).getY()) / 2);
                }
                else {
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
}
