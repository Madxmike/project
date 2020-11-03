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
public class ParsingException extends Exception {

    private String message;

    public ParsingException() {
        this.message = "";
    }

    public ParsingException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

}
