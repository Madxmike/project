package lexing.errors;

import lexing.ast.Statement;

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
