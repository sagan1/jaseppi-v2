package me.sagan.jaseppi;

import me.sagan.jaseppi.commands.CommandHandler;
import me.sagan.jaseppi.commands.CommandRegistry;
import me.sagan.jaseppi.commands.functioncommands.HelpCommand;
import me.sagan.jaseppi.commands.functioncommands.NasaCommand;
import me.sagan.jaseppi.commands.functioncommands.RedditCommand;
import me.sagan.jaseppi.commands.functioncommands.TagCommand;
import me.sagan.jaseppi.commands.functioncommands.WeatherCommand;
import me.sagan.jaseppi.commands.gamecommands.C4Command;
import me.sagan.jaseppi.commands.gamecommands.TTTCommand;
import me.sagan.jaseppi.function.RespondToTagAndPics;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class Jaseppi {

    public static JDA jda;
    public static String prefix = ".";

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT)
                .setToken(Tokens.DISCORD_MAIN_TOKEN.getToken())
                .setActivity(Activity.watching("you"))
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE))
                .setBulkDeleteSplittingEnabled(false);

        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.addEventListener(new CommandHandler());
        jda.addEventListener(new RespondToTagAndPics());

        CommandRegistry.register(new TTTCommand());
        CommandRegistry.register(new C4Command());
        CommandRegistry.register(new WeatherCommand());
        CommandRegistry.register(new NasaCommand());
        CommandRegistry.register(new TagCommand());
        CommandRegistry.register(new HelpCommand());
        CommandRegistry.register(new RedditCommand());
    }

    public static void send(TextChannel channel, String message) {
        channel.sendTyping().queue();
        channel.sendMessage(message).queue();
    }
}