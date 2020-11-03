package lexing.parsing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import lexing.ast.IdentifierExpression;
import lexing.ast.Type;
import lexing.errors.IdentifierRedeclarationException;
import lexing.errors.ParsingException;
import lexing.errors.UnknownTypeException;
import lexing.parsing.Scope;

public class IdentifierTable {
    
    // Temporary declaration of these types here for easy access;
    public static final Type BUILT_IN_TYPE_PROCEDURE = new Type("procedure");
    public static final Type BUILT_IN_TYPE_INTEGER = new Type("Integer");
    public static final Type BUILT_IN_TYPE_FLOAT = new Type("Float");

    /**
     * The identifiers that have been declared;
     */
    private List<IdentifierInformation> identifierInfos;

    /**
     * A set of all the types that have been defined;
     */
    private Set<Type> knownTypes;

    /**
     * Represent the scope as a stack that can be pushed and popped.
     * When the stack is empty, we are in global scope.
     * Example stack would be: procedure "hello" -> for loop -> for loop.
     * This would allow variables local to only the inner for loop,
     * outer for loop, the procedure "hello", or globally.
     */
    private Scope currentScope;

    
    public IdentifierTable() {
        this.identifierInfos = new ArrayList<>();
        this.knownTypes = new HashSet<>();
        this.currentScope = new Scope();

        // Add built-in types
        this.addKnownType(BUILT_IN_TYPE_PROCEDURE);
        this.addKnownType(BUILT_IN_TYPE_INTEGER);
        this.addKnownType(BUILT_IN_TYPE_FLOAT);
    }


    /**
     * Attempts to declare the identifier in the current scope as the type provided.
     * @param identifier - The identifier to declare
     * @param type - The type of the identifier
     * @throws ParsingException - Thrown if the identifier cannot be declared in this scope or with this type
     */
    public void declareIdentifier(IdentifierExpression identifier, Type type) throws ParsingException {

        // If the type doesnt exist then we cant declare this identifier
        if(!doesTypeExist(type)) {
            throw new UnknownTypeException(identifier, type);
        }

        // If the identifier is already declared in this scope then we cant declare this identifier, again
        if(identifierDeclaredInScopeAlready(identifier)) {
            throw new IdentifierRedeclarationException(identifier);
        }


        IdentifierInformation identifierInformation = new IdentifierInformation(identifier, type, currentScope);
        this.identifierInfos.add(identifierInformation);
    }

    /**
     * Adds a type to the known types set
     * @param type The type to add
     */
    public void addKnownType(Type type) {
        this.knownTypes.add(type);
    }

    /**
     * Is the type provided a known type
     * @param type The type to check
     * @return Is the type provided a known type
     */
    private boolean doesTypeExist(Type type) {
        return this.knownTypes.stream()
        .filter(type::equals)
        .findAny()
        .isPresent();
    }

    /**
     * Checks to see if there exists any declared identifiers that equal the 
     * passed in identifier tith scopes that are included in the current scope.
     * @param identifier The identifier to check
     * @return Is this identifier declared already in this scope
     */
    private boolean identifierDeclaredInScopeAlready(IdentifierExpression identifier) {
        return this.identifierInfos.stream()
        .filter(identifierInfo -> identifierInfo.getIdentifier().equals(identifier))
        .map(IdentifierInformation::getScope)
        .anyMatch(scope -> scope.isIncludedIn(currentScope));
    }

    /**
     * Push the provided scope onto the current scope.
     * @param scope The scope to push
     */
    public void pushScope(IdentifierExpression scope) {
        this.currentScope.pushScope(scope);
    }

    /**
     * Pop the top layer of the current scope.
     */
    public void popScope() {
        this.currentScope.popScope();
    }

    /**
     * IdentifierInformation is simply a class to hold various information about an identifier
     */
    private class IdentifierInformation {
        private IdentifierExpression identifier;
        private Type type;
        private Scope scope;

        private IdentifierInformation(IdentifierExpression identifier, Type type, Scope scope) {
            this.identifier = identifier;
            this.type = type;
            this.scope = scope.copy();
        }

        public IdentifierExpression getIdentifier() {
            return identifier;
        }

        public Type getType() {
            return type;
        }

        public Scope getScope() {
            return scope;
        }
    }


}
