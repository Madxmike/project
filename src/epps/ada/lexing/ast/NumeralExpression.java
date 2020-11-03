/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */
package lexing.ast;

public class NumeralExpression implements Expression {

    private float value;

    public NumeralExpression(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }

}
