package com.nikitosh.headball;

public class MatchInfo {
    private final Team firstTeam;
    private final Team secondTeam;
    private final boolean isDrawResultPossible;
    private final boolean isRestartOrExitAvailable;
    private int levelNumber = 0;

    public MatchInfo(Team firstTeam, Team secondTeam, boolean isDrawResultPossible,
                     boolean isRestartOrExitPossible) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.isDrawResultPossible = isDrawResultPossible;
        this.isRestartOrExitAvailable = isRestartOrExitPossible;
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

    public boolean isRestartOrExitAvailable() {
        return isRestartOrExitAvailable;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
