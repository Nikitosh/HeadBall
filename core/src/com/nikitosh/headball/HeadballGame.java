package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.nikitosh.headball.screens.SplashScreen;

public class HeadballGame extends Game {
    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().setScreen(new SplashScreen());
    }
}
