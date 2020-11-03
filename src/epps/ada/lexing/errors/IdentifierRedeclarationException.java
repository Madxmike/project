package lexing.errors;

import lexing.ast.IdentifierExpression;

@SuppressWarnings("serial")
public class IdentifierRedeclarationException extends ParsingException {

    private IdentifierExpression identifier;

    public IdentifierRedeclarationException(IdentifierExpression identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier + " was already declared in this scope";
    }


    
}
