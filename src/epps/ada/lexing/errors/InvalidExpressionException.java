package lexing.errors;

import lexing.ast.Expression;

@SuppressWarnings("serial")
public class InvalidExpressionException extends ParsingException {

    private Expression expression;

    public InvalidExpressionException(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression + " is an invalid expression";
    }
}
