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
import lexing.ast.Statement;
import lexing.errors.InvalidExpressionException;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class BeginStatementParser implements StatementParser<BeginStatement> {

    @Override
    public BeginStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.currentMustBe(TokenPattern.KEYWORD_BEGIN);
        tokenStream.advance();
        List<Statement> statements = new ArrayList<>();

        while (!tokenStream.isCurrent(TokenPattern.KEYWORD_END)) {
            Statement statement = parser.parseStatement();
            if (statement instanceof DeclarationStatement) {
                throw new ParsingException("declaration statement now allowed in begin block");
            }
            statements.add(statement);
        }

        tokenStream.currentMustBe(TokenPattern.KEYWORD_END);
        tokenStream.advance();

        Expression belongsTo = parser.parseExpression(0);
        if (belongsTo instanceof IdentifierExpression) {
            tokenStream.nextMustBe(TokenPattern.SYMBOL_SEMICOLON);
            tokenStream.advance();
            return new BeginStatement(statements, (IdentifierExpression) belongsTo);
        }

        throw new InvalidExpressionException(belongsTo);
    }

}
