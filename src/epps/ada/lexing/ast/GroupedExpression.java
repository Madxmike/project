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
 * A GroupedExpression defines an expression in which the sub-expression is surrounded with a set of parentheses
 */
public class GroupedExpression implements Expression {

    private Expression expression;

    public GroupedExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }

    @Override
    public void addChildren(List<Node> children) {
        children.add(expression);
    }

}
