package interpreter.evaluators;

import java.util.ArrayList;
import java.util.List;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import interpreter.object.ProcedureObject;
import lexing.ast.CallExpression;
import lexing.ast.DeclarationStatement;
import lexing.ast.Expression;
import lexing.ast.IdentifierExpression;

public class CallEvaluator implements Evaluator<CallExpression> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, CallExpression node, Environment environment) {
        List<AdaObject> args = new ArrayList<>();
        
        for(Expression expression : node.getArgsExpressions()) {
            AdaObject result = interpreter.evaluate(expression, environment);
            if(result == null) {
                //todo - this should never be null or we have an error
                return null;
            }

            args.add(result);
        }

        AdaObject object = environment.get(node.getFunctionName()); 
        if(object == null) {
            //todo - panic cause we are calling a function that doesnt exist
            return null;
        }
        
        ProcedureObject procedureObject = (ProcedureObject) object;
        List<IdentifierExpression> identifiers = new ArrayList<>();
        for(DeclarationStatement param : procedureObject.getParameters()) {
            interpreter.evaluate(param, environment);
            identifiers.addAll(param.getIdentifiers());
        }


        for(int i = 0; i < args.size(); i++) {
            environment.put(identifiers.get(i).getValue(), args.get(i));
        }


        for(DeclarationStatement param : procedureObject.getLocals()) {
            interpreter.evaluate(param, environment);
        }
        
        interpreter.evaluate(procedureObject.getBody(), environment);

        return null;
    }
    
}
