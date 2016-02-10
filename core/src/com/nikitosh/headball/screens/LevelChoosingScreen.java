package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.nikitosh.headball.LevelLoader;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.widgets.BackButtonTable;
import com.nikitosh.headball.widgets.ChoosingTable;

public class LevelChoosingScreen extends StageAbstractScreen {
    public LevelChoosingScreen(final Game game, final Screen previousScreen, final MatchInfo matchInfo) {
        final ChoosingTable choosingTable = new ChoosingTable();

        for (int i = 0; i < 2; i++) {
            Group level = LevelLoader.loadLevel(i).getGroup();
            level.setTransform(true);
            float coefficient = 0.7f;
            level.setScale(coefficient);
            level.setSize(Constants.VIRTUAL_WIDTH * coefficient,
                    (Constants.VIRTUAL_HEIGHT - Constants.UI_LAYER_HEIGHT) * coefficient);
            choosingTable.addElement(level);
        }

        choosingTable.setOnContinueListener(new Runnable() {
            @Override
            public void run() {
                matchInfo.setLevelNumber(choosingTable.getCurrentIndex());
                game.setScreen(new SinglePlayerScreen(game, new MainMenuScreen(game), matchInfo));
            }
        });
        choosingTable.setFillParent(true);

        stack.addActor(choosingTable);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
    }
}
