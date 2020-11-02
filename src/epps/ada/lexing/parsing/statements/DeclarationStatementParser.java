package lexing.parsing.statements;

import java.util.ArrayList;
import java.util.List;

import grammar.TokenPattern;
import lexing.ast.DeclarationStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.ast.Type;
import lexing.errors.MismatchedDeclarationsException;
import lexing.errors.ParsingException;
import lexing.errors.UnexpectedSymbolException;
import lexing.parsing.IdentifierTable;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class DeclarationStatementParser implements StatementParser<DeclarationStatement> {

    private List<IdentifierExpression> identifiers;

    protected DeclarationStatementParser(List<IdentifierExpression> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public DeclarationStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.currentMustNotBe(TokenPattern.SYMBOL_ASSIGNMENT);
        tokenStream.currentMustNotBe(TokenPattern.SYMBOL_SEMICOLON);

        Type type = new Type(tokenStream.currentLiteral());
        tokenStream.advance();

        for (IdentifierExpression identifier : identifiers) {
            IdentifierTable.setTypeForIdentifier(identifier, type);
        }

        if (tokenStream.isCurrent(TokenPattern.SYMBOL_ASSIGNMENT)) {
            tokenStream.advance();
            List<Expression> defaultValues = parser.parseExpressionList();

            if (identifiers.size() != defaultValues.size()) {
                throw new MismatchedDeclarationsException(identifiers.size(), defaultValues.size());
            }
            return new DeclarationStatement(identifiers, type, defaultValues);
        }

        //Special case for formal parameters declaration
        //If we detect we are the last declaration in a procedure/function's formal parameters
        //then skip the semi-colon check
        if(tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_RIGHT) && tokenStream.isNext(TokenPattern.KEYWORD_IS)) {
            return new DeclarationStatement(identifiers, type, new ArrayList<>());
        }

        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();
        return new DeclarationStatement(identifiers, type, new ArrayList<>());
    }

}
