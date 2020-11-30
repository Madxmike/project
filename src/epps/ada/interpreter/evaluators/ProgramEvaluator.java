package interpreter.evaluators;

import java.util.Map;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.ProcedureStatement;
import lexing.ast.Program;
import lexing.ast.Statement;

public class ProgramEvaluator implements Evaluator<Program> {

    private String entryPoint;

    public ProgramEvaluator(String entryPoint) {
        this.entryPoint = entryPoint;
    }


    @Override
    public AdaObject evaluate(Interpreter interpreter, Program node, Environment environment) {
        ProcedureStatement entry = null;
        for(Statement statement : node.getStatements()) {
            if(statement instanceof ProcedureStatement) {
                ProcedureStatement procedureStatement = (ProcedureStatement) statement;
                if(procedureStatement.getName().equals(entryPoint)) {
                    entry = procedureStatement;
                }
                AdaObject result = interpreter.evaluate(statement, environment);
                if(result != null) {
                    //TODO - should we error if result is null?
                    environment.put(procedureStatement.getName(), result);
                }
            }
        }

        //todo - check validity of calling this

        interpreter.evaluate(entry.getBeginStatement(), environment.enclose());

        return null;     
    }
    
}
