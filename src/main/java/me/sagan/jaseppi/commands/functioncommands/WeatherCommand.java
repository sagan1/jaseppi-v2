package me.sagan.jaseppi.commands.functioncommands;

import me.sagan.jaseppi.Tokens;
import me.sagan.jaseppi.Util;
import me.sagan.jaseppi.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class WeatherCommand extends Command {

    private final String appid = Tokens.WEATHER_TOKEN.getToken();

    public WeatherCommand() {
        super("weather", 0, 1, true, "`.weather (city=Kelowna,CA)`", "w");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {

        String city = args.length == 1 ? args[0].replaceAll("\\s+", "") : "Kelowna,CA";

        String json = Util.jsonGrab("http://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + appid + "&units=metric");

        String temp = Util.jsonGet("main.temp", json);
        String feelsLike = Util.jsonGet("main.feels_like", json);
        String humidity = Util.jsonGet("main.humidity", json);

        channel.sendMessage(new EmbedBuilder().setTitle(city).setColor(Color.CYAN)
                .addField("Temp", temp + "°C", true)
                .addField("Feels Like", feelsLike + "°C", true)
                .addField("Humidity", humidity + "%", true)
                .build()).queue();
    }
}