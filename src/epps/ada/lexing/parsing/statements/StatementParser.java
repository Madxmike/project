package lexing.parsing.statements;

import lexing.ast.Statement;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

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
