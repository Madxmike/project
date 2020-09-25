package grammar;

import java.util.regex.Pattern;

public enum TokenPattern {
    LBRACE("\\{", "{"), RBRACE("\\}", "}"), PROCEDURE("PROCEDURE", "procedure");

    private Pattern pattern;
    private String resolveTo;

    private TokenPattern(String regex, String resolveTo) {
        this.pattern = Pattern.compile(regex);
        this.resolveTo = resolveTo;
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
}
