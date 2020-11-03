/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.expressions;

import grammar.TokenPattern;
import lexing.ast.Expression;
import lexing.ast.GroupedExpression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * A GroupedExpressionParser parses out an expression that is between a set of parentheses
 */
public class GroupedExpressionParser implements ExpressionParser<GroupedExpression> {

    @Override
    public GroupedExpression parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.currentMustBe(TokenPattern.SYMBOL_PAREN_LEFT);

        tokenStream.advance();

        Expression expression = parser.parseExpression(0);
        tokenStream.nextMustBe(TokenPattern.SYMBOL_PAREN_RIGHT);
        tokenStream.advance();
        return new GroupedExpression(expression);
    }

}
