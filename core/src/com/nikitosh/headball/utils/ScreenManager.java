package com.nikitosh.headball.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

public final class ScreenManager {
    private static ScreenManager screenManager;
    private Array<Screen> screens = new Array<>();
    private Game game;

    private static final String LOG_TAG = "ScreenManager";
    private static final String
            DISPOSE_CURRENT_SCREEN_ERROR_MESSAGE = "Not enough screens for disposing. Method ignored";

    private ScreenManager() {}

    public static ScreenManager getInstance() {
        if (screenManager == null) {
            screenManager = new ScreenManager();
        }
        return screenManager;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    public void setScreen(Screen screen) {
        screens.add(screen);
        game.setScreen(screen);
    }

    public void disposeCurrentScreens(int count) {
        for (int i = 0; i < count; i++) {
            disposeCurrentScreen();
        }
    }

    public void disposeCurrentScreen() {
        if (screens.size < 2) {
            Gdx.app.log(LOG_TAG, DISPOSE_CURRENT_SCREEN_ERROR_MESSAGE);
            return;
        }
        screens.pop().dispose();
        game.setScreen(screens.peek());
    }
}
