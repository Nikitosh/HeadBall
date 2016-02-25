package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.nikitosh.headball.jsonReaders.LevelReader;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.ChoosingTable;

public class LevelChoosingScreen extends BackgroundStageAbstractScreen {
    private static final float SCALE_COEFFICIENT = 0.7f;

    public LevelChoosingScreen(final MatchInfo matchInfo) {
        final ChoosingTable choosingTable = new ChoosingTable();

        for (int i = 0; i < LevelReader.getLevelsNumber(); i++) {
            Group level = LevelReader.loadLevel(i).getGroup();
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
                ScreenManager.getInstance().disposeCurrentScreens(2);
                ScreenManager.getInstance().setScreen(new SinglePlayerScreen(matchInfo));
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable());
    }
}
