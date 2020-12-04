package interpreter.object;

public class ValueObject<T extends Number> implements AdaObject {

    private T value;

    public ValueObject(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
    
}
