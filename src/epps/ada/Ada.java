import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import grammar.Token;
import grammar.TokenPattern;
import grammar.Tokenizer;
import grammar.Tokenizer.IllegalTokenException;
import lexing.Scanner;

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

            Token token = scanner.nextToken();
            while (token != null) {
                System.out.println(token);
                outputFile.println(token.toString());
                token = scanner.nextToken();
            }

            inputFile.close();
            outputFile.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (IllegalTokenException e) {
            System.err.println(e);
        }



    }
}