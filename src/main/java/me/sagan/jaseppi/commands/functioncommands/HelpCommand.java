package me.sagan.jaseppi.commands.functioncommands;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.Responses;
import me.sagan.jaseppi.commands.Command;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * @author cam (sagan/y0op)
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", 0, 0, ".help");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {
        Jaseppi.send(channel, Responses.HELP.getRandom());
    }
}