package lexing.parsing.expressions;

import lexing.ast.Expression;
import lexing.ast.InfixExpression;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class InfixExpressionParser implements ExpressionParser<InfixExpression> {

    @Override
    public InfixExpression parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        throw new ParsingException();
    }

    @Override
    public InfixExpression parse(Parser parser, TokenStream tokenStream, Expression left) throws ParsingException {
        InfixExpression prefix = new InfixExpression(left, tokenStream.currentLiteral());

        tokenStream.advance();
        prefix.setRight(parser.parseExpression(parser.currentPrecedence()));
        return prefix;
    }

}
