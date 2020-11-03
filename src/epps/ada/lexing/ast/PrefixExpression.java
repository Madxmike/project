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

public class PrefixExpression implements Expression {

    private String operator;
    private Expression right;

    public PrefixExpression(String operator) {
        this.operator = operator;
    }

    public void setRight(Expression expression) {
        this.right = expression;
    }

    @Override
    public String toString() {
        return operator + right;
    }

    @Override
    public void addChildren(List<Node> children) {
        // TODO - Add operator here
        children.add(right);
    }

}
