package me.sagan.jaseppi.game.games;

import me.sagan.jaseppi.game.Game;
import me.sagan.jaseppi.game.GameType;
import me.sagan.jaseppi.game.Pair;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Connect4 extends Game {

    public static final String blank = "\u2B1C";
    public static final String red = "\uD83D\uDFE5";
    public static final String blue = "\uD83D\uDFE6";

    public String[][] asMatrix = {
            {" ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " "},
    };

    public static Connect4 createGame(Pair players, TextChannel channel, GameType type) {
        Connect4 connect4 = new Connect4(players, channel, type);
        connect4.initializeEmbed();
        return connect4;
    }

    public Connect4(Pair players, TextChannel channel, GameType type) {
        super(players, channel, type);
    }

    @Override
    public void initializeEmbed() {
        EmbedBuilder eb = new EmbedBuilder();

        StringBuilder builder = new StringBuilder("\u0031\uFE0F\u20E3" +
                "\u0032\uFE0F\u20E3" +
                "\u0033\uFE0F\u20E3" +
                "\u0034\uFE0F\u20E3" +
                "\u0035\uFE0F\u20E3" +
                "\u0036\uFE0F\u20E3" +
                "\u0037\uFE0F\u20E3" +
                "\n");

        for (String[] arr : asMatrix) {
            for (String s : arr) {
                switch (s) {
                    case "r": case "red":
                        builder.append(red);
                        break;
                    case "b": case "blue":
                        builder.append(blue);
                        break;
                    case " ": case "": default:
                        builder.append(blank);
                        break;
                }
            }
            builder.append("\n");
        }

        eb.addField("Connect4", builder.toString(), false);
        eb.addField("Turn:", super.getTurn().getEmoji() + " <@" + super.getTurn().getUserId() + ">", true);
        eb.addField("How to:", "`.c4 1-7` to place a marker\n`.c4 exit` to leave", false);
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
    public boolean placeTaken(int row, int col) {
        return asMatrix[row][col].equalsIgnoreCase(super.getPlayers().getOne().getTextBasedSymbol()) ||
                asMatrix[row][col].equalsIgnoreCase(super.getPlayers().getTwo().getTextBasedSymbol());
    }

    @Override
    public boolean placeTaken(int place) {
        return true;
    }

    @Override
    public void addTurn(int place) {
        int rowCheck = 0;

        while (!placeTaken(rowCheck + 1, place)) {
            rowCheck++;
            if (rowCheck == 5) break;
        }

        asMatrix[rowCheck][place] = super.getTurn().getTextBasedSymbol();
    }

    @Override
    public String findWin() {

        int[][] directions = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

        for (int[] direction : directions) {

            int dx = direction[0];
            int dy = direction[1];

            for (int y = 0; y < asMatrix.length; y++) {
                for (int x = 0; x < asMatrix[y].length; x++) {
                    // y = row  x = col
                    int furthestX = x + 3*dx;
                    int furthestY = y + 3*dy;

                    if (furthestX < 0 || furthestX > 6 || furthestY < 0 || furthestY > 5) continue;
                    if (!placeTaken(y, x)) continue;

                    int[][] toCheck = {{x + dx, y + dy}, {x + 2*dx, y + 2*dy}, {furthestX, furthestY}};
                    String toCompare = asMatrix[y][x];

                    boolean won = true;

                    for (int[] ints : toCheck) {
                        if (!asMatrix[ints[1]][ints[0]].equalsIgnoreCase(toCompare)) {
                            won = false;
                            break;
                        }
                    }

                    if (won) return toCompare;
                }
            }
        }

        return null;
    }

    @Override
    public boolean findTie() {
        for (String[] arr : asMatrix) {
            for (String s : arr) {
                if (s.equalsIgnoreCase(" ") || s.equalsIgnoreCase("")) {
                    return false;
                }
            }
        }

        return true;
    }
}
