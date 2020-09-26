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
        return this.getLiteral();
    }

    public String toString() {
        return this.pattern.resolve() + "(" + literal + ")";
    }

}
