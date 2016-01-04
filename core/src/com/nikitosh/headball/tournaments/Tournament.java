package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;

public interface Tournament {
    public void setSelectedTeam(Team teamName);
    public void simulateNextRound();
    public void handlePlayerMatch(int playerScore, int opponentScore);
    public Team getNextOpponent();
    public Array<Team> getParticipants();
    public Group getResultTable();
    public Group getStatisticsTable();
    public boolean isEnded();
}
