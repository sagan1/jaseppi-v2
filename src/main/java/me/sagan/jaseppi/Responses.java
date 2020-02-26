package me.sagan.jaseppi;

import java.util.concurrent.ThreadLocalRandom;

public enum Responses {

    HELP("fuck off", ">:(", "imagine being so dumb", "nice 1", "stop", "im not in the mood",
            "*doesn't even know the commands*", "shut yo goofy ass up", ":|"),
    TICTACTOE("you dumb? use like: .tictactoe @person"),
    ;

    private String[] s;

    Responses(String... s) {
        this.s = s;
    }

    public String[] get() {
        return this.s;
    }

    public String getRandom() {
        return this.s.length > 1 ? this.s[ThreadLocalRandom.current().nextInt(s.length-1)] : s[0];
    }
}