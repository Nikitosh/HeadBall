package com.nikitosh.headball;

import com.badlogic.gdx.utils.Array;

public class Team {
    private static final int WIN_POINTS = 3;
    private static final int DRAW_POINTS = 1;

    private String name;
    private String iconName = "";
    private int points = 0;
    private int winNumber = 0;
    private int drawNumber = 0;
    private int lossNumber = 0;
    private int goalsFor = 0;
    private int goalsAgainst = 0;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void incrementWinNumber() {
        winNumber++;
        points += WIN_POINTS;
    }

    public void incrementDrawNumber() {
        drawNumber++;
        points += DRAW_POINTS;
    }

    public void incrementLossNumber() {
        lossNumber++;
    }

    public void incrementGoalsFor(int goalsNumber) {
        goalsFor += goalsNumber;
    }

    public void incrementGoalsAgainst(int goalsNumber) {
        goalsAgainst += goalsNumber;
    }

    public int getWinNumber() {
        return winNumber;
    }

    public int getDrawNumber() {
        return drawNumber;
    }

    public int getLossNumber() {
        return lossNumber;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public Array<Integer> getStatistics() {
        return new Array<Integer>(new Integer[] {winNumber, drawNumber, lossNumber, goalsFor, goalsAgainst, goalsFor - goalsAgainst, points});
    }
}