package me.sagan.jaseppi.function;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.Responses;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author cam (sagan/y0op)
 */
public class Respond extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message msg = event.getMessage();

        if (msg.getAuthor().isBot()) return;

        if (!msg.getMentionedUsers().isEmpty()) {
            if (event.getMessage().isMentioned(Jaseppi.jda.getSelfUser())) {
                Jaseppi.send(event.getChannel(), Responses.MENTION_BOT.getRandom());
                return;
            }
        }

        // wont even run if no attachments
        if (msg.getAttachments().stream().anyMatch(attachment -> attachment.isImage() || attachment.isVideo())) {
            if (msg.getTextChannel().getId().equalsIgnoreCase("537425389103087636") && ThreadLocalRandom.current().nextDouble() > 0.6) {
                Jaseppi.send(event.getChannel(), Responses.IMAGE_VIDEO_RESPONSE.getRandom());
                return;
            }
        }

        if (ThreadLocalRandom.current().nextDouble() > 0.9 && !msg.getContentDisplay().startsWith(Jaseppi.prefix)) {
            StringBuilder newString = new StringBuilder();
            boolean upperCase = false;
            for (char c : event.getMessage().getContentDisplay().toLowerCase().toCharArray()) {
                if (Character.isSpaceChar(c)) {
                    newString.append(c);
                    continue;
                }

                if (upperCase) {
                    newString.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    newString.append(c);
                    upperCase = true;
                }
            }

            Jaseppi.send(event.getChannel(), newString.toString());
        }
    }
}