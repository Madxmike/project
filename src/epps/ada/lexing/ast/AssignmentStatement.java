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
 * An AssignmentStatement defines an assignment where a list of identifiers is assigned to a list of expression values
 */
public class AssignmentStatement extends IdentifierStatement {

    private List<IdentifierExpression> identifiers;
    private List<Expression> values;

    public AssignmentStatement(List<IdentifierExpression> identifiers, List<Expression> values) {
        this.identifiers = identifiers;
        this.values = values;
    }

    public List<IdentifierExpression> getIdentifiers() {
        return identifiers;
    }

    public List<Expression> getValues() {
        return values;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < identifiers.size() - 1; i++) {
            builder.append(identifiers.get(i).getValue());
            builder.append(", ");
        }

        builder.append(identifiers.get(identifiers.size() - 1).getValue());
        builder.append(" := ");

        for (int i = 0; i < values.size() - 1; i++) {
            builder.append(values.get(i));
            builder.append(", ");
        }
        builder.append(values.get(values.size() - 1));
        builder.append(";");

        return builder.toString();
    }

    @Override
    public void addChildren(List<Node> children) {
        children.addAll(identifiers);
        children.addAll(values);
    }

}
