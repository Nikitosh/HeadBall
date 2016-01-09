package com.nikitosh.headball.tournaments;

import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;

public class MatchController {
    public static void handle(Match match) {
        match.getFirstTeam().incrementGoalsFor(match.getFirstTeamScore());
        match.getFirstTeam().incrementGoalsAgainst(match.getSecondTeamScore());
        match.getSecondTeam().incrementGoalsFor(match.getSecondTeamScore());
        match.getSecondTeam().incrementGoalsAgainst(match.getFirstTeamScore());

        if (match.getFirstTeamScore() == match.getSecondTeamScore()) {
            match.getFirstTeam().incrementDrawNumber();
            match.getSecondTeam().incrementDrawNumber();
        }
        if (match.getFirstTeamScore() < match.getSecondTeamScore()) {
            match.getFirstTeam().incrementLossNumber();
            match.getSecondTeam().incrementWinNumber();
        }
        if (match.getFirstTeamScore() > match.getSecondTeamScore()) {
            match.getFirstTeam().incrementWinNumber();
            match.getSecondTeam().incrementLossNumber();
        }
    }

    public static Team getWinner(Match match) {
        if (match.getFirstTeamScore() >= match.getSecondTeamScore())
            return match.getFirstTeam();
        return match.getSecondTeam();
    }
}
