package com.nikitosh.headball;

public interface ActionResolver {
    void signIn();
    void signOut();
    boolean isSignedIn();
    void showAchievements();
    void showLeaderboard();
    void unlockAchievement(String achievementID);
    void showToast(String text);
}
