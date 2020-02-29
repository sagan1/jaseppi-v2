package me.sagan.jaseppi;

import me.sagan.jaseppi.commands.CommandHandler;
import me.sagan.jaseppi.commands.CommandRegistry;
import me.sagan.jaseppi.commands.functioncommands.NasaCommand;
import me.sagan.jaseppi.commands.functioncommands.WeatherCommand;
import me.sagan.jaseppi.commands.gamecommands.C4Command;
import me.sagan.jaseppi.commands.gamecommands.TTTCommand;
import me.sagan.jaseppi.game.statistic.StatisticManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.EnumSet;

public class Jaseppi {

    public static JDA jda;
    public static String prefix = ".";
    public static final File config = new File("config.json");

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT)
                .setToken(Util.jsonGet("token", config))
                .setActivity(Activity.watching("you"))
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE))
                .setBulkDeleteSplittingEnabled(false);

        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.addEventListener(new CommandHandler());

        CommandRegistry.register(new TTTCommand());
        CommandRegistry.register(new C4Command());
        CommandRegistry.register(new WeatherCommand());
        CommandRegistry.register(new NasaCommand());

        StatisticManager.initialize();
    }

    public static void send(TextChannel channel, String message) {
        channel.sendTyping().queue();
        channel.sendMessage(message).queue();
    }
}