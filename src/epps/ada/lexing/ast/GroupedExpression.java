package lexing.ast;

import java.util.List;

public class GroupedExpression implements Expression {

    private Expression expression;

    public GroupedExpression(Expression expression) {
        this.expression = expression;
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
