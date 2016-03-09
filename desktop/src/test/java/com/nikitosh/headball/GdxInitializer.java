package com.nikitosh.headball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.GdxNativesLoader;

public class GdxInitializer {
    private static class TestApplication extends ApplicationAdapter {}

    static {
        //init GDX environement to have the methods available
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Test";
        config.width = 2;
        config.height = 2;

        new LwjglApplication(new TestApplication(), config);
    }
}
