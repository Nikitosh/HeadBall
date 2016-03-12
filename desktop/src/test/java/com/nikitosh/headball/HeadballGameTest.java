package com.nikitosh.headball;

import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.jsonReaders.TeamReader;
import com.nikitosh.headball.jsonReaders.TournamentReader;
import com.nikitosh.headball.tournaments.Tournament;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class HeadballGameTest extends GdxInitializer {
    private static final String LEVELS_PATH = "..\\android\\assets\\info\\levels.json";
    private static final String LEVELS_SCHEMA_PATH = "..\\android\\assets\\info\\levelsSchema.json";
    private static final String TEAMS_PATH = "..\\android\\assets\\info\\teams.json";
    private static final String TEAMS_SCHEMA_PATH = "..\\android\\assets\\info\\teamsSchema.json";
    private static final String TOURNAMENTS_PATH = "..\\android\\assets\\info\\tournaments.json";
    private static final String TOURNAMENTS_SCHEMA_PATH = "..\\android\\assets\\info\\tournamentsSchema.json";

    @Test
    public void testTeamsJson() {
        File schemaFile = new File(TEAMS_SCHEMA_PATH);
        File jsonFile = new File(TEAMS_PATH);

        try {
            assertTrue(ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testTournamentsJson() {
        File schemaFile = new File(TOURNAMENTS_SCHEMA_PATH);
        File jsonFile = new File(TOURNAMENTS_PATH);

        try {
            assertTrue(ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLevelsJson() {
        File schemaFile = new File(LEVELS_SCHEMA_PATH);
        File jsonFile = new File(LEVELS_PATH);

        try {
            assertTrue(ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLevelsParsing() {
        LevelReader.setLevelsPath(LEVELS_PATH);
        int levelsNumber = LevelReader.getLevelsNumber();
        System.out.print(levelsNumber);
        for (int i = 0; i < levelsNumber; i++) {
            try {
                LevelReader.loadLevel(i);
            } catch (Exception e) { //if exception during parsing occurs
                e.printStackTrace();
                fail();
            }
        }
    }

    @Test
    public void testTeamsParsing() {
        TeamReader.setTeamsPath(TEAMS_PATH);
        TeamReader teamReader = TeamReader.getTeamReader();
        int teamsNumber = teamReader.getTeamsNumber();
        for (int i = 0; i < teamsNumber; i++) {
            try {
                Team teamByIndex = teamReader.getTeam(i);
                Team teamByName = teamReader.getTeam(teamByIndex.getName());
                assertEquals(teamByIndex, teamByName);
            } catch (Exception e) { //if exception during parsing occurs
                fail();
            }
        }
    }

    @Test
    public void testTournamentsParsing(){
        TournamentReader.setTournamentsPath(TOURNAMENTS_PATH);
        TournamentReader tournamentReader = TournamentReader.getTournamentReader();
        int tournamentsNumber = tournamentReader.getTournamentsNumber();
        for (int i = 0; i < tournamentsNumber; i++) {
            try {
                Tournament tournament = tournamentReader.getTournament(i);
            } catch (Exception e) { //if exception during parsing occurs
                e.printStackTrace();
                fail();
            }
        }
    }
}

