package me.sagan.jaseppi.commands.functioncommands;

import me.sagan.jaseppi.Util;
import me.sagan.jaseppi.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.awt.*;
import java.io.IOException;

public class WeatherCommand extends Command {

    private OkHttpClient client = new OkHttpClient();
    private final String appid = "deb50cd547a7f29d5f5a32979b03ae5f";

    public WeatherCommand() {
        super("weather", 0, 1, "`.weather (city=Kelowna,CA)`", "w");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {

        String city = args.length == 1 ? args[0] : "Kelowna,CA";

        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + appid + "&units=metric")
                .build();

        String json;

        try {
            json = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

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