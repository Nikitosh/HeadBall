package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class GameSettings {
    private static final String SETTINGS = "Settings";
    private static final Preferences preferences = Gdx.app.getPreferences(SETTINGS);

    private GameSettings() {}

    public static void initialize() {
        preferences.putBoolean(Constants.SETTINGS_SOUND, true);
        preferences.putBoolean(Constants.SETTINGS_MUSIC, true);
        preferences.putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_BUTTONS);
        preferences.flush();
    }

    public static void putBoolean(String settingName, boolean value) {
        preferences.putBoolean(settingName, value);
        preferences.flush();
    }

    public static void putString(String settingName, String value) {
        preferences.putString(settingName, value);
        preferences.flush();
    }

    public static boolean getBoolean(String settingName) {
        return preferences.getBoolean(settingName);
    }

    public static String getString(String settingName) {
        return preferences.getString(settingName);
    }
}
