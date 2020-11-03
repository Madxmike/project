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
 * The base class all statements and expressions implmenet
 */
public interface Node {

    /**
     * Add all the node's children to the list for printing
     * @param children
     */
    default void addChildren(List<Node> children) {
        // NO-OP
    }

}