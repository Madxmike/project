
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

public class ProcedureStatementParser implements StatementParser<ProcedureStatement> {

    @Override
    public ProcedureStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.advance();
        tokenStream.currentMustBe(TokenPattern.IDENTIFIER);

        Expression expression = parser.parseExpression(0);
        if (!(expression instanceof IdentifierExpression)) {
            throw new InvalidExpressionException(expression);
        }

        IdentifierExpression procedureName = (IdentifierExpression) expression;
        parser.getIdentifierTable().pushScope(procedureName);
        parser.getIdentifierTable().declareIdentifier(procedureName, IdentifierTable.BUILT_IN_TYPE_PROCEDURE);

        tokenStream.advance();

        List<DeclarationStatement> parameters = new ArrayList<>();
        if (tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_LEFT)) {
            parameters = parseDeclarationStatementList(parser, tokenStream);
            tokenStream.currentMustNotBe(TokenPattern.SYMBOL_SEMICOLON);
            tokenStream.currentMustBe(TokenPattern.SYMBOL_PAREN_RIGHT);
            tokenStream.advance();
        }

        tokenStream.currentMustBe(TokenPattern.KEYWORD_IS);

        // If we arn't at the begin block then we are declaring local variables
        List<DeclarationStatement> locals = new ArrayList<>();

        if (!tokenStream.isNext(TokenPattern.KEYWORD_BEGIN)) {
            locals = parseDeclarationStatementList(parser, tokenStream);
            tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        }

        tokenStream.advance();

        tokenStream.currentMustBe(TokenPattern.KEYWORD_BEGIN);
        BeginStatement begin = (BeginStatement) parser.parseStatement();
        if (!begin.getBelongsTo().equals(procedureName)) {
            throw new ParsingException("expected to find " + procedureName + ", but found " + begin.getBelongsTo());
        }
        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();

        parser.getIdentifierTable().popScope();
        return new ProcedureStatement(procedureName, parameters, locals, begin);
    }

    private List<DeclarationStatement> parseDeclarationStatementList(Parser parser, TokenStream tokenStream)
            throws ParsingException {

        List<DeclarationStatement> declarations = new ArrayList<>();
        do {
            tokenStream.advance();

            try {
                Statement statement = parser.parseStatement();
                if (statement instanceof DeclarationStatement) {
                    declarations.add((DeclarationStatement) statement);
                } else if (statement != null) {
                    throw new UnexpectedStatementException(statement);
                }
            } catch (ExpectedSymbolException e) {
                if (!tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_RIGHT)) {
                    throw e;
                }
            }
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
