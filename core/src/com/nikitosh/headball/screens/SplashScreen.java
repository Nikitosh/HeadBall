package com.nikitosh.headball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nikitosh.headball.utils.ScreenManager;
import com.nikitosh.headball.utils.AssetLoader;
import com.nikitosh.headball.utils.Constants;

public class SplashScreen extends StageAbstractScreen {
    private static final String SPLASH_TEXTURE_PATH = "images/splashScreen.jpg";

    private float splashDuration;

    public SplashScreen() {
        AssetLoader.loadFont();

        Texture splashTexture = new Texture(Gdx.files.internal(SPLASH_TEXTURE_PATH));
        Image background = new Image(splashTexture);
        background.setFillParent(true);

        stack.addActor(background);
    }

    @Override
    public void show() {
        AssetLoader.load(); //load resources for game
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        splashDuration += delta;
        if (splashDuration >= Constants.SPLASH_DURATION) {
            ScreenManager.getInstance().setScreen(new MainMenuScreen());
        }
    }
}
