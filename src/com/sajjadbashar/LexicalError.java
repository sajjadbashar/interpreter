package com.sajjadbashar;

public class LexicalError extends Exception {
    String msg;
    public LexicalError(String msg) {
        super();
        this.msg = msg;
    }

}
