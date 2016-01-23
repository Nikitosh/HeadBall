package com.nikitosh.headball;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class LevelLoader {
    private static final String LEVELS_PATH = "info/levels.json";
    private static final String JSON_LEVELS_KEY = "levels";
    private static final String JSON_WIDTH_KEY = "width";
    private static final String JSON_HEIGHT_KEY = "height";
    private static final String JSON_FOOTBALLER_RADIUS_KEY = "footballerRadius";
    private static final String JSON_FOOTBALLER_X_KEY = "footballersX";
    private static final String JSON_FOOTBALLER_Y_KEY = "footballersY";
    private static final String JSON_FOOTBALLER_LEFT_KEY = "footballersLeft";
    private static final String JSON_BALL_RADIUS_KEY = "ballRadius";
    private static final String JSON_BALL_X_KEY = "ballX";
    private static final String JSON_BALL_Y_KEY = "ballY";
    private static final String JSON_GOALS_X_KEY = "goalsX";
    private static final String JSON_GOALS_Y_KEY= "goalsY";
    private static final String JSON_GOALS_LEFT_KEY = "goalsLeft";
    private static final String JSON_GOALS_WIDTH_KEY = "goalsWidth";
    private static final String JSON_GOALS_HEIGHT_KEY = "goalsHeight";
    private static final String JSON_CROSSBAR_HEIGHT_KEY = "crossbarHeight";
    private static final String JSON_WALLS_X_KEY = "wallsX";
    private static final String JSON_WALLS_Y_KEY= "wallsY";
    private static final String JSON_WALLS_WIDTH_KEY = "wallsWidth";
    private static final String JSON_WALLS_HEIGHT_KEY = "wallsHeight";

    private static Array<Float> parseFloatArray(JSONObject level, String key) {
        JSONArray jsonArray = (JSONArray) level.get(key);
        Array<Float> array = new Array<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            array.add(((Long) jsonArray.get(i)).floatValue());
        }
        return array;
    }

    private static Array<Boolean> parseBooleanArray(JSONObject level, String key) {
        JSONArray jsonArray = (JSONArray) level.get(key);
        Array<Boolean> array = new Array<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            array.add((Boolean) jsonArray.get(i));
        }
        return array;
    }

    public static GameWorld loadLevel(int index) {
        JSONObject level = (JSONObject) ((JSONArray) Utilities.parseJSONFile(LEVELS_PATH).get(JSON_LEVELS_KEY)).get(index);
        float width = ((Long) level.get(JSON_WIDTH_KEY)).floatValue();
        float height = ((Long) level.get(JSON_HEIGHT_KEY)).floatValue();
        float footballerRadius = ((Long) level.get(JSON_FOOTBALLER_RADIUS_KEY)).floatValue();
        Array<Float> footballerPositionX = parseFloatArray(level, JSON_FOOTBALLER_X_KEY);
        Array<Float> footballerPositionY = parseFloatArray(level, JSON_FOOTBALLER_Y_KEY);
        Array<Boolean> footballerLeft = parseBooleanArray(level, JSON_FOOTBALLER_LEFT_KEY);
        float ballRadius = ((Long) level.get(JSON_BALL_RADIUS_KEY)).floatValue();
        float ballPositionX = ((Long) level.get(JSON_BALL_X_KEY)).floatValue();
        float ballPositionY = ((Long) level.get(JSON_BALL_Y_KEY)).floatValue();
        Array<Float> goalsPositionX = parseFloatArray(level, JSON_GOALS_X_KEY);
        Array<Float> goalsPositionY = parseFloatArray(level, JSON_GOALS_Y_KEY);
        Array<Boolean> goalsLeft = parseBooleanArray(level, JSON_GOALS_LEFT_KEY);
        float goalsWidth = ((Long) level.get(JSON_GOALS_WIDTH_KEY)).floatValue();
        float goalsHeight = ((Long) level.get(JSON_GOALS_HEIGHT_KEY)).floatValue();
        float crossbarHeight = ((Long) level.get(JSON_CROSSBAR_HEIGHT_KEY)).floatValue();
        Array<Float> wallsPositionX = parseFloatArray(level, JSON_WALLS_X_KEY);
        Array<Float> wallsPositionY = parseFloatArray(level, JSON_WALLS_Y_KEY);
        Array<Float> wallsWidth = parseFloatArray(level, JSON_WALLS_WIDTH_KEY);
        Array<Float> wallsHeight = parseFloatArray(level, JSON_WALLS_HEIGHT_KEY);

        GameWorld gameWorld = new GameWorld();
        gameWorld.setSize(width, height);
        gameWorld.createWalls(wallsPositionX, wallsPositionY, wallsWidth, wallsHeight);
        gameWorld.createFootballers(footballerPositionX, footballerPositionY, footballerLeft, footballerRadius);
        gameWorld.createBall(ballPositionX, ballPositionY, ballRadius);
        gameWorld.createGoals(goalsPositionX, goalsPositionY, goalsWidth, goalsHeight, crossbarHeight, goalsLeft);
        return gameWorld;
    }
}
