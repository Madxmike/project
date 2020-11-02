package lexing.errors;

@SuppressWarnings("serial")
public class UnexpectedSymbolException extends ParsingException {
    private String found;

    public UnexpectedSymbolException(String found) {
        this.found = found;
    }

    @Override
    public String toString() {
        return "unexpected symbol " + found;
    }

}
