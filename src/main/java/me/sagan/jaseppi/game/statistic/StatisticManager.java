package me.sagan.jaseppi.game.statistic;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.game.GameType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticManager {

    public static TextChannel channel;
    public static Message message;
    public static MessageEmbed embed;

    public static void initialize() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Statistics -> (Wins-Losses-Abandons)");
        for (GameType type : GameType.values()) {
            eb.addField(type.getName(), "", true);
        }
        embed = eb.build();

        try {
            channel = Jaseppi.jda.awaitReady().getTextChannelById("682451917376323607");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!channel.hasLatestMessage()) {
            channel.sendMessage(embed).queue();
        }
    }

    public static void update(Statistic... statistics) {
        if (channel != null) {
            if (channel.hasLatestMessage()) {
                message = channel.retrieveMessageById(channel.getLatestMessageId()).complete();

                embed = message.getEmbeds().get(0);

                EmbedBuilder ebNew = new EmbedBuilder().setAuthor("Statistics -> (Wins-Losses-Abandons");

                for (GameType type : GameType.values()) {
                    List<Statistic> sortedOrganized = Arrays.stream(statistics).filter(statistic -> statistic.getGameType() == type).sorted().collect(Collectors.toList());
                    List<Statistic> toEdit = new ArrayList<>();

                    // Populate already present values
                    for (MessageEmbed.Field field : embed.getFields()) {
                        if (field.getName().equalsIgnoreCase(type.getName())) {
                            String[] lines = field.getValue().split("\\r?\\n");



                            for (String line : lines) {
                                String id = line.replaceAll("\\s+","")
                                        .split(":")[0]
                                        .replaceAll("<", "")
                                        .replaceAll("!", "")
                                        .replaceAll(">", "")
                                        .replaceAll("@", "");

                                String[] stats = line.replaceAll("\\s+","")
                                        .split(":")[1]
                                        .split("-");

                                int wins = Integer.parseInt(stats[0]);
                                int losses = Integer.parseInt(stats[1]);
                                int abandons = Integer.parseInt(stats[2]);

                                toEdit.add(new Statistic(type, id, wins, losses, abandons));
                            }
                        }
                    }

                    for (Statistic update : sortedOrganized) {
                        for (Statistic old : toEdit) {
                            if (old.getUserId().equalsIgnoreCase(update.getUserId())) {
                                // Existing stat, up the counters
                                old.addToWins(update.getWins());
                                old.addToAbandons(update.getAbandons());
                                old.addToAbandons(update.getLosses());
                            } else {
                                toEdit.add(update);
                                // No new stat, put it in
                            }
                        }
                    }

                    Collections.sort(toEdit);

                    StringBuilder columnBuilder = new StringBuilder();

                    toEdit.forEach(statistic ->
                            columnBuilder.append("<@" + statistic.getUserId() + ">: " +
                            statistic.getWins() + "-" + statistic.getLosses()
                            + "-" + statistic.getAbandons() + "\n"));

                    ebNew.addField(type.getName(), columnBuilder.toString(), true);
                }
            } else {
                System.out.println("No statistic template found");
            }
        }
    }

    public static class Statistic implements Comparable<Statistic> {

        private GameType gameType;
        private String userId;
        private Integer wins;
        private int losses;
        private int abandons;

        public Statistic(GameType gameType, String userId, Integer wins, int losses, int abandons) {
            this.gameType = gameType;
            this.userId = userId;
            this.wins = wins;
            this.losses = losses;
            this.abandons = abandons;
        }

        public void addToWins(int amonnt) {
            this.wins += amonnt;
        }

        public void addToLosses(int amount) {
            this.losses += amount;
        }

        public void addToAbandons(int amount) {
            this.abandons += amount;
        }

        public GameType getGameType() {
            return gameType;
        }

        public String getUserId() {
            return userId;
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

        @Override
        public int compareTo(Statistic s) {
            return getWins().compareTo(s.getWins());
        }
    }
}