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
 * A declaration statement defines declaration in which an identifier list is given a type, and possibly given default values
 */
public class DeclarationStatement extends IdentifierStatement {

    private List<IdentifierExpression> identifiers;
    private Type type;
    private List<Expression> defaultValues;

    public DeclarationStatement(List<IdentifierExpression> identifiers, Type type, List<Expression> defaultValues) {
        this.identifiers = identifiers;
        this.type = type;
        this.defaultValues = defaultValues;
    }

    public List<IdentifierExpression> getIdentifiers() {
        return identifiers;
    }

    public List<Expression> getDefaultValues() {
        return defaultValues;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < identifiers.size() - 1; i++) {
            builder.append(identifiers.get(i).getValue());
            builder.append(", ");
        }

        builder.append(identifiers.get(identifiers.size() - 1).getValue());
        builder.append(" : ");

        builder.append(type.getValue());

        if (!defaultValues.isEmpty()) {
            builder.append(" := ");

            for (int i = 0; i < defaultValues.size() - 1; i++) {
                builder.append(defaultValues.get(i));
                builder.append(", ");
            }
            builder.append(defaultValues.get(defaultValues.size() - 1));
            builder.append(";");
        }

        return builder.toString();
    }

    @Override
    public void addChildren(List<Node> children) {
        children.addAll(identifiers);
        children.add(type);
        children.addAll(defaultValues);
    }

}
