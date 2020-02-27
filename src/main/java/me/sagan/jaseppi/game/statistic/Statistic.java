package me.sagan.jaseppi.game.statistic;

import me.sagan.jaseppi.Jaseppi;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistic {

    public static TextChannel channel;
    public static Message message;
    public static MessageEmbed embed;

    public static void initialize() {
        channel = Jaseppi.jda.getTextChannelById("682451917376323607");
        if (channel != null) {
            if (channel.hasLatestMessage()) {
                message = channel.retrieveMessageById(channel.getLatestMessageId()).complete();
                if (message.getEmbeds().size() != 1) {
                    message.delete().queue();
                    return;
                }

                embed = message.getEmbeds().get(0);

                /*

                TicTacToe:
                @sagan: 3-13-5-1
                @gator: 3-45-1-5

                 */

                for (MessageEmbed.Field field : embed.getFields()) {
                    String[] lines = field.getValue().split("\\r?\\n");
                    for (String line : lines) {

                        String name = line.split(" ")[0].replaceAll("\\s+","");
                        String id = name.replaceAll("<", "")
                                .replaceAll("!", "")
                                .replaceAll(">", "");

                        String[] stats = line.split(" ")[1]
                                .replaceAll("\\s+","")
                                .split("-");

                        int wins = Integer.parseInt(stats[0]);
                        int losses = Integer.parseInt(stats[1]);
                        int abandons = Integer.parseInt(stats[2]);

                        Stat.create(id, wins, losses, abandons);
                        List<Stat> ordered = Stat.sort();
                        Stat.reset();

                        //TODO make new embed and format re-ordered stats into it
                    }
                }
            }
        }
    }

    public static class Stat implements Comparable<Stat> {

        private static List<Stat> stats = new ArrayList<>();

        private String id;
        private Integer wins;
        private int losses;
        private int abandons;

        public static void create(String userName, int wins, int losses, int abandons) {
            stats.add(new Stat(userName, wins, losses, abandons));
        }

        private Stat(String userName, int wins, int losses, int abandons) {
            this.id = userName;
            this.wins = wins;
            this.losses = losses;
            this.abandons = abandons;
        }

        public Integer getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getAbandons() {
            return abandons;
        }

        public String getId() {
            return id;
        }

        @Override
        public int compareTo(Stat s) {
            return getWins().compareTo(s.getWins());
        }

        public static void reset(){
            stats = new ArrayList<>();
        }

        public static List<Stat> sort() {
            Collections.sort(stats);
            return stats;
        }
    }
}