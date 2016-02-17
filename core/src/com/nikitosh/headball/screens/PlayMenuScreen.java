package com.nikitosh.headball.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nikitosh.headball.ScreenManager;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.ui.GameTextButtonTouchable;
import com.nikitosh.headball.widgets.BackButtonTable;

public class PlayMenuScreen extends StageAbstractScreen {
    private static final String PRACTICE = "Practice";
    private static final String TOURNAMENT = "Tournament";
    private static final String MULTIPLAYER = "Multiplayer";
    private static final boolean IS_PRACTICE = true;
    private static final boolean IS_DRAW_POSSIBLE = true;

    public PlayMenuScreen() {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Button practiceTextButton = new GameTextButtonTouchable(PRACTICE);
        practiceTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new PracticeTeamChoosingScreen());
            }
        });

        Button tournamentTextButton = new GameTextButtonTouchable(TOURNAMENT);
        tournamentTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new TournamentChoosingScreen());
            }
        });

        Button multiPlayerTextButton = new GameTextButtonTouchable(MULTIPLAYER);
        multiPlayerTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().setScreen(new MultiPlayerWaitingScreen());
            }
        });

        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.add(practiceTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        menuTable.add(tournamentTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        menuTable.add(multiPlayerTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        stack.addActor(background);
        stack.addActor(menuTable);
        stack.addActor(new BackButtonTable());
    }
}
