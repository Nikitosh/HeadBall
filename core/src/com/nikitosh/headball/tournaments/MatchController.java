package com.nikitosh.headball.tournaments;

import com.nikitosh.headball.Team;

public class MatchController {
    public static void handle(Team firstTeam, Team secondTeam, int firstGoalNumber, int secondGoalNumber) {
        firstTeam.incrementGoalsFor(firstGoalNumber);
        firstTeam.incrementGoalsAgainst(secondGoalNumber);
        secondTeam.incrementGoalsFor(secondGoalNumber);
        secondTeam.incrementGoalsAgainst(firstGoalNumber);

        if (firstGoalNumber == secondGoalNumber) {
            firstTeam.incrementDrawNumber();
            secondTeam.incrementDrawNumber();
        }
        if (firstGoalNumber < secondGoalNumber) {
            firstTeam.incrementLossNumber();
            secondTeam.incrementWinNumber();
        }
        if (firstGoalNumber > secondGoalNumber) {
            firstTeam.incrementWinNumber();
            secondTeam.incrementLossNumber();
        }
    }

    public static Team getWinner(Team firstTeam, Team secondTeam, int firstGoalNumber, int secondGoalNumber) {
        if (firstGoalNumber >= secondGoalNumber)
            return firstTeam;
        return secondTeam;
    }
}
