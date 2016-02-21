package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

public class ScreenManager {
    private static ScreenManager screenManager;
    private Array<Screen> screens = new Array<>();
    private Game game;

    private ScreenManager() {
    }

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
        assert(screens.size > 1);
        screens.pop().dispose();
        game.setScreen(screens.peek());
    }
}
