package me.sagan.jaseppi;

/**
 * @author cam (sagan/y0op)
 */
public enum Tokens {
    MAIN("NjQ2NzYzNDkxNTI4ODAyMzA1.XsdORw.g8HeIDQII0eo95KG_ho29jSyDhM"),
    WEATHER("deb50cd547a7f29d5f5a32979b03ae5f"),
    NASA("EYOEhuMbfsqiqzoZVCpSZdbbGvw2Nb2zUEtaRPQr")
    ;

    private String[] keys;

    Tokens(String... keys) {
        this.keys = keys;
    }

    public String[] getKeys() {
        return keys;
    }
}