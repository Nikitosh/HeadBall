package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class GameSettings {
    private static final String SETTINGS = "Settings";
    private static final Preferences PREFERENCES = Gdx.app.getPreferences(SETTINGS);

    private GameSettings() {}

    public static void initialize() {
        PREFERENCES.putBoolean(Constants.SETTINGS_SOUND, true);
        PREFERENCES.putBoolean(Constants.SETTINGS_MUSIC, true);
        PREFERENCES.putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_BUTTONS);
        PREFERENCES.flush();
    }

    public static void putBoolean(String settingName, boolean value) {
        PREFERENCES.putBoolean(settingName, value);
        PREFERENCES.flush();
    }

    public static void putString(String settingName, String value) {
        PREFERENCES.putString(settingName, value);
        PREFERENCES.flush();
    }

    public static boolean getBoolean(String settingName) {
        return PREFERENCES.getBoolean(settingName);
    }

    public static String getString(String settingName) {
        return PREFERENCES.getString(settingName);
    }
}
