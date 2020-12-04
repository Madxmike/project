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
 * An InfixExpression defines an expression where the left half expression and right half expression are seperated by a operator
 */
public class InfixExpression implements Expression {

    private Expression left;
    private OperatorExpression operator;
    private Expression right;

    public InfixExpression(Expression left, OperatorExpression operator) {
        this.left = left;
        this.operator = operator;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public OperatorExpression getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

    public void addChildren(List<Node> children) {
        children.add(left);
        children.add(operator);
        children.add(right);
    }

}
