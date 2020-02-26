package me.sagan.jaseppi.game;

public class Player {

    private String userId;
    private String emoji;
    private String textBasedSymbol;
    private Game gameIn;

    public Player(String userId, String symbol, String textBasedSymbol) {
        this.userId = userId;
        this.emoji = symbol;
        this.textBasedSymbol = textBasedSymbol;
    }

    public void setGameIn(Game gameIn) {
        this.gameIn = gameIn;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getTextBasedSymbol() {
        return textBasedSymbol;
    }

    public Game getGameIn() {
        return gameIn;
    }

    public static boolean isInGame(String userId) {
        for (Game game : Game.games) {
            if (game.getPlayers().contains(userId)) {
                return true;
            }
        }
        return false;
    }

    public static Player fromId(String userId) {
        for (Game game : Game.games) {
            if (game.getPlayers().getOne().getUserId().equalsIgnoreCase(userId)) {
                return game.getPlayers().getOne();
            } else if (game.getPlayers().getTwo().getUserId().equalsIgnoreCase(userId)) {
                return game.getPlayers().getTwo();
            }
        }
        return null;
    }
}