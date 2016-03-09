package com.nikitosh.headball.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

public final class AssetLoader {
    private static final int FONT_SIZE = 22;

    private static BitmapFont font;
    private static Skin gameSkin;
    private static Skin defaultSkin;
    private static Skin tournamentsSkin;
    private static Skin teamsSkin;

    private static Texture menuTexture;
    private static Texture fieldTexture;
    private static Texture backgroundTexture;
    private static Texture darkBackgroundTexture;

    private static Box2DSprite ballSprite;
    private static Box2DSprite[] footballerSprites;
    private static Box2DSprite[] legSprites;
    private static Box2DSprite[] goalsSprites;

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

        Texture legTexture = new Texture(Gdx.files.internal("images/leg.png"));
        Texture reversedLegTexture = new Texture(Gdx.files.internal("images/reversedLeg.png"));
        Texture ballTexture = new Texture(Gdx.files.internal("images/ball.png"));
        Texture footballerTexture = new Texture(Gdx.files.internal("images/footballerHead.png"));
        Texture reversedFootballerTexture = new Texture(Gdx.files.internal("images/reversedFootballerHead.png"));
        Texture goalsTexture = new Texture(Gdx.files.internal("images/goals.png"));
        Texture reversedGoalsTexture = new Texture(Gdx.files.internal("images/reversedGoals.png"));

        menuTexture = new Texture(Gdx.files.internal("images/menu2.jpg"));
        fieldTexture = new Texture(Gdx.files.internal("images/fieldBackground.jpg"));
        backgroundTexture = new Texture(Gdx.files.internal("images/background.jpg"));
        darkBackgroundTexture = new Texture(Gdx.files.internal("images/darkBackground.png"));

        ballSprite = new Box2DSprite(ballTexture);
        footballerSprites = new Box2DSprite[Constants.PLAYERS_NUMBER];
        footballerSprites[0] = new Box2DSprite(footballerTexture);
        footballerSprites[1] = new Box2DSprite(reversedFootballerTexture);
        legSprites = new Box2DSprite[Constants.PLAYERS_NUMBER];
        legSprites[0] = new Box2DSprite(legTexture);
        legSprites[1] = new Box2DSprite(reversedLegTexture);
        goalsSprites = new Box2DSprite[Constants.PLAYERS_NUMBER];
        goalsSprites[0] = new Box2DSprite(goalsTexture);
        goalsSprites[1] = new Box2DSprite(reversedGoalsTexture);

        GameSettings.initialize();
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

    public static Box2DSprite getBallSprite() {
        return ballSprite;
    }

    public static Box2DSprite getFootballerSprite(int i) {
        return footballerSprites[i];
    }

    public static Box2DSprite getLegSprite(int i) {
        return legSprites[i];
    }

    public static Box2DSprite getGoalsSprite(int i) {
        return goalsSprites[i];
    }
}
