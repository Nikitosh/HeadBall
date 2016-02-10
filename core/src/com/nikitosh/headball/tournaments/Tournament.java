package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.widgets.ResultTable;

/*
For simulating one round you should at first call startNewRound(),
then call handlePlayerMatch() for all known results and then simulateNextRound() for generating and handling random
results for all remaining pairs of teams
 */

public interface Tournament {
    void simulateNextRound();
    void handlePlayerMatch(Team team, int playerScore, int opponentScore);
    void startNewRound();
    void endCurrentRound();
    Team getNextOpponent(Team team);
    Array<Team> getParticipants();
    ResultTable getResultTable();
    ResultTable getStatisticsTable();
    boolean isEnded(Team team);
    boolean isDrawResultPossible();
    boolean isWinner(Team team);
    String getName();
    String getIconName();
}
