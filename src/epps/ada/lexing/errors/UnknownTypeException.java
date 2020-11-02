package lexing.errors;

import lexing.ast.Type;

@SuppressWarnings("serial")
public class UnknownTypeException extends ParsingException {
    private String identifierValue;
    private String typeValue;

    public UnknownTypeException(String identifierValue, Type type) {
        this.identifierValue = identifierValue;
        this.typeValue = type.getValue();
    }

    @Override
    public String toString() {
        return identifierValue + identifierValue + " declared as type " + typeValue + ", but " + typeValue
                + " is not a known type!";
    }

}