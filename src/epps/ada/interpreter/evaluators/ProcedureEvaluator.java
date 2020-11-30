package interpreter.evaluators;

import java.util.Map;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import interpreter.object.ProcedureObject;
import lexing.ast.ProcedureStatement;

public class ProcedureEvaluator implements Evaluator<ProcedureStatement> {


    @Override
    public AdaObject evaluate(Interpreter interpreter, ProcedureStatement node, Environment environment) {
        return new ProcedureObject(node);
    }
    
}
