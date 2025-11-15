package booktracker.exception;

public class BookCategoryNotFoundException extends RuntimeException {
    public BookCategoryNotFoundException(String message) {
        super(message);
    }
}
