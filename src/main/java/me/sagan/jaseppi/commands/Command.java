package me.sagan.jaseppi.commands;

import me.sagan.jaseppi.Jaseppi;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;
import java.util.Optional;

public abstract class Command {

    private String cmd;
    private String usage;
    private String[] aliases;
    private int minArgs;
    private int maxArgs;
    private Optional<Integer> concatArgsAfterArg;

    public Command(String cmd, int minArgs, int maxArgs, String usage, String... aliases) {
        this.cmd = cmd;
        this.usage = usage;
        this.aliases = aliases;
        this.maxArgs = maxArgs;
        this.minArgs = minArgs;
        this.concatArgsAfterArg = Optional.empty();
    }

    public Command(String cmd, int minArgs, int maxArgs, int concatArgsAfterArg, String usage, String... aliases) {
        this.cmd = cmd;
        this.usage = usage;
        this.aliases = aliases;
        this.maxArgs = maxArgs;
        this.minArgs = minArgs;
        this.concatArgsAfterArg = Optional.of(concatArgsAfterArg);
        if (concatArgsAfterArg >= maxArgs) {
            this.concatArgsAfterArg = Optional.empty();
        }
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

                if (command.concatArgsAfterArg.isPresent()) {
                    //.tag quiver you're an idiot

                    // for some reason a space was being passed in when there were no args so I added this
                    if (args.length == 0) {
                        command.handle(message, author, channel, new String[]{});
                        return;
                    }

                    // get all the args before the concatenation as an array
                    String[] initialArgs = Arrays.copyOfRange(args, 0, command.concatArgsAfterArg.get());
                    // concatenate all the args after the concatenation into a single string
                    String concatArgs = String.join(" ", Arrays.copyOfRange(args, command.concatArgsAfterArg.get(), args.length));

                    // combine all prefixing args and the concatenated arg into one array
                    String[] actualArgs = new String[initialArgs.length + 1];
                    System.arraycopy(initialArgs, 0, actualArgs, 0, initialArgs.length);
                    actualArgs[actualArgs.length - 1] = concatArgs;

                    command.handle(message, author, channel, actualArgs);
                    return;
                } else if (args.length < command.minArgs || args.length > command.maxArgs) {
                    Jaseppi.send(channel, "Retard. Try this: `" + command.usage + "`");
                    return;
                }

                command.handle(message, author, channel, args);
            }
        });
    }
}