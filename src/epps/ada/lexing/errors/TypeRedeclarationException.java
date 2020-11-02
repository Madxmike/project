package lexing.errors;

import lexing.ast.Type;

public class TypeRedeclarationException extends ParsingException {
    private String identifierValue;
    private Type originalType;
    private Type newType;

    public TypeRedeclarationException(String identifierValue, Type originalType, Type newType) {
        this.identifierValue = identifierValue;
        this.originalType = originalType;
        this.newType = newType;
    }

    @Override
    public String toString() {
        return identifierValue + "(type " + originalType.getValue() + ") redeclared as type " + newType.getValue();
    }

}