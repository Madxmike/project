package lexing.errors;

/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */

import lexing.ast.IdentifierExpression;

/**
 * An IdentifierReclarationException is thrown when the parser encounters an identifier that has already been defined in this scope
 */
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
