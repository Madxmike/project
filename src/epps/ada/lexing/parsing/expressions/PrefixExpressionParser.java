/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.expressions;

import lexing.ast.PrefixExpression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class PrefixExpressionParser implements ExpressionParser<PrefixExpression> {

    @Override
    public PrefixExpression parse(Parser parser, TokenStream tokenStream) throws ParsingException {

        PrefixExpression prefix = new PrefixExpression(tokenStream.currentLiteral());

        tokenStream.advance();
        prefix.setRight(parser.parseExpression(parser.currentPrecedence()));
        return prefix;
    }

}
