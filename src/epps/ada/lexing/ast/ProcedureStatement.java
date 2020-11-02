package lexing.ast;

import java.util.List;

public class ProcedureStatement implements Statement {

    private IdentifierExpression name;
    private List<DeclarationStatement> parameters;
    private List<DeclarationStatement> locals;
    private BeginStatement begin;

    public ProcedureStatement(IdentifierExpression name, List<DeclarationStatement> parameters,
            List<DeclarationStatement> locals, BeginStatement begin) {
        this.name = name;
        this.parameters = parameters;
        this.locals = locals;
        this.begin = begin;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("procedure ");
        builder.append(name);
        if (parameters.size() > 0) {
            builder.append(" (\n");
            parameters.forEach(p -> {
                builder.append(parameters);
                builder.append("\n");
            });
            builder.append(")");
        }
        if (begin != null) {
            builder.append(" is \n");
            parameters.forEach(p -> {
                builder.append(locals);
                builder.append("\n");
            });
            builder.append(begin);
        }
        builder.append(";");
        return builder.toString();
    }

    @Override
    public void addChildren(List<Node> children) {
        children.add(name);
        children.addAll(parameters);
        children.addAll(locals);
        children.add(begin);
    }

}
