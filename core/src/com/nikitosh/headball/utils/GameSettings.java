package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class GameSettings {
    private static final String SETTINGS = "Settings";
    private static Preferences preferences = Gdx.app.getPreferences(SETTINGS);

    private GameSettings() {}

    public static void initialize() {
        preferences.putBoolean(Constants.SETTINGS_SOUND, true);
        preferences.putBoolean(Constants.SETTINGS_MUSIC, true);
        preferences.putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_BUTTONS);
        preferences.flush();
    }

    public static void putBoolean(String text, boolean value) {
        preferences.putBoolean(text, value);
        preferences.flush();
    }

    public static void putString(String text, String value) {
        preferences.putString(text, value);
        preferences.flush();
    }

    public static boolean getBoolean(String text) {
        return preferences.getBoolean(text);
    }

    public static String getString(String text) {
        return preferences.getString(text);
    }
}
