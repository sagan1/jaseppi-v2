package me.sagan.jaseppiv2;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
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
            send(event.getChannel(), Responses.HELP.getRandom());
        } else if (args[0].equalsIgnoreCase("tictactoe") || args[0].equalsIgnoreCase("ttt")) {

            if (args.length != 2) {
                send(event.getChannel(), Responses.TICTACTOE.getRandom());
                return;
            }

            if (args[1].equalsIgnoreCase("exit")) {
                // make sure to return here
                if (!Tictactoe.isInGame(id)) return;


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