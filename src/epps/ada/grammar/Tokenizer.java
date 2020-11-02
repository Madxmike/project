package grammar;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lexing.errors.ParsingException;
import lexing.parsing.Parser;

/*
* A Tokenizer will take a sequence of characters and find the first token pattern from a list of patterns.
*/
public class Tokenizer {

    private final List<TokenPattern> patterns;

    private StringBuilder partial;
    private TokenPattern pattern;

    public Tokenizer(TokenPattern... patterns) {
        this.patterns = Arrays.asList(patterns);

        // System.out.println("Tokenizer created with the following patterns:");
        // this.patterns.stream().map(TokenPattern::getPattern).forEach(System.out::println);
    }

    public Optional<TokenPattern> findPattern() {
        return this.patterns.stream().filter(p -> p.matches(partial)).findFirst();
    }

    public boolean hasMatch() {
        return this.pattern != null;
    }

    public Token getToken() throws IllegalTokenException {
        if (this.partial.length() == 0) {
            return null;
        }

        if (!this.hasMatch()) {
            throw new IllegalTokenException(this.partial.toString());
        }

        return new Token(this.partial.toString(), this.pattern);
    }

    public boolean allowWhitespace() {
        return this.pattern != null ? this.pattern.allowWhitespace() : false; 
    }

    public void append(char c) {
        this.partial.append(c);
        this.pattern = this.findPattern().orElse(null);
    }

    public char popLast() {
       char c = this.partial.charAt(this.partial.length() - 1);
       this.partial.deleteCharAt(this.partial.length() - 1);
       this.pattern = this.findPattern().orElse(null);
       return c;
    }

    public void reset() {
        this.partial = new StringBuilder();
        this.pattern = null;
    }

    public class IllegalTokenException extends ParsingException {
        private String literal;

        public IllegalTokenException(String literal) {
            this.literal = literal;
        }

        public String toString() {
            return literal + " is not a valid token";
        }
    }
}
