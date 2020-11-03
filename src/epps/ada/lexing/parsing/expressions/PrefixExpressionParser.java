/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.expressions;

import lexing.ast.OperatorExpression;
import lexing.ast.PrefixExpression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * A PrefixExpressionParser recursively parses out expressions to the right of it until all expressions are parsed
 */
public class PrefixExpressionParser implements ExpressionParser<PrefixExpression> {

    @Override
    public PrefixExpression parse(Parser parser, TokenStream tokenStream) throws ParsingException {

        PrefixExpression prefix = new PrefixExpression(new OperatorExpression(tokenStream.currentPattern()));

        tokenStream.advance();
        prefix.setRight(parser.parseExpression(parser.currentPrecedence()));
        return prefix;
    }

}
