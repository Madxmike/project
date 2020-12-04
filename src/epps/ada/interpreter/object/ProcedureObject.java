package interpreter.object;

import java.util.List;

import lexing.ast.BeginStatement;
import lexing.ast.DeclarationStatement;
import lexing.ast.ProcedureStatement;

public class ProcedureObject implements AdaObject {
 
    private ProcedureStatement procedureStatement;

    public ProcedureObject(ProcedureStatement procedureStatement) {
        this.procedureStatement = procedureStatement;
    }

    public List<DeclarationStatement> getParameters() {
        return procedureStatement.getParameters();
    }

    public List<DeclarationStatement> getLocals() {
        return procedureStatement.getLocals();
    }

    public BeginStatement getBody() {
        return procedureStatement.getBeginStatement();
    }

}
