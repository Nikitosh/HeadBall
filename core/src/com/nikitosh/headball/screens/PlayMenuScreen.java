package com.nikitosh.headball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.Team;
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

    public PlayMenuScreen(final Game game, final Screen previousScreen) {
        Image background = new Image(AssetLoader.menuTexture);
        background.setFillParent(true);

        Button practiceTextButton = new GameTextButtonTouchable(PRACTICE);
        practiceTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SinglePlayerScreen(game, PlayMenuScreen.this,
                        new MatchInfo(new Team("Player"), new Team("Bot"), IS_DRAW_POSSIBLE, IS_PRACTICE)));
            }
        });

        Button tournamentTextButton = new GameTextButtonTouchable(TOURNAMENT);
        tournamentTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new TournamentChoosingScreen(game, PlayMenuScreen.this));
            }
        });

        Button multiPlayerTextButton = new GameTextButtonTouchable(MULTIPLAYER);
        multiPlayerTextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MultiPlayerScreen(game, PlayMenuScreen.this,
                        new MatchInfo(new Team(""), new Team(""), IS_DRAW_POSSIBLE, IS_PRACTICE)));
            }
        });

        Table menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.add(practiceTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        menuTable.add(tournamentTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();
        menuTable.add(multiPlayerTextButton).pad(Constants.UI_ELEMENTS_INDENT).row();

        stack.addActor(background);
        stack.addActor(menuTable);
        stack.addActor(new BackButtonTable(game, this, previousScreen));
    }
}
