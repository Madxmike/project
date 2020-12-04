
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

import interpreter.evaluators.AssignmentEvaluator;
import interpreter.evaluators.BeginEvaluator;
import interpreter.evaluators.CallEvaluator;
import interpreter.evaluators.DeclarationEvaluator;
import interpreter.evaluators.Evaluator;
import interpreter.evaluators.GroupedEvaluator;
import interpreter.evaluators.IdentifierEvaluator;
import interpreter.evaluators.InfixEvaluator;
import interpreter.evaluators.NumeralEvaluator;
import interpreter.evaluators.ProcedureEvaluator;
import interpreter.evaluators.ProgramEvaluator;
import interpreter.object.AdaObject;
import interpreter.object.ProcedureObject;
import lexing.ast.AssignmentStatement;
import lexing.ast.BeginStatement;
import lexing.ast.CallExpression;
import lexing.ast.DeclarationStatement;
import lexing.ast.GroupedExpression;
import lexing.ast.IdentifierExpression;
import lexing.ast.InfixExpression;
import lexing.ast.Node;
import lexing.ast.NumeralExpression;
import lexing.ast.ProcedureStatement;
import lexing.ast.Program;

public class Interpreter {

    private Map<Class<? extends Node>, Evaluator<?>> evaluators;
    
    public Interpreter(String entryPoint) {

        this.evaluators = new HashMap<>();

        this.evaluators.put(Program.class, new ProgramEvaluator(entryPoint));
        this.evaluators.put(ProcedureStatement.class, new ProcedureEvaluator());
        this.evaluators.put(BeginStatement.class, new BeginEvaluator());
        this.evaluators.put(CallExpression.class, new CallEvaluator());
        this.evaluators.put(DeclarationStatement.class, new DeclarationEvaluator());
        this.evaluators.put(NumeralExpression.class, new NumeralEvaluator());
        this.evaluators.put(IdentifierExpression.class, new IdentifierEvaluator());
        this.evaluators.put(InfixExpression.class, new InfixEvaluator());
        this.evaluators.put(AssignmentStatement.class, new AssignmentEvaluator());
        this.evaluators.put(GroupedExpression.class, new GroupedEvaluator());
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
