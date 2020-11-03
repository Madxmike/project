/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.ast;

import java.util.ArrayList;
import java.util.List;

public class Program implements Node {

    private List<Statement> statements;

    public Program() {
        this.statements = new ArrayList<>();
    }

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

    public void printProgram() {
        StringBuilder builder = new StringBuilder();

        printNode(builder, this, 0);

        System.out.println(builder.toString());
    }

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
