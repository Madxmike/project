package interpreter.evaluators;

import interpreter.Environment;
import interpreter.Interpreter;
import interpreter.object.AdaObject;
import interpreter.object.ValueObject;
import lexing.ast.InfixExpression;

public class InfixEvaluator implements Evaluator<InfixExpression> {

    @Override
    public AdaObject evaluate(Interpreter interpreter, InfixExpression node, Environment environment) {
        // Only two value objects may be operated on
        // so we are assuming this works for now. Todo throw an appropriate exception
        ValueObject<Number> leftValue = (ValueObject<Number>) interpreter.evaluate(node.getLeft(), environment);
        ValueObject<Number> rightValue = (ValueObject<Number>) interpreter.evaluate(node.getRight(), environment);

        if(leftValue instanceof ValueObject && rightValue instanceof ValueObject) {
            switch (node.getOperator().operator) {
                case SYMBOL_PLUS:
                    return new ValueObject<Float>(leftValue.getValue().floatValue() + rightValue.getValue().floatValue());
                case SYMBOL_MULTIPLICATION:
                    return new ValueObject<Float>(leftValue.getValue().floatValue() * rightValue.getValue().floatValue());
                case SYMBOL_MINUS:
                    return new ValueObject<Float>(leftValue.getValue().floatValue() - rightValue.getValue().floatValue());
                case SYMBOL_DIVISION:
                    return new ValueObject<Float>(leftValue.getValue().floatValue() / rightValue.getValue().floatValue());
                default: 
                    return null;
            }

        }


        return null;
    }
    
}
