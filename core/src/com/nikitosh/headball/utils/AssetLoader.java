package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class AssetLoader {
    private static final int FONT_SIZE = 22;

    private static BitmapFont font;
    private static Skin gameSkin;
    private static Skin defaultSkin;
    private static Skin tournamentsSkin;
    private static Skin teamsSkin;

    private static Texture legTexture;
    private static Texture ballTexture;
    private static Texture footballerTexture;
    private static Texture reversedFootballerTexture;
    private static Texture goalsTexture;
    private static Texture reversedGoalsTexture;
    private static Texture menuTexture;
    private static Texture fieldTexture;
    private static Texture backgroundTexture;
    private static Texture darkBackgroundTexture;

    private static Sound goalSound;

    private AssetLoader() {}

    public static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LuckiestGuy.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = FONT_SIZE;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public static void load() {
        TextureAtlas gameSkinAtlas = new TextureAtlas("ui/gameSkin.atlas");
        gameSkin = new Skin();
        gameSkin.addRegions(gameSkinAtlas);
        gameSkin.add("default-font", font);
        gameSkin.load(Gdx.files.internal("ui/gameSkin.json"));

        defaultSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        TextureAtlas tournamentsAtlas = new TextureAtlas("tournaments/tournamentPack.pack");
        tournamentsSkin = new Skin(tournamentsAtlas);

        TextureAtlas teamsAtlas = new TextureAtlas("teams/teamPack.pack");
        teamsSkin = new Skin(teamsAtlas);

        goalSound = Gdx.audio.newSound(Gdx.files.internal("sounds/goal.wav"));

        legTexture = new Texture(Gdx.files.internal("images/splashScreen.jpg"));
        ballTexture = new Texture(Gdx.files.internal("images/ball.png"));
        footballerTexture = new Texture(Gdx.files.internal("images/footballerHead.png"));
        reversedFootballerTexture = new Texture(Gdx.files.internal("images/reversedFootballerHead.png"));
        goalsTexture = new Texture(Gdx.files.internal("images/goals.png"));
        reversedGoalsTexture = new Texture(Gdx.files.internal("images/reversedGoals.png"));
        menuTexture = new Texture(Gdx.files.internal("images/menu.jpg"));
        fieldTexture = new Texture(Gdx.files.internal("images/fieldBackground.jpg"));
        backgroundTexture = new Texture(Gdx.files.internal("images/background.jpg"));
        darkBackgroundTexture = new Texture(Gdx.files.internal("images/darkBackground.png"));
    }


    public static void dispose() {
    }

    public static BitmapFont getFont() {
        return font;
    }

    public static Skin getGameSkin() {
        return gameSkin;
    }

    public static Skin getDefaultSkin() {
        return defaultSkin;
    }

    public static Skin getTournamentsSkin() {
        return tournamentsSkin;
    }

    public static Skin getTeamsSkin() {
        return teamsSkin;
    }

    public static Texture getLegTexture() {
        return legTexture;
    }

    public static Texture getBallTexture() {
        return ballTexture;
    }

    public static Texture getFootballerTexture() {
        return footballerTexture;
    }

    public static Texture getReversedFootballerTexture() {
        return reversedFootballerTexture;
    }

    public static Texture getGoalsTexture() {
        return goalsTexture;
    }

    public static Texture getReversedGoalsTexture() {
        return reversedGoalsTexture;
    }

    public static Texture getMenuTexture() {
        return menuTexture;
    }

    public static Texture getFieldTexture() {
        return fieldTexture;
    }

    public static Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public static Texture getDarkBackgroundTexture() {
        return darkBackgroundTexture;
    }

    public static Sound getGoalSound() {
        return goalSound;
    }
}
