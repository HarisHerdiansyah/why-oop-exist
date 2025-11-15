package booktracker.exception;

public class InvalidBookCategoryNameException extends RuntimeException {
    public InvalidBookCategoryNameException(String message) {
        super(message);
    }
}
