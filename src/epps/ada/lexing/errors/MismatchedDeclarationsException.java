/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */

package lexing.errors;

/**
 * A MismatchedDeclarationsException is thrown when the parser encounters a declaration
 * in which the number of identifiers is not equal to the number of expressions
 */
@SuppressWarnings("serial")
public class MismatchedDeclarationsException extends ParsingException {

    private int leftExpected;
    private int rightExpected;

    public MismatchedDeclarationsException(int leftExpected, int rightExpected) {
        this.leftExpected = leftExpected;
        this.rightExpected = rightExpected;
    }

    @Override
    public String toString() {
        return "mismatched declaration: " + leftExpected + " identifiers were found, but " + rightExpected
                + " values were given";
    }
}
