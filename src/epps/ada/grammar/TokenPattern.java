package grammar;

import java.util.regex.Pattern;

public enum TokenPattern {
    SYMBOL_PLUS("\\+", "+"),
    SYMBOL_MINUS("-", "-"),
    SYMBOL_MULTIPLICATION("\\*", "*"),
    SYMBOL_DIVISION("/", "/"),
    SYMBOL_MODULO("%", "%"),
    SYMBOL_EQUALS("=", "="),
    SYMBOL_EQUALITY("==", "=="),
    SYMBOL_BANG("!", "!"),
    SYMBOL_SEMICOLON(";", ";"),
    SYMBOL_BRACKET_LEFT("\\[", "["),
    SYMBOL_BRACKET_RIGHT("\\]", "]"),
    SYMBOL_BRACKET_CURLY_LEFT("\\{", "{"),
    SYMBOL_BRACKET_CURLY_RIGHT("\\}", "}"),
    SYMBOL_PAREN_LEFT("\\(", "("),
    SYMBOL_PAREN_RIGHT("\\)", ")"),
    SYMBOL_DOT("\\.", "."),
    SYMBOL_COMMA(",", ","),

    KEYWORD_WITH("^with$", "with"),
    KEYWORD_USE("^use$", "use"),
    KEYWORD_PROCEDURE("^procedure$", "procedure"),
    KEYWORD_IS("^is$", "is"),
    KEYWORD_BEGIN("^begin$", "begin"),
    KEYWORD_END("^end$", "end"),

    NUMERAL("^[0-9]$", "NUMERAL"),
    LITERAL_CHARACTER("^\'(.)\'", "character_literal"),
    LITERAL_STRING("^\"([^\"]+)([\"]*)$", "string_literal", true),
    IDENTIFIER("^([A-Za-z]+)(\\w+)$", "indentifier");
    private Pattern pattern;
    private String resolveTo;
    private boolean allowWhitespace;
    private TokenPattern(String regex, String resolveTo) {
        this.pattern = Pattern.compile(regex);
        this.resolveTo = resolveTo;
    }

    private TokenPattern(String regex, String resolveTo, boolean allowWhitespace) {
        this(regex, resolveTo);
        this.allowWhitespace = allowWhitespace;
    }

    protected Pattern getPattern() {
        return this.pattern;
    }

    public boolean matches(CharSequence sequence) {
        return this.pattern.matcher(sequence).matches();
    }

    public String resolve() {
        return this.resolveTo;
    }

    protected boolean allowWhitespace() {
        return this.allowWhitespace;
    }
}
