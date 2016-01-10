package com.nikitosh.headball.utils;

public class Constants {
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;
    public static final int VIRTUAL_WIDTH = 800;
    public static final int VIRTUAL_HEIGHT = 480;
    public static final int UI_LAYER_HEIGHT = 100;
    public static final int FIELD_WIDTH = VIRTUAL_WIDTH;
    public static final int FIELD_HEIGHT = VIRTUAL_HEIGHT - UI_LAYER_HEIGHT;
    public static final int UI_ELEMENTS_INDENT = 10;
    public static final float GOALS_HEIGHT = 90;
    public static final float GOALS_WIDTH = 50;
    public static final float CROSSBAR_HEIGHT = 10;

    public static final int GAME_DURATION = 25;
    public static final float SPLASH_DURATION = 1;
    public static final int PLAYERS_NUMBER = 2;

    public static final short ROTATOR_CATEGORY = 1;
    public static final short LEG_CATEGORY = 1 << 1;
    public static final short GROUND_WALL_CATEGORY = 1 << 2;
    public static final short FOOTBALLER_CATEGORY = 1 << 3;
    public static final short GAME_OBJECT_CATEGORY = 1 << 4;
    public static final short ROTATOR_MASK = ~FOOTBALLER_CATEGORY;
    public static final short LEG_MASK = ~GROUND_WALL_CATEGORY;
    public static final short GROUND_WALL_MASK = ~LEG_CATEGORY;
    public static final short FOOTBALLER_MASK = ~ROTATOR_CATEGORY;
    public static final short GAME_OBJECT_MASK = -1;

    public static final String SETTINGS_SOUND = "Sound";
    public static final String SETTINGS_MUSIC = "Music";
    public static final String SETTINGS_CONTROL = "Control";
    public static final String SETTINGS_CONTROL_BUTTONS = "Buttons";
    public static final String SETTINGS_CONTROL_TOUCHPAD = "Touchpad";
    public static final String SETTINGS_CONTROL_KEYBOARD = "Keyboard";

    public static final int HIT = 0;
    public static final int JUMP = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
}
