package lexing.parsing.statements;

import java.util.ArrayList;
import java.util.List;

import grammar.TokenPattern;
import lexing.ast.BeginStatement;
import lexing.ast.DeclarationStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.ast.ProcedureStatement;
import lexing.ast.Statement;
import lexing.errors.ExpectedSymbolException;
import lexing.errors.InvalidExpressionException;
import lexing.errors.ParsingException;
import lexing.errors.UnexpectedStatementException;
import lexing.parsing.Parser;
import lexing.parsing.TokenStream;

public class ProcedureStatementParser implements StatementParser<ProcedureStatement> {

    @Override
    public ProcedureStatement parse(Parser parser, TokenStream tokenStream) throws ParsingException {
        tokenStream.advance();
        tokenStream.currentMustBe(TokenPattern.IDENTIFIER);

        Expression functionName = parser.parseExpression(0);
        if (!(functionName instanceof IdentifierExpression)) {
            throw new InvalidExpressionException(functionName);
        }
        tokenStream.advance();

        List<DeclarationStatement> parameters = new ArrayList<>();
        if (tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_LEFT)) {
            parameters = parseDeclarationStatementList(parser, tokenStream);
            tokenStream.currentMustNotBe(TokenPattern.SYMBOL_SEMICOLON);
            tokenStream.currentMustBe(TokenPattern.SYMBOL_PAREN_RIGHT);
            tokenStream.advance();
        }

        tokenStream.currentMustBe(TokenPattern.KEYWORD_IS);

        // If we arn't at the begin block then we are declaring local variables
        List<DeclarationStatement> locals = new ArrayList<>();

        if (!tokenStream.isNext(TokenPattern.KEYWORD_BEGIN)) {
            locals = parseDeclarationStatementList(parser, tokenStream);
            tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        }

        tokenStream.advance();

        tokenStream.currentMustBe(TokenPattern.KEYWORD_BEGIN);
        BeginStatement begin = (BeginStatement) parser.parseStatement();
        if (!begin.getBelongsTo().equals(functionName)) {
            throw new ParsingException("expected to find " + functionName + ", but found " + begin.getBelongsTo());
        }
        tokenStream.currentMustBe(TokenPattern.SYMBOL_SEMICOLON);
        tokenStream.advance();

        return new ProcedureStatement((IdentifierExpression) functionName, parameters, locals, begin);
    }

    private List<DeclarationStatement> parseDeclarationStatementList(Parser parser, TokenStream tokenStream)
            throws ParsingException {

        List<DeclarationStatement> declarations = new ArrayList<>();
        do {
            tokenStream.advance();

            try {
                Statement statement = parser.parseStatement();
                if (statement instanceof DeclarationStatement) {
                    declarations.add((DeclarationStatement) statement);
                } else if (statement != null) {
                    throw new UnexpectedStatementException(statement);
                }
            } catch (ExpectedSymbolException e) {
                if (!tokenStream.isCurrent(TokenPattern.SYMBOL_PAREN_RIGHT)) {
                    throw e;
                }
            }
        } while (tokenStream.isCurrent(TokenPattern.SYMBOL_SEMICOLON) && tokenStream.isNext(TokenPattern.IDENTIFIER));

        return declarations;
    }

    @Override
    public boolean allowedAtTopLevel() {
        return true;
    }

    @Override
    public boolean allowedBelowTopLevel() {
        return false;
    }
}
