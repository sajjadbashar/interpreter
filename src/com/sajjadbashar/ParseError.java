package com.sajjadbashar;

public class ParseError extends Exception {

    String msg;

    public ParseError(String msg) {
        super();
        this.msg = msg;
    }
}
