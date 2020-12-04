package interpreter.evaluators;

import java.util.ArrayList;
import java.util.List;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.AssignmentStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;

public class AssignmentEvaluator implements Evaluator<AssignmentStatement> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, AssignmentStatement node, Environment environment) {
        List<IdentifierExpression> identifiers = node.getIdentifiers();
        List<Expression> values = node.getValues();
        
        List<AdaObject> results = new ArrayList<>();
        for(Expression value : values) {
            AdaObject result = interpreter.evaluate(value, environment);
            results.add(result);
        }

        for(int i = 0; i < identifiers.size(); i++) {
            environment.put(identifiers.get(i).getValue(), results.get(i));
        }

        return null;
    }
    
}
