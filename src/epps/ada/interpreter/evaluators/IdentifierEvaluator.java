package interpreter.evaluators;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.IdentifierExpression;

public class IdentifierEvaluator implements Evaluator<IdentifierExpression> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, IdentifierExpression node, Environment environment) {
        return environment.get(node.getValue());
    }

    
}
