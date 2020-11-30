package interpreter;

import java.util.HashMap;
import java.util.Map;

import interpreter.object.AdaObject;

public class Environment {
    
    private Map<String, AdaObject> inner;
    private Environment outer;

    public Environment() {
        this(null);
    }

    public Environment(Environment outer) {
        this.inner = new HashMap<>();
        this.outer = outer;
    }

    public Environment enclose() {
        return new Environment(this);
    }

    public AdaObject get(String name) {
        AdaObject object = inner.get(name);
        if(object != null) {
            return object;
        }

        if(outer == null) {
            return null;
        }

        return outer.get(name);
    }

    public void put(String name, AdaObject object) {
        this.inner.put(name, object);
    }

}
