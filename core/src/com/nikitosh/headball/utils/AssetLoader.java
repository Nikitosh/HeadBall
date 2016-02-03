package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.nikitosh.headball.ui.GameLabelStyle;
import com.nikitosh.headball.ui.GameTextButtonStyle;
import com.nikitosh.headball.ui.GameTextButtonTouchableStyle;
import com.nikitosh.headball.ui.GameWindowStyle;

public class AssetLoader {

    public static BitmapFont font;
    public static Skin skin;
    public static Skin defaultSkin;
    public static Skin tournamentsSkin;
    public static Skin teamsSkin;

    public static GameTextButtonStyle gameTextButtonStyle;
    public static GameTextButtonTouchableStyle gameTextButtonTouchableStyle;
    public static GameLabelStyle gameLabelStyle;
    public static GameWindowStyle gameWindowStyle;

    public static Texture legTexture;
    public static Texture ballTexture;
    public static Texture footballerTexture;
    public static Texture reversedFootballerTexture;
    public static Texture windowBackground;
    public static Texture goalsTexture;
    public static Texture reversedGoalsTexture;
    public static Texture menuTexture;
    public static Texture fieldTexture;
    public static Texture backgroundTexture;
    public static Texture darkBackgroundTexture;

    public static Drawable touchpadBackgroundDrawable;
    public static Drawable touchpadKnobDrawable;

    public static Sound goalSound;

    public static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LuckiestGuy.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);

    }

    public static void load() {
        TextureAtlas atlas = new TextureAtlas("ui/spritesheet.pack");
        skin = new Skin(atlas);

        TextureAtlas tournamentsAtlas = new TextureAtlas("tournaments/tournamentPack.pack");
        tournamentsSkin = new Skin(tournamentsAtlas);

        TextureAtlas teamsAtlas = new TextureAtlas("teams/teamPack.pack");
        teamsSkin = new Skin(teamsAtlas);

        defaultSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        gameTextButtonStyle = new GameTextButtonStyle();
        gameTextButtonTouchableStyle = new GameTextButtonTouchableStyle();
        gameLabelStyle = new GameLabelStyle();

        goalSound = Gdx.audio.newSound(Gdx.files.internal("sounds/goal.wav"));

        legTexture = new Texture(Gdx.files.internal("images/splashScreen.jpg"));
        ballTexture = new Texture(Gdx.files.internal("images/ball.png"));
        windowBackground = new Texture(Gdx.files.internal("images/pauseBackground.jpg"));
        gameWindowStyle = new GameWindowStyle();
        footballerTexture = new Texture(Gdx.files.internal("images/footballerHead.png"));
        reversedFootballerTexture = new Texture(Gdx.files.internal("images/reversedFootballerHead.png"));
        goalsTexture = new Texture(Gdx.files.internal("images/goals.png"));
        reversedGoalsTexture = new Texture(Gdx.files.internal("images/reversedGoals.png"));
        menuTexture = new Texture(Gdx.files.internal("images/menu.jpg"));
        fieldTexture = new Texture(Gdx.files.internal("images/fieldBackground.jpg"));
        backgroundTexture = new Texture(Gdx.files.internal("images/background.jpg"));
        darkBackgroundTexture = new Texture(Gdx.files.internal("images/darkBackground.png"));
        touchpadBackgroundDrawable = new Image(new Texture(Gdx.files.internal("images/touchpadBackground.png"))).getDrawable();
        touchpadKnobDrawable = new Image(new Texture(Gdx.files.internal("images/touchpadKnob.png"))).getDrawable();

        GameSettings.initialize();
    }

    public static void dispose() {
    }

}
