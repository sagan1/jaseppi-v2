package me.sagan.jaseppi.commands;

import me.sagan.jaseppi.Jaseppi;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public abstract class Command {

    private String cmd;
    private String usage;
    private String[] aliases;
    private int minArgs;
    private int maxArgs;

    protected Command(String cmd, int minArgs, int maxArgs, String usage, String... aliases) {
        this.cmd = cmd;
        this.usage = usage;
        this.aliases = aliases;
        this.maxArgs = maxArgs;
        this.minArgs = minArgs;
    }

    public abstract void handle(Message message, Member author, TextChannel channel, String[] args);

    public String getCmd() {
        return cmd;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }

    public boolean hasAlias(String a) {
        for (String alias : this.aliases) {
            if (alias.equalsIgnoreCase(a)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAlias(String a) {
        for (String alias : this.aliases) {
            if (alias.equalsIgnoreCase(a)) {
                return true;
            }
        }

        return false;
    }

    public static void delegate(Message message, Member author, TextChannel channel, String cmd, String[] args) {

        CommandRegistry.commands.forEach(command -> {
            if (command.getCmd().equalsIgnoreCase(cmd) || command.hasAlias(cmd)) {

                if (args.length < command.minArgs || args.length > command.maxArgs) {
                    Jaseppi.send(channel, command.usage);
                    return;
                }

                command.handle(message, author, channel, args);
            }
        });
    }
}