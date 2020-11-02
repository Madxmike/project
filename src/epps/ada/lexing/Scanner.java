
package lexing;

import java.io.IOException;
import java.io.InputStream;

import grammar.Token;
import grammar.TokenPattern;
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

    public Token nextToken() throws IllegalTokenException {
        try {
            if (this.input.available() == 0) {
                return new Token("", TokenPattern.EOF);
            }

            this.tokenizer.reset();
            char c = this.skipWhitespace();

            boolean peeking = false;
            while (this.tokenizer.allowWhitespace() || !isWhitespace(c)) {
                this.tokenizer.append(c);

                if (this.tokenizer.hasMatch()) {
                    // If we match we need to do a look ahead to see if we should continue or not
                    // as "fo" is an indentifier but so is "foo"
                    peeking = true;
                }

                if (peeking && !this.tokenizer.hasMatch()) {
                    peeking = false;
                    this.input.reset();
                    this.tokenizer.popLast();
                    break;
                }

                c = this.next(peeking);
            }
            return this.tokenizer.getToken();
        } catch (EndOfStreamException e) {
            return this.tokenizer.getToken();
        } catch (IOException e) {
            return this.tokenizer.getToken();
        }
    }

    public char skipWhitespace() throws IOException, EndOfStreamException {
        char c = this.next(false);

        while (isWhitespace(c)) {
            c = this.next(false);
        }

        return c;
    }

    public boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    public char next(boolean peek) throws IOException, EndOfStreamException {
        if (peek) {
            this.input.mark(1);
        }
        int in = this.input.read();
        if (in == -1) {
            throw new EndOfStreamException();
        }

        return (char) in;
    }

    @SuppressWarnings("serial")
    private class EndOfStreamException extends Exception {
    }

}
