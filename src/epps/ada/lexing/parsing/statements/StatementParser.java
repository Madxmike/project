/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing.statements;

import lexing.ast.Statement;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

/**
 * A statement parser is a parser that will parse out a given statement and its subexpressions.
 */
@FunctionalInterface
public interface StatementParser<T extends Statement> {

    T parse(Parser parser, TokenStream tokenStream) throws ParsingException;

    default boolean allowedAtTopLevel() {
        return false;
    }

    default boolean allowedBelowTopLevel() {
        return true;
    }
}
