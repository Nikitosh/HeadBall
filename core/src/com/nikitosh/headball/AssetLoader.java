package com.nikitosh.headball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
    public static BitmapFont font;
    public static TextureAtlas atlas;
    public static Skin skin;
    public static GameTextButtonStyle gameTextButtonStyle;
    public static Sound goalSound;
    public static Texture legTexture;
    public static Texture ballTexture;
    public static Texture footballerTexture;

    public static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Animated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);

    }

    public static void load() {
        atlas = new TextureAtlas("ui/spritesheet.pack");
        skin = new Skin(atlas);

        gameTextButtonStyle = new GameTextButtonStyle();

        goalSound = Gdx.audio.newSound(Gdx.files.internal("sounds/goal.wav"));

        legTexture = new Texture(Gdx.files.internal("images/splashScreen.jpg"));
        ballTexture = new Texture(Gdx.files.internal("images/ball.png"));
        footballerTexture = new Texture(Gdx.files.internal("images/ball.png"));

        GameSettings.intialize();
    }

    public static void dispose() {
    }

}
