package com.nikitosh.headball;


import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

public class HeadballGameTest {
    @Test
    public void testTeamsJson() {
        File schemaFile = new File("..\\android\\assets\\info\\teamsSchema.json");
        File jsonFile = new File("..\\android\\assets\\info\\teams.json");

        try {
            assertTrue(ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testTournamentsJson() {
        File schemaFile = new File("..\\android\\assets\\info\\tournamentsSchema.json");
        File jsonFile = new File("..\\android\\assets\\info\\tournaments.json");

        try {
            assertTrue(ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testLevelsJson() {
        File schemaFile = new File("..\\android\\assets\\info\\levelsSchema.json");
        File jsonFile = new File("..\\android\\assets\\info\\levels.json");

        try {
            assertTrue(ValidationUtils.isJsonValid(schemaFile, jsonFile));
        } catch (Exception e) {
            fail();
        }
    }

}

