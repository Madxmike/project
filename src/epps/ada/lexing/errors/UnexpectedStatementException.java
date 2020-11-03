/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */

package lexing.errors;

import lexing.ast.Statement;

@SuppressWarnings("serial")
public class UnexpectedStatementException extends ParsingException {

    private Statement statement;

    public UnexpectedStatementException(Statement statement2) {
        this.statement = statement2;
    }

    @Override
    public String toString() {
        return "unexpected statement: " + statement.toString();
    }

}
