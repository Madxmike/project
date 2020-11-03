/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.ast;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
* The program is the root of the entire syntax tree
*/
public class Program implements Node {

    private List<Statement> statements;

    public Program() {
        this.statements = new ArrayList<>();
    }

    /**
     * Adds a statement to the root of tree
     */
    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Statement statement : statements) {
            builder.append(statement.toString());
        }

        return builder.toString();
    }

    /**
     * Prints out the syntax tree to the provided print stream
     */
    public void printProgram(PrintStream out) {
        StringBuilder builder = new StringBuilder();

        printNode(builder, this, 0);

        out.println(builder.toString());
    }

    /**
     * Prints out the syntax tree by recursively going through all of the current node's children
     * @param builder The builder to write to
     * @param node The current node being printed
     * @param indentation The current recursive depth
     */
    public void printNode(StringBuilder builder, Node node, int indentation) {
        builder.append("\n");
        for (int i = 0; i < indentation; i++) {
            builder.append("    ");
        }

        builder.append("- ");
        builder.append(node.getClass().getSimpleName());

        List<Node> children = new ArrayList<>();
        node.addChildren(children);
        for (Node child : children) {
            printNode(builder, child, indentation + 1);
        }
    }

    @Override
    public void addChildren(List<Node> children) {
        children.addAll(statements);
    }
}
