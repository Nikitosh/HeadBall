package com.nikitosh.headball.tournaments;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.Team;
import com.nikitosh.headball.jsonReaders.TeamReader;
import com.nikitosh.headball.widgets.OlympicSystemTournamentWidget;
import com.nikitosh.headball.widgets.StatisticsTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Random;

public class PlayOffTournament implements Tournament {
    private int lapNumber;
    private int selectedTeamIndex;
    private int currentRound = 0;
    private Array<Team> teams = new Array<Team>();
    private Array<Integer> nextRoundParticipants = new Array<Integer>();
    private Array<Array<Integer>> tournamentBracket = new Array<Array<Integer>>();
    private OlympicSystemTournamentWidget resultTable;
    private StatisticsTable statisticsTable;

    public PlayOffTournament(JSONObject tournament) {
        lapNumber = ((Long) tournament.get("lapNumber")).intValue();
        JSONArray participantsNames = (JSONArray) tournament.get("participants");
        TeamReader teamReader = new TeamReader();
        for (int i = 0; i < participantsNames.size(); i++) {
            String teamName = (String) participantsNames.get(i);
            //TODO teams.add(teamReader.getTeam(teamName));
            teams.add(new Team(teamName));
        }
        generateTimetable();
        Array<Integer> firstRound = new Array<Integer>();
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
        selectedTeamIndex = teams.indexOf(selectedTeam, false); //false for .equals() using, not ==
        assert(selectedTeamIndex != -1);
    }

    @Override
    public void simulateNextRound() {
        Random random = new Random();
        Array<Integer> currentRoundParticipants = tournamentBracket.peek();
        nextRoundParticipants = new Array<Integer>();
        for (int i = 0; i < currentRoundParticipants.size; i += 2) {
            int firstTeamIndex = currentRoundParticipants.get(i);
            int secondTeamIndex = currentRoundParticipants.get(i + 1);
            if (firstTeamIndex != selectedTeamIndex && secondTeamIndex != selectedTeamIndex) {
                int firstTeamScore = random.nextInt(3);
                int secondTeamScore = random.nextInt(3);
                MatchController.handle(teams.get(firstTeamIndex), teams.get(secondTeamIndex),
                        firstTeamScore, secondTeamScore);
                nextRoundParticipants.add(teams.indexOf(MatchController.getWinner(teams.get(firstTeamIndex), teams.get(secondTeamIndex),
                        firstTeamScore, secondTeamScore), false));
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
                MatchController.handle(teams.get(currentRoundParticipants.get(i)),
                        teams.get(currentRoundParticipants.get(i ^ 1)), playerScore, opponentScore);
                nextRoundParticipants.set(i / 2, teams.indexOf(MatchController.getWinner(teams.get(currentRoundParticipants.get(i)),
                        teams.get(currentRoundParticipants.get(i ^ 1)), playerScore, opponentScore), false));
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
    public Group getResultTable() {
        return resultTable;
    }

    @Override
    public Group getStatisticsTable() {
        return statisticsTable;
    }

    @Override
    public boolean isEnded() {
        return tournamentBracket.peek().indexOf(selectedTeamIndex, true) == -1 || currentRound == lapNumber - 1;
    }
}
