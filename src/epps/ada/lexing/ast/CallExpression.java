package lexing.ast;

import java.util.List;

public class CallExpression extends IdentifierStatement implements Expression {
     
    private IdentifierExpression function;
    private List<Expression> args;

    public CallExpression(IdentifierExpression function,  List<Expression> args) {
        this.function = function;
        this.args = args;
    }

    public String getFunctionName() {
        return function.getValue();
    }

    public List<Expression> getArgsExpressions() {
        return args;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(function);
        builder.append("(");
        for(int i = 0; i < args.size() - 1; i++) {
            builder.append(args.get(i));
            builder.append(", ");
        }
        builder.append(args.get(args.size() - 1));
        builder.append(")");

       return builder.toString();
    }

    @Override
    public void addChildren(List<Node> children) {
        children.add(function);
        children.addAll(args);
    }    
}
