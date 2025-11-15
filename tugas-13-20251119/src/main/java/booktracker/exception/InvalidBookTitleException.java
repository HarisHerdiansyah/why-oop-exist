package booktracker.exception;

public class InvalidBookTitleException extends RuntimeException {
    public InvalidBookTitleException(String message) {
        super(message);
    }
}
