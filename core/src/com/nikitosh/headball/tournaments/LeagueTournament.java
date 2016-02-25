package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.widgets.*;

import java.util.HashSet;
import java.util.Random;

public class LeagueTournament implements Tournament {
    private static final int MAXIMUM_GOALS_NUMBER = 3;

    private String name;
    private String iconName;
    private int lapNumber;
    private int currentRound = 0;
    private Array<Team> teams = new Array<>();
    private Array<Match> currentRoundMatches = new Array<>();
    private HashSet<Integer> playedCurrentRoundTeams = new HashSet<>();
    private Array<Array<Integer>> timeTable = new Array<>();
    private StatisticsTable resultTable;
    private LeagueTournamentStatisticsTable leagueTournamentStatisticsTable;

    public LeagueTournament() {
    }

    public LeagueTournament(String name, String iconName, Array<Team> teams, int lapNumber) {
        this.lapNumber = lapNumber;
        this.name = name;
        this.iconName = iconName;
        this.teams = teams;

        generateTimetable();
        resultTable = new StatisticsTable(teams);
        leagueTournamentStatisticsTable = new LeagueTournamentStatisticsTable(
                new NextRoundTable(timeTable.get(0), teams), new LastRoundTable(teams.size / 2));
        leagueTournamentStatisticsTable.getLastRoundTable().setVisible(false);

    }

    private void generateTimetable() {
        teams.shuffle();
        Array<Integer> nextRound = new Array<>();
        for (int i = 0; i < teams.size; i++) {
            nextRound.add(i);
        }
        for (int i = 0; i < lapNumber; i++) {
            for (int j = nextRound.size - 1; j > 1; j--) {
                int tmp = nextRound.get(j - 1);
                nextRound.set(j - 1, nextRound.get(nextRound.size - 1));
                nextRound.set(nextRound.size - 1, tmp);
            }
            timeTable.add(new Array<>(nextRound));
        }
    }

    @Override
    public void simulateNextRound() {
        Random random = new Random();
        Array<Integer> currentRoundParticipants = timeTable.get(currentRound);
        for (int i = 0; i < currentRoundParticipants.size; i += 2) {
            int firstTeamIndex = currentRoundParticipants.get(i);
            int secondTeamIndex = currentRoundParticipants.get(i + 1);
            if (!playedCurrentRoundTeams.contains(firstTeamIndex)) {
                int firstTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
                int secondTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
                Match match = new Match(teams.get(firstTeamIndex),
                        teams.get(secondTeamIndex), firstTeamScore, secondTeamScore);
                currentRoundMatches.add(match);
                MatchController.handle(match);
            }
        }
    }

    @Override
    public void handlePlayerMatch(Team team, int playerScore, int opponentScore) {
        Array<Integer> currentRoundParticipants = timeTable.get(currentRound);
        for (int i = 0; i < currentRoundParticipants.size; i++) {
            if (teams.get(currentRoundParticipants.get(i)).equals(team)) {
                Match match = new Match(teams.get(currentRoundParticipants.get(i)),
                        teams.get(currentRoundParticipants.get(i ^ 1)), playerScore, opponentScore);
                playedCurrentRoundTeams.add(currentRoundParticipants.get(i));
                playedCurrentRoundTeams.add(currentRoundParticipants.get(i ^ 1));
                currentRoundMatches.add(match);
                MatchController.handle(match);
            }
        }
    }

    @Override
    public void startNewRound() {
        currentRoundMatches.clear();
        playedCurrentRoundTeams.clear();
    }

    @Override
    public void endCurrentRound() {
        resultTable.update(teams);
        leagueTournamentStatisticsTable.getLastRoundTable().update(currentRoundMatches);
        leagueTournamentStatisticsTable.getLastRoundTable().setVisible(true);
        currentRound++;
        if (currentRound == lapNumber) {
            leagueTournamentStatisticsTable.getNextRoundTable().setVisible(false);
        } else {
            leagueTournamentStatisticsTable.getNextRoundTable().update(timeTable.get(currentRound), teams);
        }
    }

    @Override
    public Team getNextOpponent(Team team) {
        Array<Integer> currentRoundParticipants = timeTable.get(currentRound);
        for (int i = 0; i < currentRoundParticipants.size; i++) {
            if (teams.get(currentRoundParticipants.get(i)).equals(team)) {
                return teams.get(currentRoundParticipants.get(i ^ 1));
            }
        }
        assert (false);
        return null;
    }

    @Override
    public Array<Team> getParticipants() {
        return teams;
    }

    @Override
    public ResultTable getResultTable() {
        return resultTable;
    }

    @Override
    public ResultTable getStatisticsTable() {
        return leagueTournamentStatisticsTable;
    }

    @Override
    public boolean isEnded(Team team) {
        return currentRound == lapNumber;
    }

    @Override
    public boolean isDrawResultPossible() {
        return true;
    }

    @Override
    public boolean isWinner(Team team) {
        if (!isEnded(team)) {
            return false;
        }
        Array<Team> sortedTeams = teams;
        sortedTeams.sort(Team.getComparator());
        return sortedTeams.get(0).equals(team);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIconName() {
        return iconName;
    }

}
