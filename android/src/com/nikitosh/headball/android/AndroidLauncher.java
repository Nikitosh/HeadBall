package com.nikitosh.headball.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.nikitosh.headball.ActionResolver;
import com.nikitosh.headball.HeadballGame;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {
	private static final int ACHIEVEMENTS_REQUEST_CODE = 101;
    private static final int LEADERBOARD_REQUEST_CODE = 100;

    private GameHelper gameHelper;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new HeadballGame(this), config);

        if (gameHelper == null) {
            gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
            gameHelper.enableDebugLog(true);
        }
        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
                Gdx.app.log("AndroidLauncher", "Sign in failed");
			}

			@Override
			public void onSignInSucceeded() {
                Gdx.app.log("AndroidLauncher", "Sign in succeeded");
            }
        };
        gameHelper.setup(gameHelperListener);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

	@Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                public final void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("AndroidLauncher", "Log in failed: " + e.getMessage());
        }
    }

    @Override
    public void signOut()
    {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("AndroidLauncher", "Log out failed: " + e.getMessage());
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
    }

    @Override
    public void showAchievements() {
        if (!isSignedIn()) {
            showToast("You should sign in Google Play to see your achievements");
            return;
        }
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()),
                            ACHIEVEMENTS_REQUEST_CODE);
                }
            });
        } catch (Exception e) {
            Gdx.app.log("AndroidLauncher", "Achievements showing failed: " + e.getMessage());
        }
    }

    @Override
    public void showLeaderboard() {
        if (!isSignedIn()) {
            showToast("You should sign in Google Play to see leaderboard");
            return;
        }
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()),
                            LEADERBOARD_REQUEST_CODE);
                }
            });
        } catch (Exception e) {
            Gdx.app.log("AndroidLauncher", "Leaderboard showing failed: " + e.getMessage());
        }
    }

    @Override
    public void unlockAchievement(String achievementID) {
        if (!isSignedIn()) {
            return;
        }
        Games.Achievements.unlock(gameHelper.getApiClient(), achievementID);
    }

    @Override
    public void showToast(final String text) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Making toast failed: " + e.getMessage());
        }
    }
}
