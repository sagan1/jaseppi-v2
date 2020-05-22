package me.sagan.jaseppi.commands.functioncommands;

import com.fasterxml.jackson.databind.JsonNode;
import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.Tokens;
import me.sagan.jaseppi.Util;
import me.sagan.jaseppi.commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class NasaCommand extends Command {

    // comment
    private final String key = Tokens.NASA_TOKEN.getToken();

    public NasaCommand() {
        super("nasa", 0, 1, true, "`.nasa (a search query)`");
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {

        String title;
        String url;
        String explanation;

        if (args.length == 1) {

            String queryActual = args[0].replaceAll(" ", "%20");

            String json = Util.jsonGrab("https://images-api.nasa.gov/search?q=" + queryActual + "&media_type=image");

            JsonNode[] items = Util.jsonGetObjArray("collection.items", json);

            int randomizer = ThreadLocalRandom.current().nextInt(0, 5);

            String firstHref = Util.jsonGet("href", items[randomizer].toString());
            System.out.println(firstHref);
            String data = Util.jsonGetObjArray("data", items[randomizer].toString())[0].toString();
            title = Util.jsonGet("title", data);
            explanation = Util.jsonGet("description", data);

            String picsJson = Util.jsonGrab(firstHref);
            String[] pics = Util.jsonGetArray("null", picsJson);

            url = pics[0];
        } else {
            String json = Util.jsonGrab("https://api.nasa.gov/planetary/apod?api_key=" + key);

            title = Util.jsonGet("title", json);
            url = Util.jsonGet("url", json);
            explanation = Util.jsonGet("explanation", json);
        }

        explanation = explanation.length() > 360 ? explanation.substring(0, 357).concat("...") : explanation;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.MAGENTA);
        eb.setAuthor(title);
        eb.addField("Description", explanation, false);

        try {
            eb.setImage(url);
        } catch (IllegalArgumentException e) {
            Jaseppi.send(channel, "Nah some shit aint workin");
            return;
        }

        channel.sendMessage(eb.build()).queue();
    }
}