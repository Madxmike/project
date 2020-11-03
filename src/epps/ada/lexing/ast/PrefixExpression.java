/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.ast;

import java.util.List;

/**
 * A PrefixExpression defines an expression that has an operator prefixed to it
 */
public class PrefixExpression implements Expression {

    private OperatorExpression operator;
    private Expression right;

    public PrefixExpression(OperatorExpression operator) {
        this.operator = operator;
    }

    public void setRight(Expression expression) {
        this.right = expression;
    }

    @Override
    public String toString() {
        return operator +  " " + right;
    }

    @Override
    public void addChildren(List<Node> children) {
        children.add(operator);
        children.add(right);
    }

}
