package com.nikitosh.headball.gamecontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.gameobjects.Footballer;
import com.nikitosh.headball.gameobjects.Goals;
import com.nikitosh.headball.gameobjects.RectangleWall;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.players.Player;
import com.nikitosh.headball.screens.GameScreen;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiPlayerGameController extends GameController {

    private static final String LOG_TAG = "MultiPlayerGameController";
    private static final String DESERIALIZE_ERROR_MESSAGE = "Deserialization gameWorldFrame failed";

    private static final int POSITION_X_INDEX = 0;
    private static final int POSITION_Y_INDEX = 1;
    private static final int ANGLE_INDEX = 2;
    private static final int RADIUS_INDEX = 3;

    private static final int IS_ENDED_FLAG_INDEX = 0;
    private static final int BALL_PARAMETERS_INDEX = 1;
    private static final int FIRST_PLAYER_PARAMETERS_INDEX = 2;
    private static final int FIRST_PLAYER_LEG_PARAMETERS_INDEX = 3;
    private static final int SECOND_PLAYER_PARAMETERS_INDEX = 4;
    private static final int SECOND_PLAYER_LEG_PARAMETERS_INDEX = 5;
    private static final int SCORE_INDEX = 6;
    private static final int GAME_DURATION_INDEX = 7;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private GameWorld gameWorld;
    private Player player;
    private int[] score;

    private String[] ballParameters;
    private String[] scoreSerialization;
    private String gameDuration;
    private final Array<String[]> footballersParameters = new Array<>();
    private final Array<String[]> footballersLegsParameters = new Array<>();

    public MultiPlayerGameController(GameScreen gameScreen,
                                     MatchInfo matchInfo, Socket socket) throws IOException {
        super(gameScreen, matchInfo);
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        if (inputStream.readUTF().equals("0\n")) {
            playerNumber = 0;
        } else {
            playerNumber = 1;
        }

        gameWorld = LevelReader.loadLevel(matchInfo.getLevelNumber(), true);
        gameWorld.setDrawResultPossible(matchInfo.isDrawResultPossible());

        player = new LocalHumanPlayer(getInputController());
        score = new int[Constants.PLAYERS_NUMBER];
    }

    private void deserializeGameWorldFrame() {
        if (isGameNotFinished()) {
            String[] gameWorldFrameSerialization;
            try {
                gameWorldFrameSerialization = inputStream.readUTF()
                        .split(String.valueOf(Constants.DATA_SEPARATOR));
            } catch (IOException e) {
                Gdx.app.error(LOG_TAG, DESERIALIZE_ERROR_MESSAGE, e);
                return;
            }
            boolean isEnded = Boolean.parseBoolean(gameWorldFrameSerialization[IS_ENDED_FLAG_INDEX]);
            if (isEnded) {
                finishGame();
                return;
            }

            footballersParameters.clear();
            footballersLegsParameters.clear();
            ballParameters = gameWorldFrameSerialization[BALL_PARAMETERS_INDEX].split(" ");
            footballersParameters.add(gameWorldFrameSerialization[FIRST_PLAYER_PARAMETERS_INDEX].split(" "));
            footballersLegsParameters.add(
                    gameWorldFrameSerialization[FIRST_PLAYER_LEG_PARAMETERS_INDEX].split(" "));
            footballersParameters.add(gameWorldFrameSerialization[SECOND_PLAYER_PARAMETERS_INDEX].split(" "));
            footballersLegsParameters.add(
                    gameWorldFrameSerialization[SECOND_PLAYER_LEG_PARAMETERS_INDEX].split(" "));
            scoreSerialization = gameWorldFrameSerialization[SCORE_INDEX].split(" ");
            gameDuration = gameWorldFrameSerialization[GAME_DURATION_INDEX];
        }
    }

    @Override
    public void update() {

        deserializeGameWorldFrame();

        if (isGameNotFinished()) {


            gameScreen.drawBall(Float.parseFloat(ballParameters[POSITION_X_INDEX]) * Constants.BOX_TO_WORLD,
                    Float.parseFloat(ballParameters[POSITION_Y_INDEX]) * Constants.BOX_TO_WORLD,
                    Float.parseFloat(ballParameters[RADIUS_INDEX]),
                    Float.parseFloat(ballParameters[ANGLE_INDEX]));

            for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
                gameScreen.drawFootballer(i,
                        Float.parseFloat(footballersParameters.get(i)[POSITION_X_INDEX])
                                * Constants.BOX_TO_WORLD,
                        Float.parseFloat(footballersParameters.get(i)[POSITION_Y_INDEX])
                                * Constants.BOX_TO_WORLD,
                        Float.parseFloat(footballersParameters.get(i)[RADIUS_INDEX]),
                        Float.parseFloat(footballersParameters.get(i)[ANGLE_INDEX]));

                gameScreen.drawLeg(i,
                        Float.parseFloat(footballersLegsParameters.get(i)[POSITION_X_INDEX])
                                * Constants.BOX_TO_WORLD,
                        Float.parseFloat(footballersLegsParameters.get(i)[POSITION_Y_INDEX])
                                * Constants.BOX_TO_WORLD,
                        Footballer.LEG_WIDTH, Footballer.LEG_HEIGHT,
                        Float.parseFloat(footballersLegsParameters.get(i)[ANGLE_INDEX]));

                Goals[] goals = gameWorld.getGoals();
                gameScreen.drawGoals(i, goals[i].getPosition().x, goals[i].getPosition().y,
                        goals[i].getWidth(), goals[i].getHeight(), 0f);
            }

            Array<RectangleWall> walls = gameWorld.getWalls();
            Array<Array<Float>> wallRectangles = new Array<>();
            for (RectangleWall wall : walls) {
                wallRectangles.add(wall.getRectangle());
            }
            gameScreen.drawWall(wallRectangles);

            for (int i = 0; i < score.length; i++) {
                int newScore = Integer.valueOf(scoreSerialization[i]);
                if (newScore != score[i] && GameSettings.getBoolean(Constants.SETTINGS_SOUND)) {
                    AssetLoader.getGoalSound().play();
                    score[i] = newScore;
                }
            }

            gameScreen.updateScoreLabel(score);
            gameScreen.updateTimerLabel(Integer.parseInt(gameDuration.replaceAll("\n", "")));

            player.getMove().serialize(outputStream);
        }

    }

    @Override
    public int[] getScore() {
        return score;
    }

    @Override
    protected void finishGame() {
        super.finishGame();
        gameScreen.addGameOverWindow(score, playerNumber);
    }

    @Override
    public void pauseGame() {
    }
}
