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

    public static void load() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Animated.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);

        atlas = new TextureAtlas("ui/spritesheet.pack");
        skin = new Skin(atlas);

        gameTextButtonStyle = new GameTextButtonStyle();

        goalSound = Gdx.audio.newSound(Gdx.files.internal("sounds/goal.wav"));

        legTexture = new Texture(Gdx.files.internal("images/splashBall.jpg"));

        GameSettings.intialize();
    }

    public static void dispose() {
    }

}
