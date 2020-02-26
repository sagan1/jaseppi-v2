package me.sagan.jaseppiv2;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.List;

public class Tictactoe {

    public static List<Tictactoe> games = new ArrayList<>();

    private MessageEmbed embed;
    private String messageId;
    private Pair players;
    private String[][] asMatrix = new String[3][3];
    private Player turn;

    public Tictactoe(Pair players) {
        this.players = players;
        turn = players.getOne();

        // Fill all values with numbers
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                asMatrix[i][j] = String.valueOf(coordToPlace(i, j));
            }
        }

        games.add(this);

        EmbedBuilder eb = new EmbedBuilder();

        StringBuilder builder = new StringBuilder();
        for (String[] arr : asMatrix) {
            for (String s : arr) {
                switch (s) {
                    case "x":
                        builder.append("U+274C ");
                        break;
                    case "o":
                        builder.append("U+2B55 ");
                        break;
                    case " ": case "": default:
                        builder.append("U+2B1C ");
                        break;
                }
            }
            builder.append("\n");
        }

        eb.addField("Tic-Tac-Toe", builder.toString(), false);
        eb.addBlankField(false);
        eb.addField("Turn:", turn.getEmoji() + " <@" + turn.getPlayerId() + ">", false);
        embed = eb.build();
    }

    public static Tictactoe getGameFromMessageId(String messageId) {
        for (Tictactoe game : games) {
            if (game.getMessageId().equalsIgnoreCase(messageId)) {
                return game;
            }
        }

        return null;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageEmbed getEmbed() {
        return embed;
    }

    public Pair getPlayers() {
        return players;
    }

    public static boolean isInGame(String playerId) {
        return getGame(playerId) != null;
    }

    public static Tictactoe getGame(String playerId) {
        for (Tictactoe game : games) {
            if (game.players.contains(playerId)) {
                return game;
            }
        }

        return null;
    }

    public boolean isTurn(Player player) {
        return this.turn.getPlayerId().equalsIgnoreCase(player.getPlayerId());
    }

    public Player playerFromId(String playerId) {
        if (players.contains(playerId)) {
            return players.getOne().getPlayerId().equalsIgnoreCase(playerId) ? players.getOne() : players.getTwo();
        }

        return null;
    }

    public void updateMatrix(String val, int place) {
        updateMatrix(val, placeToCoord(place)[0], placeToCoord(place)[1]);
    }

    public void updateMatrix(String val, int row, int column) {
        this.asMatrix[row][column] = val;
    }

    public boolean placeTaken(int place) {
        return placeTaken(placeToCoord(place)[0], placeToCoord(place)[1]);
    }

    public boolean placeTaken(int row, int column) {
        return asMatrix[row][column].equalsIgnoreCase("o") ||
                asMatrix[row][column].equalsIgnoreCase("x");
    }

    public String findWin() {

        String win = null;

        // Horizontal
        for (String[] arr : asMatrix) {
            if (allEqual(arr, "x")) {
                win = "x";
                return win;
            } else if (allEqual(arr, "o")) {
                win = "o";
                return win;
            }
        }

        // Vertical
        for (int i = 0; i < 3; i++) {
            String[] check = new String[]{asMatrix[0][i], asMatrix[1][i], asMatrix[2][i]};
            if (allEqual(check, "x")) {
                win = "x";
                return win;
            } else if (allEqual(check, "o")) {
                win = "o";
                return win;
            }
        }

        // Diagonal
        String[] check = new String[3];
        String[] check2 = new String[3];

        for (int i = 0; i < 3; i++) {
            check[i] = asMatrix[i][i];
            check2[i] = asMatrix[i][2-i];
        }

        if (allEqual(check, "x") || allEqual(check2, "x")) {
            win = "x";
            return win;
        } else if (allEqual(check, "o") || allEqual(check2, "o")) {
            win = "x";
            return win;
        }

        return win;
    }

    public static int coordToPlace(int row, int column) {
        return row * 3 + (column + 1);
    }

    public static int[] placeToCoord(int place) {
        int row = place >= 6 ? 2 : (place >= 3 ? 1 : 0);
        int column = row == 2 ? place - 7 : (row == 1 ? place - 4 : place - 1);

        return new int[]{row, column};
    }

    public static boolean allEqual(String[] arr, String val) {
        boolean equal = true;
        for (String s : arr) {
            if (!s.equalsIgnoreCase(val)) {
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static class Player {
        private String emoji;
        private String playerId;

        public Player(String symbol, String playerId) {
            this.emoji = symbol;
            this.playerId = playerId;
        }

        public String getEmoji() {
            return emoji;
        }

        public String getPlayerId() {
            return playerId;
        }
    }
}