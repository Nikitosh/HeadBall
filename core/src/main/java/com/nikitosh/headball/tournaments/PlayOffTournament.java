package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.widgets.*;

import java.util.NoSuchElementException;
import java.util.Random;

public class PlayOffTournament implements Tournament {
    private static final int MAXIMUM_GOALS_NUMBER = 3;

    private String name;
    private String iconName;
    private int lapNumber;
    private int currentRound = 0;
    private Array<Team> teams = new Array<>();
    private Array<Integer> nextRoundParticipants;
    private final Array<Array<Integer>> tournamentBracket = new Array<>();
    private OlympicSystemTournamentWidget resultTable;
    private TournamentTimetable statisticsTable;
    private final Array<Match> currentRoundMatches = new Array<>();

    public PlayOffTournament() {}

    public PlayOffTournament(String name, String iconName, Array<Team> teams, int lapNumber) {
        this.lapNumber = lapNumber;
        this.name = name;
        this.iconName = iconName;
        this.teams = teams;

        generateTimetable();
        Array<Integer> firstRound = new Array<>();
        for (int i = 0; i < teams.size; i++) {
            firstRound.add(i);
        }
        tournamentBracket.add(firstRound);
        resultTable = new OlympicSystemTournamentWidget(lapNumber, teams, tournamentBracket);
        statisticsTable = new TournamentTimetable(
                new NextRoundTable(firstRound, teams), new LastRoundTable());
        statisticsTable.getLastRoundTable().setVisible(false);
    }

    private void generateTimetable() {
        teams.shuffle();
    }

    @Override
    public void simulateNextRound() {
        Random random = new Random();
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        for (int i = 0; i < currentRoundParticipants.size; i += 2) {
            if (nextRoundParticipants.get(i / 2) != null) {
                continue;
            }
            int firstTeamIndex = currentRoundParticipants.get(i);
            int secondTeamIndex = currentRoundParticipants.get(i + 1);
            int firstTeamScore = 0;
            int secondTeamScore = 0;
            while (firstTeamScore == secondTeamScore) { //draw is forbidden for play-off tournaments
                firstTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
                secondTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
            }
            Match match = new Match(teams.get(firstTeamIndex), teams.get(secondTeamIndex),
                    firstTeamScore, secondTeamScore);
            currentRoundMatches.add(match);
            MatchController.handle(match);
            nextRoundParticipants.set(i / 2, teams.indexOf(MatchController.getWinner(match), false));
        }
    }

    @Override
    public void handlePlayerMatch(Team team, int playerScore, int opponentScore) {
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        for (int i = 0; i < tournamentBracket.peek().size; i++) {
            if (teams.get(currentRoundParticipants.get(i)).equals(team)) {
                Match match = new Match(teams.get(currentRoundParticipants.get(i)),
                        teams.get(currentRoundParticipants.get(i ^ 1)), playerScore, opponentScore);
                currentRoundMatches.add(match);
                MatchController.handle(match);
                nextRoundParticipants.set(i / 2, teams.indexOf(MatchController.getWinner(match), false));
            }
        }
    }

    @Override
    public void startNewRound() {
        currentRoundMatches.clear();
        nextRoundParticipants = new Array<>();
        for (int i = 0; i < tournamentBracket.peek().size / 2; i++) {
            nextRoundParticipants.add(null);
        }
    }

    @Override
    public void endCurrentRound() {
        tournamentBracket.add(nextRoundParticipants);
        resultTable.update(teams, nextRoundParticipants);
        statisticsTable.getLastRoundTable().update(currentRoundMatches);
        statisticsTable.getLastRoundTable().setVisible(true);
        currentRound++;
        if (currentRound == lapNumber - 1) {
            statisticsTable.getNextRoundTable().setVisible(false);
        } else {
            statisticsTable.getNextRoundTable().update(nextRoundParticipants, teams);
        }
    }

    @Override
    public Team getNextOpponent(Team team) {
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        for (int i = 0; i < currentRoundParticipants.size; i++) {
            if (teams.get(currentRoundParticipants.get(i)).equals(team)) {
                return teams.get(currentRoundParticipants.get(i ^ 1));
            }
        }
        throw new NoSuchElementException();
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
        return statisticsTable;
    }

    @Override
    public boolean isEnded(Team team) {
        return tournamentBracket.peek().indexOf(teams.indexOf(team, false), false) == -1
                || currentRound == lapNumber - 1;
    }

    @Override
    public boolean isDrawResultPossible() {
        return false;
    }

    @Override
    public boolean isWinner(Team team) {
        return isEnded(team) && tournamentBracket.peek().indexOf(teams.indexOf(team, false), false) != -1;
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
