package me.sagan.jaseppi.game.statistic;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.game.GameType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticManager {

    public static TextChannel channel;
    public static Message message;
    public static MessageEmbed embed;

    public static void initialize() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Statistics -> (Wins-Losses-Abandons)");
        for (GameType type : GameType.values()) {
            eb.addField(type.getName(), "none", true);
        }
        embed = eb.build();

        try {
            channel = Jaseppi.jda.awaitReady().getTextChannelById("682451917376323607");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        if (channel.getHistoryFromBeginning(10).complete().getRetrievedHistory().isEmpty() ||
                !channel.hasLatestMessage()) {
            channel.sendMessage(embed).queue();
        }
    }

    public static void update(Statistic... statistics) {
        if (channel != null) {
            if (!channel.getHistoryFromBeginning(10).complete().getRetrievedHistory().isEmpty() ||
                    channel.hasLatestMessage()) {
                message = channel.retrieveMessageById(channel.getLatestMessageId()).complete();

                embed = message.getEmbeds().get(0);

                List<Statistic> presentValues = new ArrayList<>();

                for (GameType type : GameType.values()) {
                    MessageEmbed.Field field = embed.getFields().stream()
                            .filter(f -> f.getName().equalsIgnoreCase(type.getName()))
                            .findFirst()
                            .orElse(null);

                    if (field == null) {
                        System.out.println("No field found for: " + type.getName());
                        return;
                    }

                    if (!field.getValue().isBlank() && !field.getValue().isEmpty() &&
                            field.getValue() != null && !field.getValue().equalsIgnoreCase("none")) {

                            String[] lines = field.getValue().split("\\r?\\n");

                            for (String line : lines) {
                                String userId = line.split(":")[0].trim().replaceAll("[<>@!]", "");
                                String[] stats = line.split(":")[1].trim().split("-");

                                presentValues.add(new Statistic(type, userId, Integer.parseInt(stats[0]),
                                        Integer.parseInt(stats[1]), Integer.parseInt(stats[2])));
                            }
                    }
                }

                for (Statistic statistic : statistics) {
                    Statistic alreadyFound = presentValues.stream().filter(s -> s.equals(statistic))
                            .findFirst()
                            .orElse(null);

                    if (alreadyFound != null) {
                        // Found old one, update it
                        alreadyFound.addToWins(statistic.getWins());
                        alreadyFound.addToLosses(statistic.getLosses());
                        alreadyFound.addToAbandons(statistic.getAbandons());
                    } else {
                        // Nothing, add new one
                        presentValues.add(statistic);
                    }
                }

                EmbedBuilder eb = new EmbedBuilder().setAuthor("Statistics -> (Wins-Losses-Abandons)");
                for (GameType type : GameType.values()) {
                    List<Statistic> typeSpecific = presentValues.stream()
                            .filter(s -> s.getGameType() == type)
                            .sorted().collect(Collectors.toList());

                    StringBuilder column = new StringBuilder();
                    if (typeSpecific.isEmpty()) column.append("none");
                    for (Statistic s : typeSpecific) {
                        column.append("<@" + s.getUserId() + ">: " + s.getWins() + "-" + s.getLosses() + "-" + s.getAbandons() + "\n");
                    }

                    eb.addField(type.getName(), column.toString(), true);
                }

                message.editMessage(eb.build()).queue();

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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }

            Statistic s = (Statistic) o;
            return s.getUserId().equalsIgnoreCase(this.userId) &&
                    s.getGameType() == this.gameType;
        }
    }
}