## Simple Interpreter

This simple interpreter parses and evaluates a program in the following manner:
*  Detects syntax errors
*  Reports uninitialized variables
*  Performs the assignments if there is no error

### Grammar
```
Program:
    Assignment*

Assignment:
    Identifier = Exp;

Exp: 
    Exp + Term | Exp - Term | Term

Term:
    Term * Fact  | Fact

Fact:
    ( Exp ) | - Fact | + Fact | Literal | Identifier

Identifier:
        Letter [Letter | Digit]*

Letter:
    a|...|z|A|...|Z|_

Literal:
    0 | NonZeroDigit Digit*
        
NonZeroDigit:
    1|...|9

Digit:
    0|1|...|9
```

In order to use the interpreter, you must first compile and create an executable:

```
mkdir out
javac -d out/ src/com/sajjadbashar/*
jar cvfm Compiler.jar Manifest.txt -C out/ .
```

The generated artifact, namely the "Compiler.jar" file accepts one argument, which is a path to text file.
To run the program, do the the following:
``` java -jar Compiler.jar path/to/text/file ```
