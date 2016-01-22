package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.widgets.ResultTable;

public interface Tournament {
    void setSelectedTeam(Team teamName);
    void simulateNextRound();
    void handlePlayerMatch(int playerScore, int opponentScore);
    Team getNextOpponent();
    Array<Team> getParticipants();
    ResultTable getResultTable();
    ResultTable getStatisticsTable();
    boolean isEnded(Team team);
    boolean isDrawResultPossible();
    boolean isWinner(Team team);
}
