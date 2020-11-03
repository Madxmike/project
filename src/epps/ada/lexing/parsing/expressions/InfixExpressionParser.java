/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.expressions;

import lexing.ast.Expression;
import lexing.ast.InfixExpression;
import lexing.ast.OperatorExpression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * A PrefixExpressionParser recursively parses out expressions to the right of it until all expressions are parsed,
 * but also has an expression to its left
 */
public class InfixExpressionParser implements ExpressionParser<InfixExpression> {

    @Override
    public InfixExpression parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        throw new ParsingException();
    }

    @Override
    public InfixExpression parse(Parser parser, TokenStream tokenStream, Expression left) throws ParsingException {
        InfixExpression prefix = new InfixExpression(left, new OperatorExpression(tokenStream.currentPattern()));

        tokenStream.advance();
        prefix.setRight(parser.parseExpression(parser.currentPrecedence()));
        return prefix;
    }

}
