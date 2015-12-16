package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
    static private Preferences preferences = Gdx.app.getPreferences("Settings");

    static public void intialize() {
        preferences.putBoolean("sound", true);
        preferences.putBoolean("music", true);
    }


    static void putBoolean(String text, boolean value) {
        preferences.putBoolean(text, value);
    }

    static boolean getBoolean(String text) {
        return preferences.getBoolean(text);
    }
}
