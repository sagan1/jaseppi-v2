package me.sagan.jaseppi.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {

    public static List<Command> commands = new ArrayList<>();

    public static void register(Command c) {
        commands.add(c);
    }
}