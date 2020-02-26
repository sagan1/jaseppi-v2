package me.sagan.jaseppi.game.games;

import me.sagan.jaseppi.Util;
import me.sagan.jaseppi.game.Game;
import me.sagan.jaseppi.game.GameType;
import me.sagan.jaseppi.game.Pair;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;

public class TicTacToe extends Game {

    public static final String blank = "\u2B1C";
    public static final String x = "\u274C";
    public static final String o = "\u2B55";

    private String[][] asMatrix = new String[3][3];

    public TicTacToe(Pair players, TextChannel channel, GameType type) {
        super(players, channel, type);

        for (String[] arr : asMatrix) {
            Arrays.fill(arr, " ");
        }
    }

    @Override
    public void initializeEmbed() {
        EmbedBuilder eb = new EmbedBuilder();

        StringBuilder builder = new StringBuilder();
        for (String[] arr : asMatrix) {
            for (String s : arr) {
                switch (s) {
                    case "x":
                        builder.append(x);
                        break;
                    case "o":
                        builder.append(o);
                        break;
                    case " ": case "": default:
                        builder.append(blank);
                        break;
                }
            }
            builder.append("\n");
        }

        eb.addField("Tic-Tac-Toe", builder.toString(), false);
        eb.addField("Turn:", super.getTurn().getEmoji() + " <@" + super.getTurn().getUserId() + ">", true);
        eb.addField("How to:", "Type: '.ttt 1-9' to place a marker", false);
        super.setEmbed(eb.build());
    }

    @Override
    public void updateEmbed() {
        initializeEmbed();

        if (super.getMessageId() != null) {
            super.getChannel().editMessageById(super.getMessageId(), super.getEmbed()).queue();
        }
    }

    @Override
    public void addTurn(int place) {
        int[] i = placeToCoord(place);
        this.asMatrix[i[0]][i[1]] = super.getTurn().getTextBasedSymbol();
    }

    @Override
    public String findWin() {

        String win = null;

        // Horizontal
        for (String[] arr : asMatrix) {
            if (Util.allEqual(arr, "x")) {
                win = "x";
                return win;
            } else if (Util.allEqual(arr, "o")) {
                win = "o";
                return win;
            }
        }

        // Vertical
        for (int i = 0; i < 3; i++) {
            String[] check = new String[]{asMatrix[0][i], asMatrix[1][i], asMatrix[2][i]};
            if (Util.allEqual(check, "x")) {
                win = "x";
                return win;
            } else if (Util.allEqual(check, "o")) {
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

        if (Util.allEqual(check, "x") || Util.allEqual(check2, "x")) {
            win = "x";
            return win;
        } else if (Util.allEqual(check, "o") || Util.allEqual(check2, "o")) {
            win = "x";
            return win;
        }

        return win;
    }

    @Override
    public boolean findTie() {
        boolean tie = true;

        for (String[] arr : asMatrix) {
            for (String s : arr) {
                if (s.equalsIgnoreCase(" ") || s.equalsIgnoreCase("")) {
                    tie = false;
                    break;
                }
            }
        }

        return tie;
    }

    public static int[] placeToCoord(int place) {
        int row = place >= 7 ? 2 : (place >= 4 ? 1 : 0);
        int column = row == 2 ? (place - 7) : (row == 1 ? (place - 4) : (place - 1));

        return new int[]{row, column};
    }

    @Override
    public boolean placeTaken(int place) {
        return placeTaken(placeToCoord(place)[0], placeToCoord(place)[1]);
    }

    public boolean placeTaken(int row, int column) {
        return asMatrix[row][column].equalsIgnoreCase("o") ||
                asMatrix[row][column].equalsIgnoreCase("x");
    }
}