
/**
 * Course: Concepts of Programming Languages
 * Section: W01
 * Professor: Jose Garrido
 * Date: November 3rd, 2020
 * Author: Michael Epps
 * Assignment: CPL Project, Deliverable 
 */

package interpreter;

import java.util.HashMap;
import java.util.Map;

import interpreter.evaluators.BeginEvaluator;
import interpreter.evaluators.Evaluator;
import interpreter.evaluators.ProcedureEvaluator;
import interpreter.evaluators.ProgramEvaluator;
import interpreter.object.AdaObject;
import interpreter.object.ProcedureObject;
import lexing.ast.BeginStatement;
import lexing.ast.Node;
import lexing.ast.ProcedureStatement;
import lexing.ast.Program;

public class Interpreter {

    private Map<Class<? extends Node>, Evaluator<?>> evaluators;
    
    public Interpreter(String entryPoint) {

        this.evaluators = new HashMap<>();

        this.evaluators.put(Program.class, new ProgramEvaluator(entryPoint));
        this.evaluators.put(ProcedureStatement.class, new ProcedureEvaluator());
        this.evaluators.put(BeginStatement.class, new BeginEvaluator());
    }

    public AdaObject evaluate(Node node, Environment environment) {
        Evaluator evaluator = this.evaluators.get(node.getClass());

        if(evaluator == null) {
            System.out.println("[NYI] "  + node.getClass().getName());
            return null;
        }
        return evaluator.evaluate(this, node, environment);
    }

    public AdaObject evaluate(Node node) {
        return this.evaluate(node, new Environment());
    }


}
