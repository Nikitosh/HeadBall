package com.nikitosh.headball.jsonReaders;

import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.utils.Utilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class LevelReader {
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
    private static final String JSON_GOALS_Y_KEY = "goalsY";
    private static final String JSON_GOALS_LEFT_KEY = "goalsLeft";
    private static final String JSON_GOALS_WIDTH_KEY = "goalsWidth";
    private static final String JSON_GOALS_HEIGHT_KEY = "goalsHeight";
    private static final String JSON_CROSSBAR_HEIGHT_KEY = "crossbarHeight";
    private static final String JSON_WALLS_KEY = "walls";

    private static String levelsPath = "info/levels.json";

    private LevelReader() {}

    private static Array<Float> parseFloatArray(JSONArray jsonArray) {
        Array<Float> array = new Array<>();
        for (Object object : jsonArray) {
            array.add(((Long) object).floatValue());
        }
        return array;
    }

    private static Array<Float> parseFloatArrayFromKey(JSONObject level, String key) {
        return parseFloatArray((JSONArray) level.get(key));
    }

    private static Array<Boolean> parseBooleanArray(JSONObject level, String key) {
        JSONArray jsonArray = (JSONArray) level.get(key);
        Array<Boolean> array = new Array<>();
        for (Object object : jsonArray) {
            array.add((Boolean) object);
        }
        return array;
    }

    public static GameWorld loadLevel(int index, boolean toCreate) {
        JSONObject level = (JSONObject) ((JSONArray) Utilities.parseJSONFile(levelsPath).
                get(JSON_LEVELS_KEY)).get(index);
        float width = ((Long) level.get(JSON_WIDTH_KEY)).floatValue();
        float height = ((Long) level.get(JSON_HEIGHT_KEY)).floatValue();
        float footballerRadius = ((Long) level.get(JSON_FOOTBALLER_RADIUS_KEY)).floatValue();
        Array<Float> footballerPositionX = parseFloatArrayFromKey(level, JSON_FOOTBALLER_X_KEY);
        Array<Float> footballerPositionY = parseFloatArrayFromKey(level, JSON_FOOTBALLER_Y_KEY);
        Array<Boolean> footballerLeft = parseBooleanArray(level, JSON_FOOTBALLER_LEFT_KEY);
        float ballRadius = ((Long) level.get(JSON_BALL_RADIUS_KEY)).floatValue();
        float ballPositionX = ((Long) level.get(JSON_BALL_X_KEY)).floatValue();
        float ballPositionY = ((Long) level.get(JSON_BALL_Y_KEY)).floatValue();
        Array<Float> goalsPositionX = parseFloatArrayFromKey(level, JSON_GOALS_X_KEY);
        Array<Float> goalsPositionY = parseFloatArrayFromKey(level, JSON_GOALS_Y_KEY);
        Array<Boolean> goalsLeft = parseBooleanArray(level, JSON_GOALS_LEFT_KEY);
        float goalsWidth = ((Long) level.get(JSON_GOALS_WIDTH_KEY)).floatValue();
        float goalsHeight = ((Long) level.get(JSON_GOALS_HEIGHT_KEY)).floatValue();
        float crossbarHeight = ((Long) level.get(JSON_CROSSBAR_HEIGHT_KEY)).floatValue();
        JSONArray wallsArray = (JSONArray) level.get(JSON_WALLS_KEY);
        Array<Array<Float>> walls = new Array<>();
        for (Object wall : wallsArray) {
            walls.add(parseFloatArray((JSONArray) wall));
        }
        if (!toCreate) {
            return null;
        }
        GameWorld gameWorld = new GameWorld();
        gameWorld.setSize(width, height);
        gameWorld.createWalls(walls);
        gameWorld.createFootballers(footballerPositionX, footballerPositionY, footballerLeft, footballerRadius);
        gameWorld.createBall(ballPositionX, ballPositionY, ballRadius);
        gameWorld.createGoals(goalsPositionX, goalsPositionY, goalsWidth, goalsHeight, crossbarHeight, goalsLeft);
        return gameWorld;
    }

    public static int getLevelsNumber() {
        return ((JSONArray) Utilities.parseJSONFile(levelsPath).get(JSON_LEVELS_KEY)).size();
    }

    public static void setLevelsPath(String levelsPath) {
        LevelReader.levelsPath = levelsPath;
    }
}
