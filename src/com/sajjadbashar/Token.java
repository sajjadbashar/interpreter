package com.sajjadbashar;

class Token {

    public static enum Tag {
        LITERAL,
        IDENTIFIER,
        MULTIPLY,
        MINUS,
        PLUS,
        ASSIGN,
        LEFT_PAR,
        RIGHT_PAR,
        SEMICOLON,
        END_OF_FILE;
    }

    Tag tag;
    public Token(Tag t) {
        tag = t;
    }
}

class Literal extends Token {

    int value;
    public Literal(int v) {
        super(Tag.LITERAL);
        value = v;
    }
}

class Identifier extends Token {

    String lexeme;
    public Identifier(String l) {
        super(Tag.IDENTIFIER);
        lexeme = l;
    }
}
