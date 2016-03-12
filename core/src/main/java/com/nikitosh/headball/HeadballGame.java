package com.nikitosh.headball;

import com.badlogic.gdx.Game;
import com.nikitosh.headball.screens.SplashScreen;
import com.nikitosh.headball.utils.ScreenManager;

public class HeadballGame extends Game {

    public HeadballGame(ActionResolver actionResolver) {
        super();
        ActionResolverSingleton.initialize(actionResolver);
    }

    @Override
    public void create() {
        ActionResolverSingleton.getInstance().signIn();
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().setScreen(new SplashScreen());
    }
}
