package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.nikitosh.headball.*;
import com.nikitosh.headball.gamecontrollers.SinglePlayerGameController;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.ChoosingTable;

public class LevelChoosingScreen extends BackgroundStageAbstractScreen {
    private static final float SCALE_COEFFICIENT = 0.7f;

    public LevelChoosingScreen(final MatchInfo matchInfo) {
        final ChoosingTable choosingTable = new ChoosingTable();

        for (int i = 0; i < LevelReader.getLevelsNumber(); i++) {
            GameScreen gameScreen = new GameScreen();
            SinglePlayerGameController gameController = new SinglePlayerGameController(gameScreen,
                    new MatchInfo(new Team("", ""), new Team("", ""), false, false));
            gameController.setGameWorld(LevelReader.loadLevel(i, true));
            gameController.update();
            Group level = gameScreen.getField();
            level.setTransform(true);
            level.setScale(SCALE_COEFFICIENT);
            level.setSize(Constants.VIRTUAL_WIDTH * SCALE_COEFFICIENT,
                    (Constants.VIRTUAL_HEIGHT - Constants.UI_LAYER_HEIGHT) * SCALE_COEFFICIENT);
            choosingTable.addElement(level);
        }

        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                matchInfo.setLevelNumber(choosingTable.getCurrentIndex());
                //dispose 2 screens: LevelChoosingScreen and PracticeTeamChoosingScreen
                ScreenManager.getInstance().disposeLastScreens(2);
                GameScreen gameScreen = new GameScreen();
                new SinglePlayerGameController(gameScreen, matchInfo);
                ScreenManager.getInstance().setScreen(gameScreen);
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable());
    }
}
