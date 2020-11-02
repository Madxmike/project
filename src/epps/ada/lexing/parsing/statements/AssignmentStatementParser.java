package lexing.parsing.statements;

import java.util.List;

import grammar.TokenPattern;
import lexing.ast.AssignmentStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.errors.MismatchedDeclarationsException;
import lexing.errors.ParsingException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class AssignmentStatementParser implements StatementParser<AssignmentStatement> {

    private List<IdentifierExpression> identifiers;

    public AssignmentStatementParser(List<IdentifierExpression> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public AssignmentStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.currentMustNotBe(TokenPattern.SYMBOL_COLON);
        tokenStream.currentMustNotBe(TokenPattern.SYMBOL_SEMICOLON);

        List<Expression> values = parser.parseExpressionList();

        if (identifiers.size() != values.size()) {
            throw new MismatchedDeclarationsException(identifiers.size(), values.size());
        }
        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();
        return new AssignmentStatement(identifiers, values);
    }

}
