/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.ast;

import grammar.TokenPattern;

/**
 * An OperatorExpression simply defines an operator such as +, -, *, /
 */
public class OperatorExpression implements Expression {
    public TokenPattern operator;

    public OperatorExpression(TokenPattern operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator.resolve();
    }
}
