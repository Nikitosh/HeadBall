package com.nikitosh.headball.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nikitosh.headball.ActionResolver;
import com.nikitosh.headball.Server;
import com.nikitosh.headball.utils.Constants;
import com.nikitosh.headball.utils.GameSettings;

public class ServerLauncher implements ActionResolver {
    private static DesktopLauncher launcher = new DesktopLauncher();

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 960;
        config.height = 540;
        new LwjglApplication(new Server(launcher), config);
        GameSettings.putString(Constants.SETTINGS_CONTROL, Constants.SETTINGS_CONTROL_KEYBOARD);
    }

    @Override
    public void signIn() {
        Gdx.app.log("Desktop", "Sign in");
    }

    @Override
    public void signOut() {
        Gdx.app.log("Desktop", "Sign out");
    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void showAchievements() {
        Gdx.app.log("Desktop", "Show achievements");
    }

    @Override
    public void showLeaderboard() {
        Gdx.app.log("Desktop", "Show leaderboard");
    }

    @Override
    public void unlockAchievement(String achievementID) {
        Gdx.app.log("Desktop", "Unlock achievement: " + achievementID);
    }

    @Override
    public void showToast(String text) {
        Gdx.app.log("Desktop", "Toast: " + text);
    }
}
