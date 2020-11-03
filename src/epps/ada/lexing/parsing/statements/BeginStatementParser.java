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

/**
 * A BeginStatementParser parses out a begin statement block.
 * All statements from "begin" to "end" are parsed.
 */
public class BeginStatementParser implements StatementParser<BeginStatement> {

    @Override
    public BeginStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        // Start at the "begin" keyword
        tokenStream.currentMustBe(TokenPattern.KEYWORD_BEGIN);
        tokenStream.advance();

        List<Statement> statements = new ArrayList<>();
        
        // Parse out statements until we reach the end
        while (!tokenStream.isCurrent(TokenPattern.KEYWORD_END)) {
            Statement statement = parser.parseStatement();

            // Disallow declarations here
            if (statement instanceof DeclarationStatement) {
                throw new ParsingException("declaration statement now allowed in begin block");
            }
            statements.add(statement);
        }

        // We are at the "end" keyword
        tokenStream.currentMustBe(TokenPattern.KEYWORD_END);
        tokenStream.advance();

        // Parse out the identifier to ensure which statement this begin block belongs to
        Expression belongsTo = parser.parseExpression(0);
        if (belongsTo instanceof IdentifierExpression) {
            tokenStream.nextMustBe(TokenPattern.SYMBOL_SEMICOLON);
            tokenStream.advance();
            return new BeginStatement(statements, (IdentifierExpression) belongsTo);
        }

        // If this expression was not an identifier, error out
        throw new InvalidExpressionException(belongsTo);
    }

}
