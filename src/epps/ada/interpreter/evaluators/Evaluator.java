package interpreter.evaluators;

import java.util.Map;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import lexing.ast.Node;

@FunctionalInterface
public interface Evaluator<T extends Node> {

    AdaObject evaluate(Interpreter interpreter, T node, Environment environment);    
}
