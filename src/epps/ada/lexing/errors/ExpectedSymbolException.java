package lexing.errors;

import grammar.Token;
import grammar.TokenPattern;

public class ExpectedSymbolException extends ParsingException {

    private TokenPattern pattern;
    private String found;

    public ExpectedSymbolException(TokenPattern pattern, String found) {
        this.pattern = pattern;
        this.found = found;
    }

    public TokenPattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "expected " + pattern.resolve() + "  but found " + found;
    }

    
}
