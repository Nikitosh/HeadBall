package com.nikitosh.headball.tournaments;

import com.nikitosh.headball.utils.Pair;

import java.util.ArrayList;

public abstract class Tournament {
    public abstract void setSelectedTeam(String teamName);
    public abstract void playNextMatch();
    public abstract String getNextOpponent();
    protected abstract void generateTimetable();
}
