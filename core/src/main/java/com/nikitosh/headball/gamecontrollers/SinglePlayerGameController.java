package com.nikitosh.headball.gamecontrollers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.nikitosh.headball.ActionResolverSingleton;
import com.nikitosh.headball.GameWorld;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.gameobjects.Ball;
import com.nikitosh.headball.gameobjects.Footballer;
import com.nikitosh.headball.gameobjects.Goals;
import com.nikitosh.headball.gameobjects.RectangleWall;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.players.EasyAIPlayer;
import com.nikitosh.headball.players.HardAIPlayer;
import com.nikitosh.headball.players.LocalHumanPlayer;
import com.nikitosh.headball.players.MediumAIPlayer;
import com.nikitosh.headball.screens.GameScreen;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

public class SinglePlayerGameController extends GameController {
    private GameWorld gameWorld;

    public SinglePlayerGameController(GameScreen gameScreen, MatchInfo matchInfo) {
        super(gameScreen, matchInfo);

        gameWorld = LevelReader.loadLevel(matchInfo.getLevelNumber());
        gameWorld.setDrawResultPossible(matchInfo.isDrawResultPossible());

        gameScreen.addPauseButton();

        playerNumber = 0;
        players[0] = new LocalHumanPlayer(getInputController());
        switch (GameSettings.getString(Constants.AI_LEVEL)) {
            case Constants.AI_LEVEL_EASY:
                players[1] = new EasyAIPlayer(gameWorld, 1);
                break;
            case Constants.AI_LEVEL_MEDIUM:
                players[1] = new MediumAIPlayer(gameWorld, 1);
                break;
            case Constants.AI_LEVEL_HARD:
                players[1] = new HardAIPlayer(gameWorld, 1);
                break;
        }
    }

    @Override
    public void update() {
        if (gameState == GameState.GAME_RUNNING) {
            accumulator += getDelta();
            gameWorld.update(accumulator, players[0].getMove(), players[1].getMove());
            accumulator = 0;
        }

        if (gameState == GameState.GAME_RUNNING && gameWorld.isGoal()
                && GameSettings.getBoolean(Constants.SETTINGS_SOUND)) {
            AssetLoader.getGoalSound().play();
        }

        if (gameWorld.isEnded()) {
            finishGame();
        }

        Ball ball = gameWorld.getBall();
        gameScreen.drawBall(ball.getPosition().x * Constants.BOX_TO_WORLD,
                ball.getPosition().y * Constants.BOX_TO_WORLD, ball.getRadius(), ball.getAngle());

        for (int i = 0; i < Constants.PLAYERS_NUMBER; i++) {
            Footballer[] footballers = gameWorld.getFootballers();
            Body body = footballers[i].getBody();
            gameScreen.drawFootballer(i, body.getPosition().x * Constants.BOX_TO_WORLD,
                    body.getPosition().y * Constants.BOX_TO_WORLD,
                    footballers[i].getRadius() * Constants.BOX_TO_WORLD, body.getAngle());

            Body leg = footballers[i].getLeg();
            gameScreen.drawLeg(i, leg.getPosition().x * Constants.BOX_TO_WORLD,
                    leg.getPosition().y * Constants.BOX_TO_WORLD,
                    Footballer.LEG_WIDTH, Footballer.LEG_HEIGHT, leg.getAngle());

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

        gameScreen.updateScoreLabel(gameWorld.getScore());
        gameScreen.updateTimerLabel(GameSettings.getInteger(Constants.GAME_DURATION)
                - Math.round(gameWorld.getGameDuration()));
    }

    @Override
    protected void finishGame() {
        super.finishGame();
        gameScreen.addGameOverWindow(gameWorld.getScore(), playerNumber);
        if (GameSettings.getString(Constants.SETTINGS_CONTROL).equals(Constants.SETTINGS_CONTROL_TOUCHPAD)) {
            ActionResolverSingleton.getInstance().unlockAchievement(Constants.ACHIEVEMENT_TOUCHPAD_GAMER);
        }
        int[] score = gameWorld.getScore();
        if (score[playerNumber] > score[1 - playerNumber]) {
            ActionResolverSingleton.getInstance().unlockAchievement(Constants.ACHIEVEMENT_FIRST_WIN);
            if (GameSettings.getString(Constants.SETTINGS_CONTROL)
                    .equals(Constants.SETTINGS_CONTROL_ACCELEROMETER)) {
                ActionResolverSingleton.getInstance().unlockAchievement(Constants.ACHIEVEMENT_SHAKE_IT);
            }
            if (score[playerNumber] >= Constants.ACHIEVEMENT_LUCKY_GUY_GOALS_NUMBER) {
                ActionResolverSingleton.getInstance().unlockAchievement(Constants.ACHIEVEMENT_LUCKY_GUY);
            }
        }
    }

    @Override
    public void restartGame() {
        super.restartGame();
        gameWorld.restartGame();
    }

    @Override
    public void exitGame() {
        super.exitGame();
        gameWorld.getBox2dWorld().dispose();
    }


    @Override
    public int[] getScore() {
        return gameWorld.getScore();
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
}
