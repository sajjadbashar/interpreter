package com.sajjadbashar;

import java.io.*;

public class Main {

    public static void main(String[] args){
        File input = null;
        Interpreter interpreter;

        if (args.length > 0) {
            input = new File(args[0]);
        } else {
            System.out.println("A path to file is required.");
            System.exit(1);
        }
        try (InputStream in = new FileInputStream(input) ) {

            interpreter = new Interpreter(in);
            interpreter.interpret();

        } catch (LexicalError e) {
            System.out.println(e.msg);
        } catch (ParseError e) {
            System.out.println(e.msg);
        } catch (FileNotFoundException e) {
            System.out.println("Could not find specified file.");
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the file.");
        }
        System.exit(0);
    }
}
