package me.sagan.jaseppi.functionality;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * @author cam (sagan/y0op)
 */
public class DeletePlayCommands extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();

        if (msg.getAuthor().isBot()) return;

        if (msg.getContentDisplay().startsWith("-play") ||
                msg.getContentDisplay().startsWith("-leave") ||
                msg.getContentDisplay().startsWith("-skip") ||
                msg.getContentDisplay().startsWith("-join")) {
            msg.delete().queue();
        }
    }
}