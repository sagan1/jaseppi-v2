package me.sagan.jaseppi.commands.functioncommands;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author cam (sagan/y0op)
 */
public class SayCommand extends Command {

    public SayCommand() {
        super("say", 1, 10000, 0, ".say <message>");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {
        message.delete().queue();

        Jaseppi.send(channel, args[0]);
    }
}