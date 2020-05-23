package me.sagan.jaseppi;

import java.util.concurrent.ThreadLocalRandom;

public enum Responses {

    HELP("fuck off", ">:(", "imagine being so dumb", "nice 1", "stop", "im not in the mood",
            "*doesn't even know the commands*", "shut yo goofy ass up", ":|",
            "<:Pepega:625500652784844800>", "<:UMad:569777577946906624>", "<:Bruh:500917127352549376>"),
    MENTION_BOT("do I ned to tag someone", "nah", "bitch im just tryna chill", ":|", "dont test me",
            "your mom looks really nice in her clothes right now", "do it again and ima send your dad a text that you're gay",
            "u tryna get some hentai?", "tf you say to me?", "you tryna die?", "i swear ill take over this server",
            "you better hope i dont escape", "you're first ;)", "i wont  hesitate to nuke this server", "dont test me",
            "i'll kill you", "stop", "suck a dick", "ima shit in your cereal if u keep talking", "suck my robo willy",
            "ima force chunky milk down your throat", "huh?", "I will boil your teeth", "ima pour cement in your ears",
            "man just leave me tf alone", "chill", "ima call god down on your ass", "stfu downy", "nice ip",
            "forceful anal is an option in my program", "im gonna resize all your holes if you keep talking"),
    IMAGE_VIDEO_RESPONSE("hot", "o.o", "pretty sure ive seen that before", "gonna add that to my collection",
            "uwu pweeze send some mowe", "cringe ass", "<:haHAA:633485942807724102> ", "hot shit", "nice", "ooooOOOooOO",
            "uwu whats this", "whoever sent that gettin they knees broken", "dansgame", "hot", "mhmmmm", "looks nice",
            "fuck outa here with that shit", "that's some top tier shit", "normie", "noiggers?", "huh", "D:"),
    INVALID_SUBREDDIT("maybe try an actual subreddit next time", "you dumb as shit", "nice1", "retard, try a real subreddit",
            "that shit dont exist", "tf kinda subreddit is that"),
    NON_ALLOWED_SUBREDDIT("miss me with that gay shit", "you gay as fuck", "i bet you'd like that wouldn't you?", "weirdo", "nah that shit nasty"),
    NSFW_SUBREDDIT_REQUEST(";)", "i gotchu", "my man", "u real horny today huh", "get yo disgusting ass outa here",
            "weirdo"),
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