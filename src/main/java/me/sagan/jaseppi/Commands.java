package me.sagan.jaseppi;

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

            if (args.length != 2 || msg.getMentionedUsers().size() != 1) {
                Jaseppi.send(event.getChannel(), Responses.TICTACTOE.getRandom());
                return;
            }

            User user = msg.getMentionedUsers().get(0);

            if (user != null) {

                if (Tictactoe.isInGame(user.getId())) {
                    Jaseppi.send(event.getChannel(), "they're in a game");
                    return;
                }

                Tictactoe newGame = new Tictactoe(new Pair(new Tictactoe.Player("x", event.getAuthor().getId()),
                        new Tictactoe.Player("o", user.getId())), event.getChannel());

                event.getChannel().sendMessage(newGame.getEmbed()).queue(message -> {
                    message.addReaction("U+2196").queue();
                    message.addReaction("U+2B06").queue();
                    message.addReaction("U+2197").queue();
                    message.addReaction("U+2B05").queue();
                    message.addReaction("U+23F9").queue();
                    message.addReaction("U+27A1").queue();
                    message.addReaction("U+2199").queue();
                    message.addReaction("U+2B07").queue();
                    message.addReaction("U+2198").queue();
                    newGame.setMessageId(message.getId());
                });

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

            // Check if is user, and then if either user is in match, if not; start game.
        }
    }
}