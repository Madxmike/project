package grammar;

/*
* A token is a lexeme that matches a certain pattern.
* For example the string '10' would be a lexeme that matches the digit token pattern.
*/
public class Token {

    private String lexeme;
    private TokenPattern pattern;

    public Token(String lexeme, TokenPattern pattern) {
        this.lexeme = lexeme;
        this.pattern = pattern;
    }

    public String toString() {
        return this.pattern.resolve() + "(" + lexeme + ")";
    }
}
