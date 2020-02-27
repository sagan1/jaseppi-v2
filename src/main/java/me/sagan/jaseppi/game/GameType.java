package me.sagan.jaseppi.game;

public enum GameType {

    TICTACTOE("TicTacToe"),
    CONNECT4("Connect4"),
    ;

    private String name;

    GameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}