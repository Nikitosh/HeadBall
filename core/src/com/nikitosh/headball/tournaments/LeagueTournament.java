package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Match;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.widgets.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.security.acl.Group;
import java.util.Comparator;
import java.util.Random;

public class LeagueTournament implements Tournament {
    private int lapNumber;
    private int selectedTeamIndex = 0;
    private int currentRound = 0;
    private Array<Team> teams = new Array<>();
    private Array<Match> currentRoundMatches = new Array<>();
    private Array<Array<Integer>> timeTable = new Array<>();
    private StatisticsTable resultTable;
    private LastRoundTable lastRoundTable;
    private NextRoundTable nextRoundTable;
    private LeagueTournamentStatisticsTable leagueTournamentStatisticsTable;

    public LeagueTournament(JSONObject tournament) {
        lapNumber = ((Long) tournament.get("lapNumber")).intValue();
        JSONArray participantsNames = (JSONArray) tournament.get("participants");
        for (int i = 0; i < participantsNames.size(); i++) {
            String teamName = (String) participantsNames.get(i);
            teams.add(new Team(teamName));
        }
        generateTimetable();

        resultTable = new StatisticsTable(teams);
        lastRoundTable = new LastRoundTable(teams.size / 2);
        lastRoundTable.setVisible(false);
        nextRoundTable = new NextRoundTable(timeTable.get(0), teams);
        leagueTournamentStatisticsTable = new LeagueTournamentStatisticsTable(nextRoundTable, lastRoundTable);
    }

    private void generateTimetable() {
        teams.shuffle();
        Array<Integer> nextRound = new Array<>();
        for (int i = 0; i < teams.size; i++) {
            nextRound.add(i);
        }
        for (int i = 0; i < lapNumber; i++) {
            int tmp = 0;
            for (int j = nextRound.size - 1; j > 1; j--) {
                tmp = nextRound.get(j - 1);
                nextRound.set(j - 1, nextRound.get(nextRound.size - 1));
                nextRound.set(nextRound.size - 1, tmp);
            }
            timeTable.add(new Array<>(nextRound));
        }
    }

    @Override
    public void setSelectedTeam(Team teamName) {
        selectedTeamIndex = teams.indexOf(teamName, false);
        assert(selectedTeamIndex != -1);
    }

    @Override
    public void simulateNextRound() {
        Random random = new Random();
        Array<Integer> currentRoundParticipants = timeTable.get(currentRound);
        for (int i = 0; i < currentRoundParticipants.size; i += 2) {
            int firstTeamIndex = currentRoundParticipants.get(i);
            int secondTeamIndex = currentRoundParticipants.get(i + 1);
            if (firstTeamIndex != selectedTeamIndex && secondTeamIndex != selectedTeamIndex) {
                int firstTeamScore = random.nextInt(3);
                int secondTeamScore = random.nextInt(3);
                Match match = new Match(teams.get(firstTeamIndex),
                        teams.get(secondTeamIndex), firstTeamScore, secondTeamScore);
                currentRoundMatches.add(match);
                MatchController.handle(match);
            }
        }
    }

    @Override
    public void handlePlayerMatch(int playerScore, int opponentScore) {
        Array<Integer> currentRoundParticipants = timeTable.get(currentRound);
        for (int i = 0; i < currentRoundParticipants.size; i++) {
            if (currentRoundParticipants.get(i) == selectedTeamIndex) {
                Match match = new Match(teams.get(currentRoundParticipants.get(i)),
                        teams.get(currentRoundParticipants.get(i ^ 1)), playerScore, opponentScore);
                currentRoundMatches.add(match);
                MatchController.handle(match);
            }
        }
        resultTable.update(teams);
        lastRoundTable.update(currentRoundMatches);
        lastRoundTable.setVisible(true);
        currentRoundMatches.clear();
        currentRound++;
        if (currentRound == lapNumber) {
            nextRoundTable.setVisible(false);
        } else {
            nextRoundTable.update(timeTable.get(currentRound), teams);
        }
    }

    @Override
    public Team getNextOpponent() {
        Array<Integer> currentRoundParticipants = timeTable.get(currentRound);
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
        return leagueTournamentStatisticsTable;
    }

    @Override
    public boolean isEnded() {
        return currentRound == lapNumber;
    }

    @Override
    public boolean isDrawResultPossible() {
        return true;
    }

    @Override
    public boolean isWinner(Team team) {
        if (!isEnded()) {
            return false;
        }
        Array<Team> sortedTeams = teams;
        sortedTeams.sort(Team.getComparator());
        return sortedTeams.get(0).equals(team);
    }
}
