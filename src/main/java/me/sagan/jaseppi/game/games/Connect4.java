package me.sagan.jaseppi.game.games;

import me.sagan.jaseppi.game.Game;
import me.sagan.jaseppi.game.GameType;
import me.sagan.jaseppi.game.Pair;
import net.dv8tion.jda.api.entities.TextChannel;

public class Connect4 extends Game {

    public Connect4(Pair players, TextChannel channel, GameType type) {
        super(players, channel, type);
    }

    @Override
    public void initializeEmbed() {

    }

    @Override
    public void updateEmbed() {

    }

    @Override
    public void addTurn(int place) {

    }

    @Override
    public String findWin() {
        return null;
    }

    @Override
    public boolean findTie() {
        return false;
    }
}
