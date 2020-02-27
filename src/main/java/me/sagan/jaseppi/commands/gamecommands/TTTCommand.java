package me.sagan.jaseppi.commands.gamecommands;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.commands.Command;
import me.sagan.jaseppi.game.Game;
import me.sagan.jaseppi.game.GameType;
import me.sagan.jaseppi.game.Pair;
import me.sagan.jaseppi.game.Player;
import me.sagan.jaseppi.game.games.TicTacToe;
import me.sagan.jaseppi.game.statistic.StatisticManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class TTTCommand extends Command {

    public TTTCommand() {
        super("tictactoe", 1, 1, "`.ttt <1-9, exit, @player>`", "ttt");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {

        if (!message.getMentionedUsers().isEmpty()) {

            User mentioned = message.getMentionedUsers().get(0);

            if (mentioned.isBot()) {
                Jaseppi.send(channel, "bitch u cant play with me");
                return;
            }

            if (message.getGuild().getMember(mentioned).getOnlineStatus() != OnlineStatus.ONLINE) {
                Jaseppi.send(channel, "User is not online or does not want to play");
                return;
            }

            if (Player.isInGame(mentioned.getId())) {
                Jaseppi.send(channel, "User is a currently in a game");
                return;
            }

            if (Player.isInGame(author.getId())) {
                Jaseppi.send(channel, "bitch, finish the game you're in");
                return;
            }

            Game newGame = TicTacToe.createGame(
                    new Pair(new Player(author.getId(), TicTacToe.x, "x"),
                            new Player(mentioned.getId(), TicTacToe.o, "o")),
                    channel,
                    GameType.TICTACTOE);

            channel.sendMessage(newGame.getEmbed()).queue(m -> newGame.setMessageId(m.getId()));

            return;
        }

        if (args[0].equalsIgnoreCase("exit")) {
            if (!Player.isInGame(author.getId())) return;

            Game game = Game.getGame(author.getId());
            Jaseppi.send(channel, "Game's over, <@" + author.getId() + "> left like a bitch");
            game.end(new StatisticManager.Statistic(GameType.TICTACTOE, author.getId(), 0, 0, 1));
            return;
        }

        int place;

        try {
            place = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return;
        }

        if (!Player.isInGame(author.getId())) return;

        Game game = TicTacToe.getGame(author.getId());

        if (!game.isTurn(author.getId())) return;
        if (game.placeTaken(place)) return;

        game.addTurn(place);

        String won = game.findWin();

        if (won != null) {
            Jaseppi.send(channel, "Game's over: <@" + game.getTurn().getUserId() + "> wins");
            game.end(new StatisticManager.Statistic(GameType.TICTACTOE, game.getTurn().getUserId(), 1, 0, 0),
                    new StatisticManager.Statistic(GameType.TICTACTOE,
                            game.getTurn().getUserId().equalsIgnoreCase(game.getPlayers().getOne().getUserId()) ?
                            game.getPlayers().getTwo().getUserId() : game.getPlayers().getTwo().getUserId(),
                            0, 1, 0));
            return;
        } else if (game.findTie()) {
            Jaseppi.send(channel, "Game's over: tie cuz u both suck");
            game.end();
            return;
        }

        game.switchTurn();
        game.updateEmbed();
        message.delete().queue();
    }
}
