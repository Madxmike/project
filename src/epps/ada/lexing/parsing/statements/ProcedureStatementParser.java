
/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.statements;

import java.util.ArrayList;
import java.util.List;

import grammar.TokenPattern;
import lexing.ast.BeginStatement;
import lexing.ast.DeclarationStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.ast.ProcedureStatement;
import lexing.ast.Statement;
import lexing.errors.ExpectedSymbolException;
import lexing.errors.InvalidExpressionException;
import lexing.errors.ParsingException;
import lexing.errors.UnexpectedStatementException;
import lexing.parsing.IdentifierTable;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * A ProcedureStatementParser parses out a Procedure Statement
 */
public class ProcedureStatementParser implements StatementParser<ProcedureStatement> {

    @Override
    public ProcedureStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        // We are currently at the "procedure" token, ensure that the next token is a
        // identifier
        // so that the procedure has a name.
        tokenStream.nextMustBe(TokenPattern.IDENTIFIER);
        tokenStream.advance();

        // Parse out the next expression
        Expression expression = parser.parseExpression(0);

        // If the expression isnt an identifier for some reason, throw an error
        if (!(expression instanceof IdentifierExpression)) {
            throw new InvalidExpressionException(expression);
        }

        IdentifierExpression procedureName = (IdentifierExpression) expression;

        // Push this scope onto the identifier table
        parser.getIdentifierTable().pushScope(procedureName);

        // Declare the name identifier as a procedure type
        parser.getIdentifierTable().declareIdentifier(procedureName, IdentifierTable.BUILT_IN_TYPE_PROCEDURE);

        tokenStream.advance();

        // Parse out the list of formal parameters in the parentheses, if there are any
        List<DeclarationStatement> parameters = new ArrayList<>();
        if (tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_LEFT)) {
            parameters = parseDeclarationStatementList(parser, tokenStream);
            // The last formal parameter must not end with a semicolon
            tokenStream.currentMustNotBe(TokenPattern.SYMBOL_SEMICOLON);
            tokenStream.currentMustBe(TokenPattern.SYMBOL_PAREN_RIGHT);
            tokenStream.advance();
        }

        // Start parsing the "is" block
        tokenStream.currentMustBe(TokenPattern.KEYWORD_IS);

        // If we arn't at the begin block then we are declaring local variables
        List<DeclarationStatement> locals = new ArrayList<>();
        if (!tokenStream.isNext(TokenPattern.KEYWORD_BEGIN)) {
            locals = parseDeclarationStatementList(parser, tokenStream);
            tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        }

        tokenStream.advance();

        // Start parsing the "begin" block
        tokenStream.currentMustBe(TokenPattern.KEYWORD_BEGIN);
        BeginStatement begin = (BeginStatement) parser.parseStatement();

        // If the identifier at the end of the begin block is not the same as the
        // procedure's identifier
        // then throw an error.
        if (!begin.getBelongsTo().equals(procedureName)) {
            throw new ParsingException("expected to find " + procedureName + ", but found " + begin.getBelongsTo());
        }

        // Entire statement must end with a semi-colon
        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();

        // Pop the procedure scope off
        parser.getIdentifierTable().popScope();
        return new ProcedureStatement(procedureName, parameters, locals, begin);
    }

    /**
     * Parses out a list of declaration statements
     * 
     * @param parser      The parser to use
     * @param tokenStream The token stream to use
     * @return A list of declaration statements
     * @throws ParsingException Thrown if any errors occur while parsing the
     *                          declaration statements
     */
    private List<DeclarationStatement> parseDeclarationStatementList(Parser parser, TokenStream tokenStream)
            throws ParsingException {

        List<DeclarationStatement> declarations = new ArrayList<>();
        do {
            tokenStream.advance();

            // Attempt to parse a declaration statement
            try {
                Statement statement = parser.parseStatement();
                if (statement instanceof DeclarationStatement) {
                    declarations.add((DeclarationStatement) statement);
                } else if (statement != null) {
                    // If the statement is not a declaration statement we are in an error state
                    throw new UnexpectedStatementException(statement);
                }
            } catch (ExpectedSymbolException e) {
                // This is a special case such that the last declaration statement does not require a semi-colon
                if (!tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_RIGHT)) {
                    throw e;
                }
            }
            // Continue as long as we find a semi-colon ending the current declaration statement
            // followed by an identifier starting the next one
        } while (tokenStream.isCurrent(TokenPattern.SYMBOL_SEMICOLON) && tokenStream.isNext(TokenPattern.IDENTIFIER));

        return declarations;
    }

    @Override
    public boolean allowedAtTopLevel() {
        return true;
    }

    @Override
    public boolean allowedBelowTopLevel() {
        return false;
    }
}
