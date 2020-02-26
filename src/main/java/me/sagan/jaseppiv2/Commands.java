package me.sagan.jaseppiv2;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

public class Commands extends ListenerAdapter {

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

        //TODO check if place is taken and if not update board and: check for win, check for tie, switch turn etc
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();

        if (!msg.getContentRaw().startsWith(Jaseppi.prefix)) return;

        String[] args = msg.getContentRaw().split(" ");
        args[0] = args[0].substring(1);

        String id = event.getAuthor().getId();

        if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("commands")) {
            send(event.getChannel(), Responses.HELP.getRandom());
        } else if (args[0].equalsIgnoreCase("tictactoe") || args[0].equalsIgnoreCase("ttt")) {

            if (args.length != 2 || msg.getMentionedUsers().size() != 1) {
                send(event.getChannel(), Responses.TICTACTOE.getRandom());
                return;
            }

            User user = msg.getMentionedUsers().get(0);

            if (user != null) {
                Tictactoe newGame = new Tictactoe(new Pair(new Tictactoe.Player("U+274C", event.getAuthor().getId()),
                        new Tictactoe.Player("U+2B55", user.getId())));

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
                send(event.getChannel(), "");

                Tictactoe.games.remove(game);

                send(event.getChannel(), "Game's over, <@" + msg.getAuthor().getId() + "> left like a bitch");

                return;
            }

            switch (args[1]) {
                case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9":
                    int place = Integer.parseInt(args[1]);

                    if (Tictactoe.isInGame(event.getAuthor().getId())) {

                    }

                    return;
            }

            // Check if is user, and then if either user is in match, if not; start game.
        }
    }

    public static void send(TextChannel channel, String message) {
        channel.sendTyping().queue();
        channel.sendMessage(message).queue();
    }
}