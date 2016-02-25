package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {

    public static BitmapFont font;
    public static Skin gameSkin;
    public static Skin defaultSkin;
    public static Skin tournamentsSkin;
    public static Skin teamsSkin;

    public static Texture legTexture;
    public static Texture ballTexture;
    public static Texture footballerTexture;
    public static Texture reversedFootballerTexture;
    public static Texture goalsTexture;
    public static Texture reversedGoalsTexture;
    public static Texture menuTexture;
    public static Texture fieldTexture;
    public static Texture backgroundTexture;
    public static Texture darkBackgroundTexture;

    public static Sound goalSound;

    public static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LuckiestGuy.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 22;//???
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

        //GameSettings.initialize();//??
    }

    public static void dispose() {
    }

}
