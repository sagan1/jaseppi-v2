package me.sagan.jaseppi.game;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    public static List<Game> games = new ArrayList<>();

    private Pair players;
    private String messageId;
    private MessageEmbed embed;
    private Player turn;
    private TextChannel channel;
    private GameType type;

    public Game(Pair players, TextChannel channel, GameType type) {
        this.players = players;
        this.channel = channel;
        this.type = type;

        this.turn = players.getOne();
        this.players.getOne().setGameIn(this);
        this.players.getTwo().setGameIn(this);

        games.add(this);

        initializeEmbed();
    }

    public abstract void initializeEmbed();
    public abstract void updateEmbed();
    public abstract void addTurn(int place);
    public abstract String findWin();
    public abstract boolean findTie();

    public void switchTurn() {
        this.turn = this.turn.getUserId().equalsIgnoreCase(this.players.getOne().getUserId()) ?
                this.players.getTwo() : this.players.getOne();
    }

    public boolean placeTaken(int place) {
        return true;
    }

    public boolean placeTaken(int row, int col) {
        return true;
    }

    public void end() {
        channel.deleteMessageById(messageId).queue();
        games.remove(this);
    }

    public static Game getGame(String playerId) {
        for (Game game : games) {
            if (game.getPlayers().contains(playerId)) {
                return game;
            }
        }

        return null;
    }

    public boolean isTurn(String userId) {
        return this.turn.getUserId().equalsIgnoreCase(userId);
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageEmbed getEmbed() {
        return embed;
    }

    public void setEmbed(MessageEmbed embed) {
        this.embed = embed;
    }

    public Pair getPlayers() {
        return players;
    }

    public String getMessageId() {
        return messageId;
    }

    public Player getTurn() {
        return turn;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public GameType getType() {
        return type;
    }
}