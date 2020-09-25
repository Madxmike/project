package lexing;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import grammar.Token;
import grammar.Tokenizer;
import grammar.Tokenizer.IllegalTokenException;

/*
* A Scanner will read from an input stream and interperate all read bytes as a sequence of characters.
* A Scanner will continually read from an input stream until the stream is empty or closed.
*/
public class Scanner {

    private InputStream input;
    private Tokenizer tokenizer;

    public Scanner(InputStream input, Tokenizer tokenizer) {
        this.input = input;
        this.tokenizer = tokenizer;
    }

    public Token nextToken() throws IOException, IllegalTokenException {
        this.tokenizer.reset();

        try {
            char c = this.skipWhitespace();
            while (!this.tokenizer.hasMatch() && !isWhitespace(c)) {
                this.tokenizer.append(c);
                c = this.next();
            }
            return this.tokenizer.getToken();
        } catch (EndOfStreamException e) {
            return this.tokenizer.getToken();
        }
    }

    public char skipWhitespace() throws IOException, EndOfStreamException {
        char c = this.next();

        while (isWhitespace(c)) {
            c = this.next();
        }

        return c;
    }

    public boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public char next() throws IOException, EndOfStreamException {
        int in = this.input.read();
        if (in == -1) {
            throw new EndOfStreamException();
        }

        return (char) in;
    }

    private class EndOfStreamException extends Exception {
    }

}
