package me.sagan.jaseppiv2;

public enum Secret {

    TOKEN("NjQ2NzYzNDkxNTI4ODAyMzA1.XlSF0w.7zVALSOAKMh0SU5ttKpEJFBNkvc"),
    ;

    private String[] s;

    Secret(String... s) {
        this.s = s;
    }

    public String[] get() {
        return s;
    }
}