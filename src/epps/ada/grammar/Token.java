/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package grammar;

/*
* A token is a lexeme that matches a certain pattern.
* For example the string '10' would be a lexeme that matches the digit token pattern.
*/
public class Token {

    private String literal;
    private TokenPattern pattern;

    public Token(String lexeme, TokenPattern pattern) {
        this.literal = lexeme;
        this.pattern = pattern;
    }

    public String getLiteral() {
        return this.literal;
    }

    public TokenPattern getPattern() {
        return this.pattern;
    }

    @Override
    public String toString() {
        return this.pattern.resolve() + "(" + literal + ")";
    }

}
