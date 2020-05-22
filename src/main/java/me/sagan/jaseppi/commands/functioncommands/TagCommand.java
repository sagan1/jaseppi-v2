package me.sagan.jaseppi.commands.functioncommands;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author cam (sagan/y0op)
 */
public class TagCommand extends Command {

    public TagCommand() {
        super("tag", 1, 1000, 1, ".tag <user> (suffix)");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {
        if (message.getMentionedUsers().isEmpty()) {
            return;
        }

        User mentioned = message.getMentionedUsers().get(0);

        if (mentioned == null) {
            return;
        }

        if (mentioned.isBot() || mentioned.isFake()) {
            Jaseppi.send(channel, "try that again and ima ddos your grandmas life support");
            return;
        }

        if (mentioned.getId().equalsIgnoreCase("203347457944322048")) {
            Jaseppi.send(channel, "bitch u think i wouldn't code that in?");
            return;
        }

        //String toTag = "<@" + mentioned.getId() + ">";

        Timer t = new Timer();
        t.schedule(new TimerTask() {

            int i = 0;

            @Override
            public void run() {
                i++;
                if (i >= 8) {
                    this.cancel();
                } else {
                    Jaseppi.send(channel, args.length > 1 ? Arrays.toString(args) : args[0]);
                }
            }
        }, 1000, 2000);
    }
}