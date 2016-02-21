package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
    private static final String SETTINGS = "Settings";                              //???
    static private Preferences preferences = Gdx.app.getPreferences(SETTINGS);      //???

    static public void initialize() {
        preferences.putBoolean(Constants.SETTINGS_SOUND, true);
        preferences.putBoolean(Constants.SETTINGS_MUSIC, true);
        preferences.putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_BUTTONS);
        preferences.flush();
    }

    static public void putBoolean(String text, boolean value) {
        preferences.putBoolean(text, value);
        preferences.flush();
    }

    static public void putString(String text, String value) {
        preferences.putString(text, value);
        preferences.flush();
    }

    static public boolean getBoolean(String text) {
        return preferences.getBoolean(text);
    }

    static public String getString(String text) {
        return preferences.getString(text);
    }
}
