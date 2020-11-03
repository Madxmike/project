/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.errors;

import grammar.TokenPattern;

/**
 * An ExceptedSymbolException is thrown when the parser does not encounter the token that it expects
 */
@SuppressWarnings("serial")
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
