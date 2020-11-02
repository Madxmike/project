package lexing.ast;

public class IdentifierExpression implements Expression {
    private String value;

    public IdentifierExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof IdentifierExpression)) {
            return false;
        }

        IdentifierExpression o = (IdentifierExpression) other;
        return this.value.equals(o.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
