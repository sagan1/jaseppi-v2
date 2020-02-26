package me.sagan.jaseppi.game.games;

import me.sagan.jaseppi.game.Game;
import me.sagan.jaseppi.game.GameType;
import me.sagan.jaseppi.game.Pair;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Arrays;

public class Connect4 extends Game {

    public static final String blank = "\u2B1C";
    public static final String red = "\uD83D\uDFE5";
    public static final String blue = "\uD83D\uDFE6";

    public String[][] asMatrix = new String[6][7];

    public Connect4(Pair players, TextChannel channel, GameType type) {
        super(players, channel, type);

        for (String[] arr : asMatrix) {
            Arrays.fill(arr, " ");
        }
    }

    @Override
    public void initializeEmbed() {
        EmbedBuilder eb = new EmbedBuilder();

        StringBuilder builder = new StringBuilder(" 1 2 3 4 5 6 7\n");

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

        eb.addField("Tic-Tac-Toe", builder.toString(), false);
        eb.addField("Turn:", super.getTurn().getEmoji() + " <@" + super.getTurn().getUserId() + ">", true);
        eb.addField("How to:", "Type: '.c4 1-7' to place a marker", false);
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

    //TODO make sure to check if the very top space is taken in commands
    @Override
    public void addTurn(int place) {
        int rowCheck = 0;
        int columnCheck = place;

        while (!placeTaken(rowCheck + 1, columnCheck)) {
            rowCheck++;
        }

        asMatrix[rowCheck][columnCheck] = super.getTurn().getTextBasedSymbol();
    }

    @Override
    public String findWin() {
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
