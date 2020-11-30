package interpreter.evaluators;

import java.util.Map;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.BeginStatement;
import lexing.ast.Statement;

public class BeginEvaluator implements Evaluator<BeginStatement> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, BeginStatement node, Environment environment) {
        for(Statement statement : node.getStatements()) {
            interpreter.evaluate(statement, environment);
        }

        return null;
    }

}
