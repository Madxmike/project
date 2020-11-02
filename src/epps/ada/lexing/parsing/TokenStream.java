package lexing.parsing;

import java.io.IOException;

import grammar.Token;
import grammar.TokenPattern;
import grammar.Tokenizer.IllegalTokenException;
import lexing.Scanner;
import lexing.errors.ExpectedSymbolException;
import lexing.errors.ParsingException;
import lexing.errors.UnexpectedSymbolException;

public class TokenStream {

    private Scanner tokenScanner;
    private Token current;
    private Token next;

    public TokenStream(Scanner tokenScanner) throws ParsingException {
        this.tokenScanner = tokenScanner;

        this.advance();
        this.advance();
    }

    public void advance() throws ParsingException {
        this.current = this.next;
        this.next = this.tokenScanner.nextToken();
    }

    public TokenPattern currentPattern() {
        return this.current != null ? this.current.getPattern() : TokenPattern.EOF;
    }

    public TokenPattern nextPattern() {
        return this.next != null ? this.next.getPattern() : TokenPattern.EOF;
    }

    public boolean isCurrent(TokenPattern pattern) {
        return this.currentPattern() == pattern;
    }

    public boolean isNext(TokenPattern pattern) {
        return this.nextPattern() == pattern;
    }

    public void currentMustBe(TokenPattern pattern) throws ExpectedSymbolException {
        if(!this.isCurrent(pattern)) {
            throw new ExpectedSymbolException(pattern, this.currentLiteral());
        }
    }

    public void nextMustBe(TokenPattern pattern) throws ExpectedSymbolException {
        if(!this.isNext(pattern)) {
            throw new ExpectedSymbolException(pattern, this.nextLiteral());
        }
    }

    public void currentMustNotBe(TokenPattern pattern) throws UnexpectedSymbolException {
        if(this.isCurrent(pattern)) {
            throw new UnexpectedSymbolException(this.currentLiteral());
        }
    }

    public void nextMustNotBe(TokenPattern pattern) throws UnexpectedSymbolException {
        if(this.isNext(pattern)) {
            throw new UnexpectedSymbolException(this.nextLiteral());
        }
    }

    public String currentLiteral() {
        return this.current != null ? this.current.getLiteral() : "";
    }
     
    public String nextLiteral() {
        return this.next != null ? this.next.getLiteral() : "";
    }



}
