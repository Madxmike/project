/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.statements;

import java.util.List;

import grammar.TokenPattern;
import lexing.ast.AssignmentStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.errors.MismatchedDeclarationsException;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * An AssignmentStatementParser parses out an assignment statement of the form:
 * <identifier list> := <expression list>
 */
public class AssignmentStatementParser implements StatementParser<AssignmentStatement> {

    private List<IdentifierExpression> identifiers;

    public AssignmentStatementParser(List<IdentifierExpression> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public AssignmentStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        // We are assuming that we are starting at the expression list since we have the identifier list already
        tokenStream.currentMustNotBe(TokenPattern.SYMBOL_COLON);
        tokenStream.currentMustNotBe(TokenPattern.SYMBOL_SEMICOLON);

        // Parse out the expressions
        List<Expression> values = parser.parseExpressionList();

        // If the identifiers dont match the expressions then error out
        if (identifiers.size() != values.size()) {
            throw new MismatchedDeclarationsException(identifiers.size(), values.size());
        }

        // Must end with a semi-colon
        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();
        return new AssignmentStatement(identifiers, values);
    }

}
