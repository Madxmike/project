/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing;

import grammar.Token;
import grammar.TokenPattern;
import lexing.Scanner;
import lexing.errors.ExpectedSymbolException;
import lexing.errors.ParsingException;
import lexing.errors.UnexpectedSymbolException;

/**
 * A TokenStream represents a stream of parsed tokens from a scanner.
 */
public class TokenStream {

    /**
     * The scanner the TokenStream reads from
     */
    private Scanner tokenScanner;

    /**
     * The currently processed token
     */
    private Token current;

    /**
     * The next token to be processed;
     */
    private Token next;

    /**
     * Creates a new TokenStream that reads from the provided scanner
     */
    public TokenStream(Scanner tokenScanner) throws ParsingException {
        this.tokenScanner = tokenScanner;

        this.advance();
        this.advance();
    }


    /**
     * Advances the token stream by one token
     */
    public void advance() throws ParsingException {
        this.current = this.next;
        this.next = this.tokenScanner.nextToken();
    }

    /**
     * Get the pattern of the current token
     * @return The pattern of the current token
     */
    public TokenPattern currentPattern() {
        return this.current != null ? this.current.getPattern() : TokenPattern.EOF;
    }

    /**
     * Get the pattern of the next token
     * @return The pattern of the next token
     */
    public TokenPattern nextPattern() {
        return this.next != null ? this.next.getPattern() : TokenPattern.EOF;
    }

    /**
     * Checks to see if the current token matches the pattern provided
     * @param pattern The pattern to check against
     * @return Does the current token match the provided pattern
     */
    public boolean isCurrent(TokenPattern pattern) {
        return this.currentPattern() == pattern;
    }


     /**
     * Checks to see if the next token matches the pattern provided
     * @param pattern The pattern to check against
     * @return Does the next token match the provided pattern
     */
    public boolean isNext(TokenPattern pattern) {
        return this.nextPattern() == pattern;
    }

    /**
     * Requires that the current token matches the provided pattern.
     * If it does not then an ExpectedSymbolException is thrown.
     */
    public void currentMustBe(TokenPattern pattern) throws ExpectedSymbolException {
        if (!this.isCurrent(pattern)) {
            throw new ExpectedSymbolException(pattern, this.currentLiteral());
        }
    }

    /**
     * Requires that the next token matches the provided pattern.
     * If it does not then an ExpectedSymbolException is thrown.
     */
    public void nextMustBe(TokenPattern pattern) throws ExpectedSymbolException {
        if (!this.isNext(pattern)) {
            throw new ExpectedSymbolException(pattern, this.nextLiteral());
        }
    }

    /**
     * Requires that the current token DOES NOT match the provided pattern.
     * If it does not then an UnexpectedSymbolException is thrown.
     */
    public void currentMustNotBe(TokenPattern pattern) throws UnexpectedSymbolException {
        if (this.isCurrent(pattern)) {
            throw new UnexpectedSymbolException(this.currentLiteral());
        }
    }

    /**
     * Requires that the next token DOES NOT match the provided pattern.
     * If it does not then an UnexpectedSymbolException is thrown.
     */
    public void nextMustNotBe(TokenPattern pattern) throws UnexpectedSymbolException {
        if (this.isNext(pattern)) {
            throw new UnexpectedSymbolException(this.nextLiteral());
        }
    }

    /**
     * @return The literal of the current token
     */
    public String currentLiteral() {
        return this.current != null ? this.current.getLiteral() : "";
    }

    /**
     * @return The literal of the next token
     */
    public String nextLiteral() {
        return this.next != null ? this.next.getLiteral() : "";
    }

}
