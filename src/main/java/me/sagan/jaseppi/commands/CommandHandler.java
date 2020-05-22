package me.sagan.jaseppi.commands;

import me.sagan.jaseppi.Jaseppi;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CommandHandler extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();

        if (msg.getAuthor().isBot()) return;

        if (msg.getContentRaw().length() < 2) {
            return;
        }

        if (!String.valueOf(msg.getContentRaw().charAt(0)).equalsIgnoreCase(Jaseppi.prefix)) return;

        String[] argsRaw = msg.getContentRaw().substring(Jaseppi.prefix.length()).split(" ");
        String cmd = argsRaw[0];
        String[] argsActual = Arrays.copyOfRange(argsRaw, 1, argsRaw.length);

        Command.delegate(event.getMessage(), event.getMember(), event.getChannel(), cmd, argsActual);
    }
}