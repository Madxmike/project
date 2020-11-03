/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.errors;

@SuppressWarnings("serial")
public class UnexpectedSymbolException extends ParsingException {
    private String found;

    public UnexpectedSymbolException(String found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "unexpected symbol " + found;
    }

}
