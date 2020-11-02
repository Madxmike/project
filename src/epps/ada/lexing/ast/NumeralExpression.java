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
