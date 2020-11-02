package lexing.parsing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lexing.ast.IdentifierExpression;
import lexing.ast.Type;
import lexing.errors.TypeRedeclarationException;
import lexing.errors.UnknownTypeException;

public class IdentifierTable {

    private static final Type UNKNOWN_TYPE = new Type("");
    private static Map<String, Type> identifiers = new HashMap<>();
    private static Set<Type> knownTypes = new HashSet<>();

    public static IdentifierExpression getOrCreate(String identifierValue) {
        if (!identifiers.containsKey(identifierValue)) {
            identifiers.put(identifierValue, UNKNOWN_TYPE);
        }
        return new IdentifierExpression(identifierValue);
    }

    public static void setTypeForIdentifier(IdentifierExpression identifier, Type type)
            throws TypeRedeclarationException, UnknownTypeException {
        if (!knownTypes.contains(type)) {
            throw new UnknownTypeException(identifier.getValue(), type);
        }

        Type currentType = identifiers.get(identifier.getValue());
        if (!currentType.equals(UNKNOWN_TYPE)) {
            throw new TypeRedeclarationException(identifier.getValue(), currentType, type);
        }

        identifiers.put(identifier.getValue(), type);

    }

    public static void addType(Type type) {
        // System.out.println(type.getValue());
        knownTypes.add(type);
    }

}
