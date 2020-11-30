package lexing.ast;

import java.util.List;

public class CallStatement extends IdentifierStatement {
    
    private CallExpression expression;

    public CallStatement(CallExpression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return expression.toString() + ";";
    }

    @Override
    public void addChildren(List<Node> children) {
        children.add(expression);
    }    
}
