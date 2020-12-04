package interpreter.evaluators;

import java.util.ArrayList;
import java.util.List;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.DeclarationStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;

public class DeclarationEvaluator implements Evaluator<DeclarationStatement> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, DeclarationStatement node, Environment environment) {

        List<AdaObject> results = new ArrayList<>();
        for(Expression expression : node.getDefaultValues()) {
            AdaObject result = interpreter.evaluate(expression, environment);
            results.add(result);
        }
        List<IdentifierExpression> identifiers = node.getIdentifiers();

        if(results.size() == 0) {
            for(IdentifierExpression identifier : identifiers) {
                environment.put(identifier.getValue(), null);
            }
        } else {
            for(int i = 0; i < results.size(); i++) {
                environment.put(identifiers.get(i).getValue(), results.get(i));
            }
        }


        
        return null;
    }
    
}
