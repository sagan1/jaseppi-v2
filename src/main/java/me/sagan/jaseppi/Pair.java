package me.sagan.jaseppi;

public class Pair {

    private Tictactoe.Player p1;
    private Tictactoe.Player p2;

    public Pair(Tictactoe.Player p1, Tictactoe.Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Tictactoe.Player getOne() {
        return p1;
    }

    public void setOne(Tictactoe.Player p1) {
        this.p1 = p1;
    }

    public Tictactoe.Player getTwo() {
        return p2;
    }

    public void setTwo(Tictactoe.Player p2) {
        this.p2 = p2;
    }

    public boolean contains(String playerId) {
        return p1.getPlayerId().equalsIgnoreCase(playerId) || p2.getPlayerId().equalsIgnoreCase(playerId);
    }

    public Tictactoe.Player getPlayer(String playerId) {
        if (!contains(playerId)) return null;
        return p1.getPlayerId().equalsIgnoreCase(playerId) ? p1 : p2;
    }
}