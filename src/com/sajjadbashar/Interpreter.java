package com.sajjadbashar;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Interpreter {

    private Lexer lexer;
    private Map<String, Integer> stored;
    private Token current;

    public Interpreter(InputStream in) throws IOException, LexicalError {
        lexer = new Lexer(in);
        stored = new LinkedHashMap<>();
        next();
    }

    public void interpret() throws IOException, LexicalError, ParseError {
        this.program();
        for (Map.Entry<String, Integer> entry : stored.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    private void program() throws IOException, LexicalError, ParseError {
        while (current.tag != Token.Tag.END_OF_FILE)
            assignment();
    }

    private void assignment() throws IOException, ParseError, LexicalError {
        String id = id();
        match(Token.Tag.ASSIGN);
        int value = expr();
        match(Token.Tag.SEMICOLON);
        stored.put(id, value);
    }

    private int expr() throws IOException, ParseError, LexicalError {
        int res = 0;
        res = term();
        switch (current.tag) {
            case MINUS:
                match(Token.Tag.MINUS);
                return res - expr();
            case PLUS:
                match(Token.Tag.PLUS);
                return res + expr();
            default:
                break;
        }
        return res;
    }

    private int term() throws IOException, LexicalError, ParseError {
        int res = 0;
        res = fact();
        if (current.tag == Token.Tag.MULTIPLY) {
            match(Token.Tag.MULTIPLY);
            return res * fact();
        }
        return res;
    }

    private int fact() throws IOException, LexicalError, ParseError {
        int res = 0;
        switch (current.tag) {
            case LITERAL:
                int r = ((Literal) current).value;
                next();
                return r;
            case IDENTIFIER:
                Identifier id = (Identifier) current;
                if (!stored.containsKey(id.lexeme))
                    throw new Error();
                next();
                return stored.get(id.lexeme);
            case MINUS:
                match(Token.Tag.MINUS);
                return -1 * fact();
            case PLUS:
                match(Token.Tag.PLUS);
                return fact();
            case LEFT_PAR:
                match(Token.Tag.LEFT_PAR);
                res = expr();
                match(Token.Tag.RIGHT_PAR);
                break;
        }
        return res;
    }

    private String id() throws IOException, ParseError, LexicalError {
        if (current.tag != Token.Tag.IDENTIFIER)
            throw new Error();
        String id = ((Identifier) current).lexeme;
        match(Token.Tag.IDENTIFIER);
        return id;
    }


    private void next() throws IOException, LexicalError {
        current = lexer.nextToken();
    }

    private void match(Token.Tag t) throws IOException, ParseError, LexicalError {
        if (current.tag == t)
            next();
        else
            throw new ParseError("Unexpected token at line: " + lexer.row + " col: " + lexer.col);
    }
}
