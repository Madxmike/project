import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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

        Tokenizer tokenizer = new Tokenizer(TokenPattern.LBRACE, TokenPattern.RBRACE, TokenPattern.PROCEDURE);
        Path filePath = FileSystems.getDefault().getPath(args[0]);
        try (InputStream file = Files.newInputStream(filePath)) {
            Scanner scanner = new Scanner(file, tokenizer);

            Token token = scanner.nextToken();
            while (token != null) {
                System.out.println(token);
                token = scanner.nextToken();
            }

        } catch (IOException e) {
            System.out.println("could not find and open file: " + args[0]);
            return;
        } catch (IllegalTokenException e) {
            System.err.println(e);
            return;
        }

    }
}