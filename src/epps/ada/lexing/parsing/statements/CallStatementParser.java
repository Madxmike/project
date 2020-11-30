package lexing.parsing.statements;

import grammar.TokenPattern;
import lexing.ast.CallExpression;
import lexing.ast.CallStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierStatement;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;
import lexing.parsing.expressions.CallExpressionParser;

public class CallStatementParser extends IdentifierStatementParser {

    @Override
    public IdentifierStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        CallExpressionParser callExpressionParser = new CallExpressionParser();
        CallExpression callExpression = callExpressionParser.parse(parser, tokenStream);

        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();
        if(callExpression != null) {
            return new CallStatement( callExpression);
        }
        return null;
    }
    
}
