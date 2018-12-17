package com.sajjadbashar;

import java.io.IOException;
import java.io.InputStream;

class Lexer {
    public static final char END_OF_FILE = 26;

    int row, col;
    InputStream in;
    char current;

    public Lexer(InputStream in) {
        this.in = in;
        current = ' ';
        row = 1;
        col = 1;
    }


    private void readNext() throws IOException {
        int read = in.read();
        if (read == -1)
            current = END_OF_FILE;
        else
            current = (char) read;
    }

    public Token nextToken() throws IOException, LexicalError {
        while (true) {
            if (current == ' ' || current == '\t') {
                col++;
                readNext();
                continue;
            } else if (current == '\n') {
                col = 1;
                row++;
                readNext();
                continue;
            } else if (current == END_OF_FILE)
                return new Token(Token.Tag.END_OF_FILE);
            else
                break;
        }

        if ( isDigit(current) ) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(current);
                readNext();
            } while ( isDigit(current) );

            String literal = sb.toString();
            if (isValidLiteral(literal))
                return new Literal( Integer.valueOf(literal) );
            else
                throw new LexicalError("Could not parse literal number at line: " + row + " col: " + col);
        }

        if ( isLetter(current) ) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(current);
                readNext();
            } while ( isLetter(current) || isDigit(current) );

            String identifier = sb.toString();
            if (isValidIdentifier(identifier))
                return new Identifier(identifier);
            else
                throw new LexicalError("Illegal identifier at line: " + row + " col: " + col);
        }
        switch (current) {
            case '*':
                readNext();
                return new Token(Token.Tag.MULTIPLY);
            case '-':
                readNext();
                return new Token(Token.Tag.MINUS);
            case '+':
                readNext();
                return new Token(Token.Tag.PLUS);
            case '=':
                readNext();
                return new Token(Token.Tag.ASSIGN);
            case '(':
                readNext();
                return new Token(Token.Tag.LEFT_PAR);
            case ')':
                readNext();
                return new Token(Token.Tag.RIGHT_PAR);
            case ';':
                readNext();
                return new Token(Token.Tag.SEMICOLON);
            default:
                break;
        }

        throw new LexicalError("Unknown token at line: " + row + " col: " + col);
    }

    private boolean isValidIdentifier(String s) {
        if (!isLetter(s.charAt(0))) return false;
        for (int i = 1; i < s.length(); i++)
            if (!isLetter(s.charAt(i)) && !isDigit(s.charAt(i)))
                return false;
        return true;
    }

    private boolean isValidLiteral(String s) {
        if (s.charAt(0) == '0' && s.length() > 1)
            return false;
        for (int i = 0; i < s.length(); i++)
            if ( !isDigit(s.charAt(i)) )
                return false;
        return true;
    }

    private boolean isLetter(char ch) { return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch == '_'); }

    private boolean isDigit(char ch) { return isNonZeroDigit(ch) || ch == '0'; }

    private boolean isNonZeroDigit(char ch) { return ch >= '1' && ch <= '9'; }

}