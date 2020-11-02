package lexing.parsing.statements;

import java.util.ArrayList;
import java.util.List;

import grammar.TokenPattern;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.ast.IdentifierStatement;
import lexing.errors.ExpectedSymbolException;
import lexing.errors.InvalidExpressionException;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class IdentifierStatementParser implements StatementParser<IdentifierStatement> {

    @Override
    public IdentifierStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        List<IdentifierExpression> identifiers = this.parseIdentifierList(parser, tokenStream);
        if(tokenStream.isCurrent(TokenPattern.SYMBOL_COLON)) {
            tokenStream.advance();
            return new DeclarationStatementParser(identifiers).parse(parser, tokenStream);
        } else if(tokenStream.isCurrent(TokenPattern.SYMBOL_ASSIGNMENT)) {
            tokenStream.advance();
            return new AssignmentStatementParser(identifiers).parse(parser, tokenStream);
        }

        throw new ExpectedSymbolException(TokenPattern.SYMBOL_COLON, tokenStream.currentLiteral());
    }    

    private List<IdentifierExpression> parseIdentifierList(Parser parser, TokenStream tokenStream) throws ParsingException {
        List<Expression> expressions = parser.parseExpressionList();

        List<IdentifierExpression> identifiers = new ArrayList<>();
        for (Expression expression : expressions) {
            if(expression instanceof IdentifierExpression) {
                identifiers.add((IdentifierExpression) expression);
            } else {
                throw new InvalidExpressionException(expression);
            }
        }
        return identifiers;
    }
}
