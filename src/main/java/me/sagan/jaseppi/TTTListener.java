package me.sagan.jaseppi;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class TTTListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {

        // If the user is in a game, and if the message the clicked on is a valid and active game
        if (!Tictactoe.isInGame(event.getUserId())) return;
        if (Tictactoe.getGameFromMessageId(event.getMessageId()) == null) return;

        Tictactoe game = Tictactoe.getGame(event.getUserId());

        // If the user is actually apart of that game
        if (game.getMessageId().equalsIgnoreCase(event.getMessageId())) return;

        if (!game.isTurn(game.playerFromId(event.getUserId()))) return;

        int place = 0;

        switch (event.getReactionEmote().getAsCodepoints()) {
            case "U+2196":
                place = 1;
                break;
            case "U+2B06":
                place = 2;
                break;
            case "U+2197":
                place = 3;
                break;
            case "U+2B05":
                place = 4;
                break;
            case "U+23F9":
                place = 5;
                break;
            case "U+27A1":
                place = 6;
                break;
            case "U+2199":
                place = 7;
                break;
            case "U+2B07":
                place = 8;
                break;
            case "U+2198":
                place = 9;
                break;
        }

        if (game.placeTaken(place)) return;

        game.addTurn(place);
        String won = game.findWin();

        if (won != null) {
            Tictactoe.Player winner = won.equalsIgnoreCase("x") ? game.getPlayers().getOne() :
                    game.getPlayers().getTwo();
            Jaseppi.send(event.getChannel(), "Game's over: <@" + winner.getPlayerId() + "> wins");
            game.end(event.getChannel());
        } else if (game.findTie()) {
            Jaseppi.send(event.getChannel(), "Game's over: tie cuz u both suck");
            game.end(event.getChannel());
        }

        game.switchTurn();
    }
}