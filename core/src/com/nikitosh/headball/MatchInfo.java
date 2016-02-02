package com.nikitosh.headball;

public class MatchInfo {
    private Team firstTeam;
    private Team secondTeam;
    private boolean isDrawResultPossible;
    private boolean isRestartOrExitPossible;

    public MatchInfo(Team firstTeam, Team secondTeam, boolean isDrawResultPossible, boolean isRestartOrExitPossible) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.isDrawResultPossible = isDrawResultPossible;
        this.isRestartOrExitPossible = isRestartOrExitPossible;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public boolean isDrawResultPossible() {
        return isDrawResultPossible;
    }

    public boolean isRestartOrExitPossible() {
        return isRestartOrExitPossible;
    }
}
