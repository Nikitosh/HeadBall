package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.nikitosh.headball.screens.SplashScreen;

public class HeadballGame extends Game {
    public static ActionResolver actionResolver;

    public HeadballGame(ActionResolver actionResolver) {
        super();
        HeadballGame.actionResolver = actionResolver;
    }

    @Override
    public void create() {
        actionResolver.signIn();
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().setScreen(new SplashScreen());
    }
}
