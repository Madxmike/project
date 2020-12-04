package interpreter.evaluators;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.GroupedExpression;

public class GroupedEvaluator implements Evaluator<GroupedExpression> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, GroupedExpression node, Environment environment) {
        return interpreter.evaluate(node.getExpression(), environment);
    }
    
}
