package lexing.errors;

public class MismatchedDeclarationsException extends ParsingException {
    
    private int leftExpected;
    private int rightExpected;

    public MismatchedDeclarationsException(int leftExpected, int rightExpected) {
        this.leftExpected = leftExpected;
        this.rightExpected = rightExpected;
    }

    @Override
    public String toString() {
        return "mismatched declaration: " + leftExpected + " identifiers were found, but " + rightExpected + " values were given";
    }
}
