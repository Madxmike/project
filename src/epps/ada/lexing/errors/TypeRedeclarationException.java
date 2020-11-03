/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */

package lexing.errors;

import lexing.ast.Type;

/**
 * A TypeRedeclarationException is thrown when the parser tries to redeclare an identifier with a new type
 */
@SuppressWarnings("serial")
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