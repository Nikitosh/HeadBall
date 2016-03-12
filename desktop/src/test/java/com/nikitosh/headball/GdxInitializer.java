package com.nikitosh.headball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nikitosh.headball.desktop.DesktopLauncher;
import com.nikitosh.headball.utils.AssetLoader;

public class GdxInitializer {
    public static class TestApplication extends ApplicationAdapter {
        private static final String DEFAULT_SKIN_PATH = "..\\android\\assets\\ui\\uiskin.json";

        @Override
        public void create() {
            AssetLoader.loadDefaultSkin(DEFAULT_SKIN_PATH);
        }
    }

    static {
        //init GDX environement to have the methods available
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Test";
        config.width = 2;
        config.height = 2;

        new LwjglApplication(new TestApplication(), config);
        ActionResolverSingleton.initialize(new DesktopLauncher());
    }
}
