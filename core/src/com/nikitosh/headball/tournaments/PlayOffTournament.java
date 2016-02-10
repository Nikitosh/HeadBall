package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamsReader;
import com.nikitosh.headball.widgets.OlympicSystemTournamentWidget;
import com.nikitosh.headball.widgets.ResultTable;
import com.nikitosh.headball.widgets.StatisticsTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Random;

public class PlayOffTournament implements Tournament {
    private static final int MAXIMUM_GOALS_NUMBER = 3;
    private static final String LAP_NUMBER = "lapNumber";
    private static final String PARTICIPANTS = "participants";

    private String name;
    private String iconName;
    private int lapNumber;
    private int currentRound = 0;
    private Array<Team> teams = new Array<>();
    private Array<Integer> nextRoundParticipants;
    private Array<Array<Integer>> tournamentBracket = new Array<>();
    private OlympicSystemTournamentWidget resultTable;
    private StatisticsTable statisticsTable;

    public PlayOffTournament(String name, String iconName, Array <Team> teams, int lapNumber) {
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
        statisticsTable = new StatisticsTable(teams);
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
            int firstTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
            int secondTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
            Match match = new Match(teams.get(firstTeamIndex), teams.get(secondTeamIndex),
                    firstTeamScore, secondTeamScore);
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
                MatchController.handle(match);
                nextRoundParticipants.set(i / 2, teams.indexOf(MatchController.getWinner(match), false));
            }
        }
    }

    @Override
    public void startNewRound() {
        nextRoundParticipants = new Array<>();
        for (int i = 0; i < tournamentBracket.peek().size / 2; i++) {
            nextRoundParticipants.add(null);
        }
    }

    @Override
    public void endCurrentRound() {
        tournamentBracket.add(nextRoundParticipants);
        statisticsTable.update(teams);
        resultTable.update(teams, nextRoundParticipants);
        currentRound++;
    }

    @Override
    public Team getNextOpponent(Team team) {
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        for (int i = 0; i < currentRoundParticipants.size; i++) {
            if (teams.get(currentRoundParticipants.get(i)).equals(team)) {
                return teams.get(currentRoundParticipants.get(i ^ 1));
            }
        }
        assert(false);
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
        return statisticsTable;
    }

    @Override
    public boolean isEnded(Team team) {
        return tournamentBracket.peek().indexOf(teams.indexOf(team, false), false) == -1 || currentRound == lapNumber - 1;
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
