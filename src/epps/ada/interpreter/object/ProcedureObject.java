package interpreter.object;

import lexing.ast.ProcedureStatement;

public class ProcedureObject implements AdaObject {
 
    private ProcedureStatement procedureStatement;

    public ProcedureObject(ProcedureStatement procedureStatement) {
        this.procedureStatement = procedureStatement;
    }

}
