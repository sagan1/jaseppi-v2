package me.sagan.jaseppi;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;

public class Jaseppi {

    public static JDA jda;
    public static String prefix = ".";


    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT)
                .setToken(configGet("token"))
                .setActivity(Activity.watching("you"))
                .setDisabledCacheFlags(EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE))
                .setBulkDeleteSplittingEnabled(false);

        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        jda.addEventListener(new Commands());
        jda.addEventListener(new TTTListener());
    }

    public static String configGet(String key) {

        JSONParser parser = new JSONParser();

        try {
            FileReader reader = new FileReader("config.json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            return (String) jsonObject.get(key);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void send(TextChannel channel, String message) {
        channel.sendTyping().queue();
        channel.sendMessage(message).queue();
    }
}