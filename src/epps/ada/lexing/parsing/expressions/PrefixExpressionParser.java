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
