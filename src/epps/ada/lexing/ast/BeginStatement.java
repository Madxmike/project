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

public class BeginStatement implements Statement {

    private List<Statement> statements;
    private IdentifierExpression belongsTo;

    public BeginStatement(List<Statement> statements, IdentifierExpression belongsTo) {
        this.statements = statements;
        this.belongsTo = belongsTo;
    }

    public IdentifierExpression getBelongsTo() {
        return belongsTo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("begin");
        statements.forEach(s -> {
            builder.append("\n");
            builder.append(s);
        });
        builder.append("\nend ");
        builder.append(belongsTo);
        return builder.toString();
    }

    @Override
    public void addChildren(List<Node> children) {
        children.addAll(statements);
        children.add(belongsTo);
    }

}
