package interpreter.evaluators;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import interpreter.object.ValueObject;
import lexing.ast.NumeralExpression;

public class NumeralEvaluator implements Evaluator<NumeralExpression> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, NumeralExpression node, Environment environment) {
        return new ValueObject<Float>(node.getValue());
    }
    
}
