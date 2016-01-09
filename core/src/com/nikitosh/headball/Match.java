package com.nikitosh.headball;

import com.badlogic.gdx.utils.Array;

public class Match {

    private Team firstTeam = null;
    private Team secondTeam = null;
    private int firstTeamScore = 0;
    private int secondTeamScore = 0;


    public Match(Team firstTeam, Team secondTeam, int firstTeamScore, int secondTeamScore) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstTeamScore = firstTeamScore;
        this.secondTeamScore = secondTeamScore;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public int getFirstTeamScore() {
        return firstTeamScore;
    }

    public int getSecondTeamScore() {
        return secondTeamScore;
    }

    public Array<String> getStatistics() {
        return new Array<>(new String[] {firstTeam.getName(), firstTeamScore+" : "+secondTeamScore, secondTeam.getName()});
    }
}

