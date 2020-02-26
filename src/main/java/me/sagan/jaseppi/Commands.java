package me.sagan.jaseppi;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Commands extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();

        if (!msg.getContentRaw().startsWith(Jaseppi.prefix)) return;

        String[] args = msg.getContentRaw().split(" ");
        args[0] = args[0].substring(1);

        String id = event.getAuthor().getId();

        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("commands")) {
            Jaseppi.send(event.getChannel(), Responses.HELP.getRandom());
        } else if (args[0].equalsIgnoreCase("tictactoe") || args[0].equalsIgnoreCase("ttt")) {

            if (args.length != 2) {
                Jaseppi.send(event.getChannel(), Responses.TICTACTOE.getRandom());
                return;
            }

            if (msg.getMentionedUsers().size() == 1) {
                User user = msg.getMentionedUsers().get(0);

                if (event.getGuild().getMember(user).getOnlineStatus() != OnlineStatus.ONLINE) {
                    Jaseppi.send(event.getChannel(), "User not online");
                    return;
                }

                if (Tictactoe.isInGame(user.getId())) {
                    Jaseppi.send(event.getChannel(), "they're in a game");
                    return;
                }

                Tictactoe newGame = new Tictactoe(new Pair(new Tictactoe.Player("x", event.getAuthor().getId()),
                        new Tictactoe.Player("o", user.getId())), event.getChannel());

                event.getChannel().sendMessage(newGame.getEmbed()).queue(message -> newGame.setMessageId(message.getId()));

                return;
            }

            if (args[1].equalsIgnoreCase("exit")) {
                // make sure to return here
                if (!Tictactoe.isInGame(id)) return;

                Tictactoe game = Tictactoe.getGame(id);
                Jaseppi.send(event.getChannel(), "Game's over, <@" + msg.getAuthor().getId() + "> left like a bitch");

                game.end(event.getChannel());

                return;
            }

            int place;

            try {
                place = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return;
            }

            if (!Tictactoe.isInGame(event.getAuthor().getId())) return;

            Tictactoe game = Tictactoe.getGame(event.getAuthor().getId());

            if (!game.isTurn(game.playerFromId(event.getAuthor().getId()))) return;

            if (game.placeTaken(place)) return;
            game.addTurn(place);

            String won = game.findWin();

            if (won != null) {
                Tictactoe.Player winner = won.equalsIgnoreCase("x") ? game.getPlayers().getOne() :
                        game.getPlayers().getTwo();
                Jaseppi.send(event.getChannel(), "Game's over: <@" + winner.getPlayerId() + "> wins");
                game.end(event.getChannel());
                return;
            } else if (game.findTie()) {
                Jaseppi.send(event.getChannel(), "Game's over: tie cuz u both suck");
                game.end(event.getChannel());
                return;
            }

            game.switchTurn();
            game.updateBoard(event.getChannel());
            event.getMessage().delete().queue();
        }
    }
}