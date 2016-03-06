package com.nikitosh.headball;

import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

public class Team {
    private static Comparator<Team> comparator = new Comparator<Team>() {
        @Override
        public int compare(Team o1, Team o2) {
            if (o1.points == o2.points) {
                return o1.winNumber < o2.winNumber ? 1 : -1;
            }
            return o1.points < o2.points ? 1 : -1;
        }
    };

    private static final int WIN_POINTS = 3;
    private static final int DRAW_POINTS = 1;

    private String name;
    private String iconName;
    private int points = 0;
    private int winNumber = 0;
    private int drawNumber = 0;
    private int lossNumber = 0;
    private int goalsFor = 0;
    private int goalsAgainst = 0;

    public Team() {}

    public Team(String name, String iconName) {
        this.name = name;
        this.iconName = iconName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Team && name.equals(((Team) obj).getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getIconName() {
        return iconName;
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

    public Array<Integer> getStatistics() {
        return new Array<>(new Integer[] {winNumber, drawNumber, lossNumber,
                goalsFor, goalsAgainst, goalsFor - goalsAgainst, points});
    }

    public static Comparator<Team> getComparator() {
        return comparator;
    }

}
