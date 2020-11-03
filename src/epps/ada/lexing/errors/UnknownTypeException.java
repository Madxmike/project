package lexing.errors;

import lexing.ast.IdentifierExpression;
import lexing.ast.Type;

@SuppressWarnings("serial")
public class UnknownTypeException extends ParsingException {
    private IdentifierExpression identifier;
    private Type type;

    public UnknownTypeException(IdentifierExpression identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public String toString() {
        return identifier + " declared as type " + type + ", but " + type + " is not a known type!";
    }

}