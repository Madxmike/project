package lexing.parsing.expressions;

import java.util.List;

import grammar.TokenPattern;
import lexing.ast.CallExpression;
import lexing.ast.Expression;
import lexing.ast.GroupedExpression;
import lexing.ast.IdentifierExpression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class CallExpressionParser implements ExpressionParser<CallExpression> {

    @Override
    public CallExpression parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.currentMustBe(TokenPattern.IDENTIFIER);
        Expression identifierExpression = parser.parseExpression(0);
        tokenStream.advance();

        tokenStream.currentMustBe(TokenPattern.SYMBOL_PAREN_LEFT);
        tokenStream.advance();

        List<Expression> args = parser.parseExpressionList();
        tokenStream.currentMustBe(TokenPattern.SYMBOL_PAREN_RIGHT);
        tokenStream.advance();

        if(identifierExpression instanceof IdentifierExpression) {
            return new CallExpression((IdentifierExpression) identifierExpression, args);
        }
        return null;
    }


}

