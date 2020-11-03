/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
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
import lexing.errors.ParsingException;
import lexing.parsing.expressions.ExpressionParser;
import lexing.parsing.expressions.GroupedExpressionParser;
import lexing.parsing.expressions.InfixExpressionParser;
import lexing.parsing.expressions.PrefixExpressionParser;
import lexing.parsing.statements.BeginStatementParser;
import lexing.parsing.statements.IdentifierStatementParser;
import lexing.parsing.statements.ProcedureStatementParser;
import lexing.parsing.statements.StatementParser;

/**
 * The Parser is parser based on Pratt Parsing. It is a recursive descent parser. 
 * The parser will read from a stream of tokens, and match the token with a given set of sub-parsers.
 * These sub-parsers will then parse out the correct statement or expression for the token.
 * Once the token stream is finished the parser will have a completed syntax tree of the program.
 * If any error occurs the parser will stop parsing on the token and return.
 */
public class Parser {

    /**
     * The token stream the parser reads from
     */
    private TokenStream tokenStream;

    /**
    * The identifier table the parser uses to store identifier information
    */
    private IdentifierTable IdentifierTable;

    /**
    * Mapping of Token Patterns to parsers that will parse out statements
    */
    private Map<TokenPattern, StatementParser<?>> statementParsers;

    /**
    * Mapping of Token Patterns to parsers that will parse out most expressions
    */
    private Map<TokenPattern, ExpressionParser<?>> expressionParsers;
    
    /**
    * Mapping of Token Patterns to parsers that will parse out infixed expressions
    */
    private Map<TokenPattern, InfixExpressionParser> infixParsers;

    /**
     * Mapping of Token Patterns to operator precedences. Only contains patterns with precendence.
     * All other tokens are assumed to have the highest precedence.
     */
    private Map<TokenPattern, Integer> precedences;

    /**
     * Determines if we are currently in the global scope.
     * Used to prevent things such as procedure statements being defined inside
     * of other procedure statements.
     */
    private boolean atTopLevel;

    /**
     * Create a new Parser and fill its parser mappings with values
     * @param tokenStream The token stream this parser will read from
     */
    public Parser(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
        this.IdentifierTable = new IdentifierTable();

        this.statementParsers = new HashMap<>();
        this.statementParsers.put(TokenPattern.IDENTIFIER, new IdentifierStatementParser());
        this.statementParsers.put(TokenPattern.KEYWORD_PROCEDURE, new ProcedureStatementParser());
        this.statementParsers.put(TokenPattern.KEYWORD_BEGIN, new BeginStatementParser());

        this.expressionParsers = new HashMap<>();
        this.expressionParsers.put(TokenPattern.IDENTIFIER, (p, s) -> {
            return new IdentifierExpression(s.currentLiteral());
        });
        this.expressionParsers.put(TokenPattern.NUMERAL, (p, s) -> {
            return new NumeralExpression(Float.parseFloat(s.currentLiteral().replaceAll("_", "")));
        });
        this.expressionParsers.put(TokenPattern.SYMBOL_MINUS, new PrefixExpressionParser());
        this.expressionParsers.put(TokenPattern.SYMBOL_PAREN_LEFT, new GroupedExpressionParser());

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

    /**
     * Parse out the entire program. Will run until either EOF is reached or an error occurs
     * @return The parsed Syntax Tree
     * @throws RuntimeException Thrown when any error occurs while parsing
     */
    public Program parse() throws RuntimeException {

        Program program = new Program();

        try {
            // Parse until EOF
            while (!this.tokenStream.isCurrent(TokenPattern.EOF)) {
                // As this is recursive parsing, if we are here we are in the global scope
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

    /**
     * Parses the next statement found. 
     * @return The next statement that was parsed
     * @throws ParsingException Thrown if any error occurs while parsing the statement
     */
    public Statement parseStatement() throws ParsingException {
        // Get the parser for the next statement
        StatementParser<?> statementParser = this.statementParsers.get(this.tokenStream.currentPattern());

        if (statementParser != null) {
            // If we in global scope and the statement is not allowed in global scope (I.E. Declarations) or
            // if we are not in global scope and the statement requires global scope (I.E. Procedures)
            // then throw a new error.
            if ((atTopLevel && !statementParser.allowedAtTopLevel())
                    || (!atTopLevel && !statementParser.allowedBelowTopLevel())) {
                throw new ParsingException("statement not allowed at this level");
            }

            // If we are at this point then we are not in global scope anymore
            this.atTopLevel = false;
            
            // Actually parse the next statement
            Statement statement = statementParser.parse(this, this.tokenStream);
            if (statement == null) {
                throw new ParsingException("invalid statement");
            }
            return statement;
        }

        return null;
    }

    /**
     * Parses out the next expression using the defined expression parsers. 
     * @param precedence The current precedence of the expression being parsed
     * @return The parsed expression
     * @throws ParsingException Thrown if any error occurs while parsing the expression
     */
    public Expression parseExpression(int precedence) throws ParsingException {
        // Get the expression parser for this 
        ExpressionParser<?> prefixParser = this.expressionParsers.get(this.tokenStream.currentPattern());
        if (prefixParser == null) {
            return null;
        }

        // Parse the expression
        Expression leftExpression = prefixParser.parse(this, this.tokenStream);
        // If the expression is not a semicolon and the next expression has a lower precedence than current this is a infix expression
        while (!this.tokenStream.isNext(TokenPattern.SYMBOL_SEMICOLON) && precedence < this.nextPrecedence()) {
            // Get the infix parser for the next token
            InfixExpressionParser infixParser = this.infixParsers.get(this.tokenStream.nextPattern());
            if (infixParser == null) {
                return leftExpression;
            }

            this.tokenStream.advance();
            // Parse the infix expression
            leftExpression = infixParser.parse(this, this.tokenStream, leftExpression);
        }

        return leftExpression;
    }

    /**
     * Parses an list of expressions seperated by commas
     * @return The list of expressions
     * @throws ParsingException Thrown if any expression errors while parsing
     */
    public List<Expression> parseExpressionList() throws ParsingException {
        List<Expression> expressions = new ArrayList<>();

        do {
            // If the token is a comma then we advance to the next expression
            if (this.tokenStream.isCurrent(TokenPattern.SYMBOL_COMMA)) {
                this.tokenStream.advance();
            }
            // Parse the next expression
            Expression expression = this.parseExpression(0);
            if (expression != null) {
                expressions.add(expression);
            }
            this.tokenStream.advance();

            // Continue until the token is not a comma anymore
        } while (this.tokenStream.isCurrent(TokenPattern.SYMBOL_COMMA));

        return expressions;
    }

    /**
     * Looks up the precedence for the current token, 0 if the token is not in the mapping
     */
    public int currentPrecedence() {
        return this.precedences.getOrDefault(this.tokenStream.currentPattern(), 0);
    }

    
    /**
     * Looks up the precedence for the next token, 0 if the token is not in the mapping
     */
    private int nextPrecedence() {
        return this.precedences.getOrDefault(this.tokenStream.nextPattern(), 0);
    }

    public IdentifierTable getIdentifierTable() {
        return IdentifierTable;
    }
}