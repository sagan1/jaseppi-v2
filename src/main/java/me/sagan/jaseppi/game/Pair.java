package me.sagan.jaseppi.game;

public class Pair {

    private Player p1;
    private Player p2;

    public Pair(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player getOne() {
        return p1;
    }

    public Player getTwo() {
        return p2;
    }

    public boolean contains(String playerId) {
        return p1.getUserId().equalsIgnoreCase(playerId) || p2.getUserId().equalsIgnoreCase(playerId);
    }

    public Player getPlayer(String userId) {
        if (!contains(userId)) return null;
        return p1.getUserId().equalsIgnoreCase(userId) ? p1 : p2;
    }
}