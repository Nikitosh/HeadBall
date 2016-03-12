package com.nikitosh.headball.gamecontrollers;

import com.nikitosh.headball.MatchInfo;
import com.nikitosh.headball.screens.GameScreen;
import com.nikitosh.headball.screens.TournamentScreen;
import com.nikitosh.headball.tournaments.TournamentSerializer;

public class TournamentGameController extends SinglePlayerGameController {
    private TournamentScreen screen;

    public TournamentGameController(GameScreen gameScreen, MatchInfo matchInfo, TournamentScreen screen) {
        super(gameScreen, matchInfo);
        this.screen = screen;
    }

    @Override
    public void exitGame() {
        super.exitGame();
        screen.handleMatchEnd(matchInfo.getFirstTeam(), getScore());
        TournamentSerializer.serialize(screen.getTournament(), screen.getPlayerTeam());
    }
}
