package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamReader;
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


    private int lapNumber;
    private int selectedTeamIndex;
    private int currentRound = 0;
    private Array<Team> teams = new Array<>();
    private Array<Integer> nextRoundParticipants;
    private Array<Array<Integer>> tournamentBracket = new Array<>();
    private OlympicSystemTournamentWidget resultTable;
    private StatisticsTable statisticsTable;

    public PlayOffTournament(JSONObject tournament) {
        lapNumber = ((Long) tournament.get(LAP_NUMBER)).intValue();
        JSONArray participantsNames = (JSONArray) tournament.get(PARTICIPANTS);
        TeamReader teamReader = new TeamReader();
        for (Object teamName : participantsNames) {
            teams.add(new Team((String) teamName));
        }
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
    public void setSelectedTeam(Team selectedTeam) {
        selectedTeamIndex = teams.indexOf(selectedTeam, false); //false means comparison with .equals() not ==
        assert(selectedTeamIndex != -1);
    }

    @Override
    public void simulateNextRound() {
        Random random = new Random();
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        nextRoundParticipants = new Array<>();
        for (int i = 0; i < currentRoundParticipants.size; i += 2) {
            int firstTeamIndex = currentRoundParticipants.get(i);
            int secondTeamIndex = currentRoundParticipants.get(i + 1);
            if (firstTeamIndex != selectedTeamIndex && secondTeamIndex != selectedTeamIndex) {
                int firstTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
                int secondTeamScore = random.nextInt(MAXIMUM_GOALS_NUMBER);
                Match match = new Match(teams.get(firstTeamIndex), teams.get(secondTeamIndex),
                        firstTeamScore, secondTeamScore);
                MatchController.handle(match);
                nextRoundParticipants.add(teams.indexOf(MatchController.getWinner(match), false));
            }
            else {
                nextRoundParticipants.add(null);
            }
        }
    }

    @Override
    public void handlePlayerMatch(int playerScore, int opponentScore) {
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        for (int i = 0; i < tournamentBracket.peek().size; i++) {
            if (currentRoundParticipants.get(i) == selectedTeamIndex) {
                Match match = new Match(teams.get(currentRoundParticipants.get(i)),
                        teams.get(currentRoundParticipants.get(i ^ 1)), playerScore, opponentScore);
                MatchController.handle(match);
                nextRoundParticipants.set(i / 2, teams.indexOf(MatchController.getWinner(match), false));
            }
        }
        tournamentBracket.add(nextRoundParticipants);
        statisticsTable.update(teams);
        resultTable.update(teams, nextRoundParticipants);
        currentRound++;
    }

    @Override
    public Team getNextOpponent() {
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        for (int i = 0; i < currentRoundParticipants.size; i++) {
            if (currentRoundParticipants.get(i) == selectedTeamIndex) {
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
    public boolean isEnded() {
        return tournamentBracket.peek().indexOf(selectedTeamIndex, false) == -1 || currentRound == lapNumber - 1;
    }

    @Override
    public boolean isDrawResultPossible() {
        return false;
    }

    @Override
    public boolean isWinner(Team team) {
        return isEnded() && tournamentBracket.peek().indexOf(teams.indexOf(team, false), false) != -1 &&
                currentRound == lapNumber - 1;
    }
}
