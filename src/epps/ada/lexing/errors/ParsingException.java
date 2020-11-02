package lexing.errors;

public class ParsingException extends Exception {
    
    private String message;

    public ParsingException() {
        this.message = "";
    }

    public ParsingException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

}
