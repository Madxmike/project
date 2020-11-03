/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.ast;

/**
 * An IdentifierExpression defines an expression that is simply an identifier
 */
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
