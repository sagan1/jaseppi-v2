package me.sagan.jaseppi;

/**
 * @author cam (sagan/y0op)
 */
public enum Tokens {

    DISCORD_MAIN_TOKEN("NjQ2NzYzNDkxNTI4ODAyMzA1.XsdbbA.gOi6ZA8J4dd26bVSnSGrw_X8Wz4"),
    WEATHER_TOKEN("deb50cd547a7f29d5f5a32979b03ae5f"),
    NASA_TOKEN("EYOEhuMbfsqiqzoZVCpSZdbbGvw2Nb2zUEtaRPQr"),
    ;

    private String[] tokens;

    Tokens(String... tokens) {
        this.tokens = tokens;
    }

    public String[] getTokens() {
        return tokens;
    }

    public String getToken() {
        return System.getenv(this.tokens[0]);
    }

    public String getToken(String environmentVarName) {
        return System.getenv(environmentVarName);
    }
}