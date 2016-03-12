package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public final class GameSettings {
    private static final String SETTINGS = "Settings";
    private static final Preferences PREFERENCES = Gdx.app.getPreferences(SETTINGS);

    private GameSettings() {}

    public static void initialize() {
        if (PREFERENCES.getString(Constants.SETTINGS_CONTROL).equals("")) {
            putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_BUTTONS);
        }
        if (PREFERENCES.getInteger(Constants.GAME_DURATION) == 0) {
            putInteger(Constants.GAME_DURATION, Constants.GAME_DURATION_OPTIONS[0]);
        }
        if (PREFERENCES.getString(Constants.AI_LEVEL).equals("")) {
            putString(Constants.AI_LEVEL, Constants.AI_LEVEL_MEDIUM);
        }
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

    public static void putInteger(String settingName, int value) {
        PREFERENCES.putInteger(settingName, value);
        PREFERENCES.flush();
    }

    public static int getInteger(String settingName) {
        return PREFERENCES.getInteger(settingName);
    }

    public static boolean getBoolean(String settingName) {
        return PREFERENCES.getBoolean(settingName);
    }

    public static String getString(String settingName) {
        return PREFERENCES.getString(settingName);
    }
}
