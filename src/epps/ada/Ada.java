
/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import grammar.TokenPattern;
import grammar.Tokenizer;
import lexing.Scanner;
import lexing.ast.Program;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class Ada {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("usage: ada <path to ada file>");
            return;
        }

        Tokenizer tokenizer = new Tokenizer(TokenPattern.values());
        Path inputFilePath = FileSystems.getDefault().getPath(args[0]);
        Path outputFilePath = FileSystems.getDefault().getPath("./output.txt");

        try {
            InputStream inputFile = new BufferedInputStream(Files.newInputStream(inputFilePath));
            PrintStream outputFile = new PrintStream(Files.newOutputStream(outputFilePath, StandardOpenOption.APPEND));

            Scanner scanner = new Scanner(inputFile, tokenizer);
            TokenStream tokenStream = new TokenStream(scanner);
            Parser parser = new Parser(tokenStream);
            Program program = parser.parse();

            // System.out.println(program.toString());
            program.printProgram(outputFile);

            inputFile.close();
            outputFile.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("failed to parse input: " + e);
        }

    }
}