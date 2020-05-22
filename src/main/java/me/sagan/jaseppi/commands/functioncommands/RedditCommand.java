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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author cam (sagan/y0op)
 */
public class RedditCommand extends Command {

    private Map<String, String> basicHeaders;
    private Map<String, String> payloadData;
    private Map<String, String> authData;

    public RedditCommand() {
        super("reddit", 0, 1, 0, ".reddit (subreddit)");

        basicHeaders = new HashMap<>();
        basicHeaders.put("user-agent", "basement-bot-jaseppi by y0op");

        payloadData = new HashMap<>();
        payloadData.put("grant_type", "password");
        payloadData.put("username", Tokens.REDDIT.getToken("REDDIT_USERNAME"));
        payloadData.put("password", Tokens.REDDIT.getToken("REDDIT_PASSWORD"));

        authData = new HashMap<>();
        authData.put(Tokens.REDDIT.getToken("REDDIT_CLIENT_ID"),
                Tokens.REDDIT.getToken("REDDIT_CLIENT_SECRET"));
    }

    @Override
    public void handle(Message message, Member author, TextChannel channel, String[] args) {
        String subredditName = args.length == 0 ? "dankmemes" : args[0];

        if (subredditName.contains("man") || subredditName.contains("men") || subredditName.contains("male") ||
                subredditName.contains("gay") || subredditName.contains("homo")) {
            Jaseppi.send(channel, Responses.HOMOSEXUAL_SUBREDDIT.getRandom());
            return;
        }

        String tokenAccessUrl = "https://www.reddit.com/api/v1/access_token";

        Map<String, String> basicHeaders = new HashMap<>();
        basicHeaders.put("user-agent", "basement-bot-jaseppi by y0op");

        Map<String, String> payloadData = new HashMap<>();
        payloadData.put("grant_type", "password");
        payloadData.put("username", Tokens.REDDIT.getToken("REDDIT_USERNAME"));
        payloadData.put("password", Tokens.REDDIT.getToken("REDDIT_PASSWORD"));

        Map<String, String> authData = new HashMap<>();
        authData.put(Tokens.REDDIT.getToken("REDDIT_CLIENT_ID"),
                Tokens.REDDIT.getToken("REDDIT_CLIENT_SECRET"));

        String tokenResponse = Util.jsonPost(tokenAccessUrl, basicHeaders, payloadData, authData);

        String token = "bearer " + Util.jsonGet("access_token", tokenResponse);
        String baseUrl = "https://oauth.reddit.com/r/dankmemes/hot.json?sort=hot&limit=1";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("User-Agent", "basement-bot-jasepii by y0op");
        String response = Util.jsonGrab(baseUrl, headers, Collections.emptyMap());

        System.out.println(response);

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