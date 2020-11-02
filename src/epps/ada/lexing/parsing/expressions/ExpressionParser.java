package lexing.parsing.expressions;

import lexing.ast.Expression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

@FunctionalInterface
public interface ExpressionParser<T extends Expression> {
    
    T parse(Parser parser, TokenStream tokenStream) throws ParsingException;

    default T parse(Parser parser, TokenStream tokenStream, Expression left) throws ParsingException {
        return parse(parser, tokenStream);
    }
}
