package lexing.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grammar.TokenPattern;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;
import lexing.ast.NumeralExpression;
import lexing.ast.Program;
import lexing.ast.Statement;
import lexing.ast.Type;
import lexing.errors.ParsingException;
import lexing.parsing.expressions.ExpressionParser;
import lexing.parsing.expressions.GroupedExpressionParser;
import lexing.parsing.expressions.InfixExpressionParser;
import lexing.parsing.expressions.PrefixExpressionParser;
import lexing.parsing.statements.BeginStatementParser;
import lexing.parsing.statements.IdentifierStatementParser;
import lexing.parsing.statements.ProcedureStatementParser;
import lexing.parsing.statements.StatementParser;

public class Parser {

    private TokenStream tokenStream;
    private IdentifierTable IdentifierTable;

    private Map<TokenPattern, StatementParser<?>> statementParsers;
    private Map<TokenPattern, ExpressionParser<?>> prefixParsers;
    private Map<TokenPattern, InfixExpressionParser> infixParsers;
    private Map<TokenPattern, Integer> precedences;

    private boolean atTopLevel;

    public Parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
        this.IdentifierTable = new IdentifierTable();

        this.statementParsers = new HashMap<>();
        this.statementParsers.put(TokenPattern.IDENTIFIER, new IdentifierStatementParser());
        this.statementParsers.put(TokenPattern.KEYWORD_PROCEDURE, new ProcedureStatementParser());
        this.statementParsers.put(TokenPattern.KEYWORD_BEGIN, new BeginStatementParser());

        this.prefixParsers = new HashMap<>();
        this.prefixParsers.put(TokenPattern.IDENTIFIER, (p, s) -> {
            return new IdentifierExpression(s.currentLiteral());
        });
        this.prefixParsers.put(TokenPattern.NUMERAL, (p, s) -> {
            return new NumeralExpression(Float.parseFloat(s.currentLiteral().replaceAll("_", "")));
        });
        this.prefixParsers.put(TokenPattern.SYMBOL_MINUS, new PrefixExpressionParser());
        this.prefixParsers.put(TokenPattern.SYMBOL_PAREN_LEFT, new GroupedExpressionParser());

        InfixExpressionParser infixExpressionParser = new InfixExpressionParser();
        this.infixParsers = new HashMap<>();
        this.infixParsers.put(TokenPattern.SYMBOL_PLUS, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_MINUS, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_DIVISION, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_MULTIPLICATION, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_EQUALITY, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_INEQUALITY, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_LESSTHAN, infixExpressionParser);
        this.infixParsers.put(TokenPattern.SYMBOL_GREATERTHAN, infixExpressionParser);

        this.precedences = new HashMap<>();
        this.precedences.put(TokenPattern.SYMBOL_EQUALITY, 1);
        this.precedences.put(TokenPattern.SYMBOL_INEQUALITY, 1);
        this.precedences.put(TokenPattern.SYMBOL_LESSTHAN, 2);
        this.precedences.put(TokenPattern.SYMBOL_GREATERTHAN, 2);
        this.precedences.put(TokenPattern.SYMBOL_PLUS, 3);
        this.precedences.put(TokenPattern.SYMBOL_MINUS, 3);
        this.precedences.put(TokenPattern.SYMBOL_DIVISION, 4);
        this.precedences.put(TokenPattern.SYMBOL_MULTIPLICATION, 4);

    }

    public Program parse() throws RuntimeException {

        Program program = new Program();

        try {
            while (!this.tokenStream.isCurrent(TokenPattern.EOF)) {
                this.atTopLevel = true;
                Statement statement = this.parseStatement();
                if (statement != null) {
                    program.addStatement(statement);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return program;
    }

    public Statement parseStatement() throws ParsingException {
        StatementParser<?> statementParser = this.statementParsers.get(this.tokenStream.currentPattern());

        if (statementParser != null) {
            if ((atTopLevel && !statementParser.allowedAtTopLevel())
                    || (!atTopLevel && !statementParser.allowedBelowTopLevel())) {
                throw new ParsingException("statement not allowed at this level");
            }

            // System.out.println("parser: " + statementParser.getClass().getSimpleName() +
            // " for: " + this.tokenStream.currentLiteral());
            this.atTopLevel = false;
            Statement statement = statementParser.parse(this, this.tokenStream);
            if (statement == null) {
                throw new ParsingException("invalid statement");
            }
            return statement;
        }

        return null;
    }

    public Expression parseExpression(int precedence) throws ParsingException {
        ExpressionParser<?> prefixParser = this.prefixParsers.get(this.tokenStream.currentPattern());
        if (prefixParser == null) {
            return null;
        }

        Expression leftExpression = prefixParser.parse(this, this.tokenStream);
        while (!this.tokenStream.isNext(TokenPattern.SYMBOL_SEMICOLON) && precedence < this.nextPrecedence()) {
            InfixExpressionParser infixParser = this.infixParsers.get(this.tokenStream.nextPattern());
            if (infixParser == null) {
                return leftExpression;
            }

            this.tokenStream.advance();
            leftExpression = infixParser.parse(this, this.tokenStream, leftExpression);
        }

        return leftExpression;
    }

    public List<Expression> parseExpressionList() throws ParsingException {
        List<Expression> expressions = new ArrayList<>();

        do {
            if (this.tokenStream.isCurrent(TokenPattern.SYMBOL_COMMA)) {
                this.tokenStream.advance();
            }
            Expression expression = this.parseExpression(0);
            if (expression != null) {
                expressions.add(expression);
            }
            this.tokenStream.advance();

        } while (this.tokenStream.isCurrent(TokenPattern.SYMBOL_COMMA));

        return expressions;
    }

    public int currentPrecedence() {
        return this.precedences.getOrDefault(this.tokenStream.currentPattern(), 0);
    }

    private int nextPrecedence() {
        return this.precedences.getOrDefault(this.tokenStream.nextPattern(), 0);
    }

    public IdentifierTable getIdentifierTable() {
        return IdentifierTable;
    }
}