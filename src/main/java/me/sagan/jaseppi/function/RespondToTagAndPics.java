package me.sagan.jaseppi.function;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.Responses;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * @author cam (sagan/y0op)
 */
public class RespondToTagAndPics extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();

        if (msg.getAuthor().isBot()) return;

        if (msg.getMentionedUsers().isEmpty()) return;

        if (event.getMessage().isMentioned(Jaseppi.jda.getSelfUser())) {
            Jaseppi.send(event.getChannel(), Responses.MENTION_BOT.getRandom());
            return;
        }

        // wont even run if no attachments
        if (msg.getAttachments().stream().anyMatch(attachment -> attachment.isImage() || attachment.isVideo())) {
            if (msg.getTextChannel().getId().equalsIgnoreCase("537425389103087636")) {
                Jaseppi.send(event.getChannel(), Responses.IMAGE_VIDEO_RESPONSE.getRandom());
            }
        }
    }
}