/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.parsing;

import java.util.Stack;

import lexing.ast.IdentifierExpression;

/**
 * Represent the scope as a stack that can be pushed and popped. When the stack
 * is empty, we are in global scope. Example stack would be: procedure "hello"
 * -> for loop -> for loop. This would allow variables local to only the inner
 * for loop, outer for loop, the procedure "hello", or globally.
 */
public class Scope {

    private Stack<IdentifierExpression> stack;

    public Scope() {
        this.stack = new Stack<>();
    }

    /**
     * Checks to see if this scope is either equal to or a parent scope of the
     * scope. A global scope is included in all other scopes.
     */
    public boolean isIncludedIn(Scope otherScope) {
        // Special case: If the stack is empty then this is a global scope.
        // A global scope is implicitity included in all other scopes.
        if (this.stack.size() == 0) {
            return true;
        }

        // If the other scope is smaller then its impossible to be included in it.
        if (this.stack.size() > otherScope.stack.size()) {
            return false;
        }

        // Since we know this scope is always smaller or equal to the other scope,
        // we will never go out of bounds here.
        for (int i = 0; i < this.stack.size(); i++) {
            if (!this.stack.get(i).equals(otherScope.stack.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Scope)) {
            return false;
        }

        Scope otherScope = (Scope) obj;

        if (this.stack.size() != otherScope.stack.size()) {
            return false;
        }

        for (int i = 0; i < this.stack.size(); i++) {
            if (!this.stack.get(i).equals(otherScope.stack.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a new Scope that has the same scope as this one.
     * 
     * @return the newly created Scope
     */
    public Scope copy() {
        Scope copy = new Scope();
        for (IdentifierExpression identifierExpression : this.stack) {
            copy.stack.push(identifierExpression);
        }

        return copy;
    }

    /**
     * Adds a another layer to the scope.
     * 
     * @param identifierExpression The layer to add to the scope
     */
    public void pushScope(IdentifierExpression identifierExpression) {
        this.stack.push(identifierExpression);
    }

    /**
     * Removes the top most layer from the scope;
     */
    public void popScope() {
        this.stack.pop();
    }

}
