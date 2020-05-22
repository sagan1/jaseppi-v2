package me.sagan.jaseppi.commands.functioncommands;

import me.sagan.jaseppi.Jaseppi;
import me.sagan.jaseppi.Responses;
import me.sagan.jaseppi.Tokens;
import me.sagan.jaseppi.Util;
import me.sagan.jaseppi.commands.Command;
import net.dean.jraw.ApiException;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.SearchSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author cam (sagan/y0op)
 */
public class RedditCommand extends Command {

    private UserAgent userAgent;
    private Credentials credentials;
    private NetworkAdapter networkAdapter;
    private RedditClient redditClient;

    public RedditCommand() {
        super("reddit", 0, 1, 0, ".reddit (subreddit)");
        this.userAgent = new UserAgent("basement-bot", "me.sagan.jaseppi", "1.0.0", "y0op");

        credentials = Credentials.script(Tokens.REDDIT.getToken("REDDIT_USERNAME"),
                Tokens.REDDIT.getToken("REDDIT_PASSWORD"),
                Tokens.REDDIT.getToken("REDDIT_CLIENT_ID"),
                Tokens.REDDIT.getToken("REDDIT_CLIENT_SECRET"));
        networkAdapter = new OkHttpNetworkAdapter(userAgent);
        redditClient = OAuthHelper.automatic(networkAdapter, credentials);
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {
        String subredditName = args.length == 0 ? "dankmemes" : args[0];

        if (subredditName.contains("man") || subredditName.contains("men") || subredditName.contains("male") ||
                subredditName.contains("gay") || subredditName.contains("homo")) {
            Jaseppi.send(channel, Responses.HOMOSEXUAL_SUBREDDIT.getRandom());
            return;
        }

        String found = Util.jsonGrab("https://reddit.com/r/" + subredditName + "/hot?limit=1");
        System.out.println(found);

        /*
        try {
            Listing<Submission> submissions = redditClient.subreddit(subredditName).posts().sorting(SubredditSort.HOT).limit(10).build().next();
            Submission submission = submissions.get(ThreadLocalRandom.current().nextInt(1, 10));
            if (submission.isNsfw() || redditClient.subreddit(subredditName).about().isNsfw()) {

            } else {
                Jaseppi.send(channel, submission.getPermalink());
                Jaseppi.send(channel, submission.getThumbnail());
                Jaseppi.send(channel, submission.getUrl());
                Jaseppi.send(channel, submission.getPreview().getImages().get(0).getSource().getUrl());
            }
        } catch (ApiException | NetworkException | NullPointerException e) {
            Jaseppi.send(channel, Responses.INVALID_SUBREDDIT.getRandom());
            e.printStackTrace();
            return;
        }*/
    }
}