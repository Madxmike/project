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
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * A expression parser is a parser that will parse out an expression for the current token.
 */
@FunctionalInterface
public interface ExpressionParser<T extends Expression> {

    T parse(Parser parser, TokenStream tokenStream) throws ParsingException;

    default T parse(Parser parser, TokenStream tokenStream, Expression left) throws ParsingException {
        return parse(parser, tokenStream);
    }
}
